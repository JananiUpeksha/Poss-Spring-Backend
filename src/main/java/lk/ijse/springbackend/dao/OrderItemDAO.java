package lk.ijse.springbackend.dao;

import lk.ijse.springbackend.entity.impl.OrderEntity;
import lk.ijse.springbackend.entity.impl.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemDAO extends JpaRepository<OrderItemEntity, Integer> {
}
