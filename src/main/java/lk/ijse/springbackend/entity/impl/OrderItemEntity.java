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
    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "itemId")
    private ItemEntity item;
    private Integer quantity;
}
