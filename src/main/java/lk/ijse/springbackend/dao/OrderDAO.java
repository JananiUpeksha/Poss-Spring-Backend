package lk.ijse.springbackend.dao;

import lk.ijse.springbackend.entity.impl.CustomerEntity;
import lk.ijse.springbackend.entity.impl.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<OrderEntity,String> {
}
