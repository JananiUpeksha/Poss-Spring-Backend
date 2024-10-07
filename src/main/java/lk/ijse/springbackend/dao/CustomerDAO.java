package lk.ijse.springbackend.dao;

import lk.ijse.springbackend.entity.impl.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDAO extends JpaRepository<CustomerEntity,String> {
}
