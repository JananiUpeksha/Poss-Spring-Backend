package lk.ijse.springbackend.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrderDTO {
    private String orderId;
    private String customerId;
    private Double total;
    private LocalDateTime date;
}
