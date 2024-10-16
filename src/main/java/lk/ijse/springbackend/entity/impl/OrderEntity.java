package lk.ijse.springbackend.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private CustomerEntity customer;

    private Double total;
    private LocalDate date;

    // Update this line
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();
}
