package lk.ijse.springbackend.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    private String id;
    private String name;
    private String address;
    private String contact;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>(); // Initialize the list


    public CustomerEntity(String orderId) {
        this.id = id;
    }
}
