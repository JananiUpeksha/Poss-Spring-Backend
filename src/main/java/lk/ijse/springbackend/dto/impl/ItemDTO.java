package lk.ijse.springbackend.dto.impl;

import lk.ijse.springbackend.dto.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ItemDTO implements ItemStatus {
    private String id;
    private String name;
    private Double price;
    private Integer qty;
    private List<OrderItemDTO> orderItems;
}
