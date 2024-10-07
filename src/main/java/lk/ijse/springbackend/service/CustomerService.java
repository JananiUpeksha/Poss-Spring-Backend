package lk.ijse.springbackend.service;

import lk.ijse.springbackend.dto.CustomerStatus;
import lk.ijse.springbackend.dto.impl.CustomerDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CustomerService {
    void saveCustomer(CustomerDTO customerDTO);

    CustomerStatus getCustomer(String customerId);

    List<CustomerDTO> getAllCustomers();

    void deleteNote(String customerId);

    void updateCustomer(String customerId, CustomerDTO updateCustomerDTO);
}
