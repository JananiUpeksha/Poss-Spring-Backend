package lk.ijse.springbackend.dto.impl;

import lk.ijse.springbackend.dto.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CustomerDTO implements CustomerStatus {
    private String id;
    private String name;
    private String address;
    private String contact;
    private List<OrderDTO> orders;
}
