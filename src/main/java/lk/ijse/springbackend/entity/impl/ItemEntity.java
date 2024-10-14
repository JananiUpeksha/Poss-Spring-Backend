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
@Table(name = "item")
public class ItemEntity {
    @Id
    private String id;
    private String name;
    private Double price;
    private Integer qty;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orders = new ArrayList<>(); // Initialize the list

    public ItemEntity(String id) {
        this.id = id;
    }
}
