package lk.ijse.springbackend.dao;

import lk.ijse.springbackend.entity.impl.CustomerEntity;
import lk.ijse.springbackend.entity.impl.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDAO extends JpaRepository<ItemEntity,String> {

}
