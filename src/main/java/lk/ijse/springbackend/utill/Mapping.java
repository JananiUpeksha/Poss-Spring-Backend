package lk.ijse.springbackend.utill;

import lk.ijse.springbackend.dto.impl.CustomerDTO;
import lk.ijse.springbackend.dto.impl.ItemDTO;
import lk.ijse.springbackend.dto.impl.OrderDTO;
import lk.ijse.springbackend.entity.impl.CustomerEntity;
import lk.ijse.springbackend.entity.impl.ItemEntity;
import lk.ijse.springbackend.entity.impl.OrderEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    // Mapping methods for Customer
    public CustomerEntity toCustomerEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, CustomerEntity.class);
    }

    public CustomerDTO toCustomerDTO(CustomerEntity customerEntity) {
        return modelMapper.map(customerEntity, CustomerDTO.class);
    }

    public List<CustomerDTO> asCustomerDTOList(List<CustomerEntity> customerEntityList) {
        return modelMapper.map(customerEntityList, new TypeToken<List<CustomerDTO>>() {}.getType());
    }

    // Mapping methods for Item
    public ItemEntity toItemEntity(ItemDTO itemDTO) {
        return modelMapper.map(itemDTO, ItemEntity.class);
    }

    public ItemDTO toItemDTO(ItemEntity itemEntity) {
        return modelMapper.map(itemEntity, ItemDTO.class);
    }

    public List<ItemDTO> asItemDTOList(List<ItemEntity> itemEntityList) {
        return modelMapper.map(itemEntityList, new TypeToken<List<ItemDTO>>() {}.getType());
    }

    // Mapping methods for Order
    public OrderEntity toOrderEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, OrderEntity.class);
    }

    public OrderDTO toOrderDTO(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    public List<OrderDTO> asOrderDTOList(List<OrderEntity> orderEntityList) {
        return modelMapper.map(orderEntityList, new TypeToken<List<OrderDTO>>() {}.getType());
    }
}
