package lk.ijse.springbackend.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ItemDTO {
    private String id;
    private String name;
    private Double price;
    private Integer qty;
}
