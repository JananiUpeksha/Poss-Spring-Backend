package lk.ijse.springbackend.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
    private String orderId;
    private String customerId; // Assuming you want to keep customer ID
    private Double total;
    private LocalDate date;
    private List<OrderItemDTO> orderItems; // List of order items

    public List<OrderItemDTO> getItemDtoList() {
        return orderItems; // Return the list of order items
    }
}
