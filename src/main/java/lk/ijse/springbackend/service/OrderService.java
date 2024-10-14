package lk.ijse.springbackend.service;

import lk.ijse.springbackend.dto.impl.OrderDTO;

public interface OrderService {
    void saveOrder(OrderDTO orderDTO);
}
