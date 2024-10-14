package lk.ijse.springbackend.service.impl;

import lk.ijse.springbackend.dao.CustomerDAO;
import lk.ijse.springbackend.dao.ItemDAO;
import lk.ijse.springbackend.dao.OrderDAO;
import lk.ijse.springbackend.dao.OrderItemDAO;
import lk.ijse.springbackend.dto.impl.OrderDTO;
import lk.ijse.springbackend.dto.impl.OrderItemDTO;
import lk.ijse.springbackend.entity.impl.CustomerEntity;
import lk.ijse.springbackend.entity.impl.ItemEntity;
import lk.ijse.springbackend.entity.impl.OrderEntity;
import lk.ijse.springbackend.entity.impl.OrderItemEntity;
import lk.ijse.springbackend.exception.DataPersistException;
import lk.ijse.springbackend.service.ItemService;
import lk.ijse.springbackend.service.OrderService;
import lk.ijse.springbackend.utill.AppUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private OrderItemDAO orderItemDAO;

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public void saveOrder(OrderDTO orderDTO) {
        if (orderDTO.getDate() == null) {
            throw new IllegalArgumentException("Order date cannot be null.");
        }

        CustomerEntity customerEntity = customerDAO.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new DataPersistException("Customer ID does not exist."));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(AppUtill.generateOrderId());
        orderEntity.setCustomer(customerEntity);
        orderEntity.setTotal(orderDTO.getTotal());
        orderEntity.setDate(orderDTO.getDate());

        // Save the order
        orderDAO.save(orderEntity);

        // Save order items and decrease item quantities
        List<OrderItemEntity> orderItems = new ArrayList<>();
        for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
            // Create OrderItemEntity
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrder(orderEntity);
            orderItemEntity.setItem(new ItemEntity(orderItemDTO.getItemId()));
            orderItemEntity.setQuantity(orderItemDTO.getQuantity());

            orderItems.add(orderItemEntity);

            // Decrease the item's quantity
            itemService.updateItemQuantity(orderItemDTO.getItemId(), -orderItemDTO.getQuantity()); // Use negative quantity
        }

        // Save all order items
        orderEntity.setOrderItems(orderItems);
        orderDAO.save(orderEntity); // This is necessary if you need to persist relationships.
    }


}
