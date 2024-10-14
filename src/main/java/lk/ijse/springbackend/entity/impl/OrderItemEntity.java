package lk.ijse.springbackend.entity.impl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "ordersItem")
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Adding an ID for OrderItemEntity

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "itemId", nullable = false)
    private ItemEntity item;

    private Integer quantity;
}
