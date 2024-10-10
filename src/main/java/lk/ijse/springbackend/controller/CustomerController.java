package lk.ijse.springbackend.controller;

import lk.ijse.springbackend.customStatusCode.SelectedCustomerErrorStatus;
import lk.ijse.springbackend.dto.CustomerStatus;
import lk.ijse.springbackend.dto.impl.CustomerDTO;
import lk.ijse.springbackend.exception.CustomerNotFoundException;
import lk.ijse.springbackend.exception.DataPersistException;
import lk.ijse.springbackend.service.CustomerService;
import lk.ijse.springbackend.utill.RegexProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@CrossOrigin(origins = "http://localhost:63342")
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            logger.info("Saving customer: {}", customerDTO);
            customerService.saveCustomer(customerDTO);
            logger.info("Customer saved successfully: {}", customerDTO.getId());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            logger.error("Failed to save customer: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerStatus getSelectedCustomer(@PathVariable("id") String customerId) {
        logger.info("Fetching customer with ID: {}", customerId);
        if (!RegexProcess.customerIdMatcher(customerId)) {
            logger.warn("Invalid Customer ID format: {}", customerId);
            return new SelectedCustomerErrorStatus(1, "Customer ID is not valid");
        }

        CustomerStatus customerStatus = customerService.getCustomer(customerId);
        if (customerStatus instanceof SelectedCustomerErrorStatus) {
            logger.warn("Customer not found for ID: {}", customerId);
        } else {
            logger.info("Customer retrieved successfully: {}", customerId);
        }
        return customerStatus;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomer() {
        logger.info("Fetching all customers");
        List<CustomerDTO> customers = customerService.getAllCustomers();
        logger.info("Total customers retrieved: {}", customers.size());
        return customers;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") String customerId) {
        logger.info("Attempting to delete customer with ID: {}", customerId);
        try {
            if (!RegexProcess.customerIdMatcher(customerId)) {
                logger.warn("Invalid Customer ID format: {}", customerId);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            customerService.deleteNote(customerId);
            logger.info("Customer deleted successfully: {}", customerId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CustomerNotFoundException e) {
            logger.error("Customer not found for ID: {}", customerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{customerId}")
    public ResponseEntity<Void> updateCustomer(@PathVariable("customerId") String customerId, @RequestBody CustomerDTO updateCustomerDTO) {
        logger.info("Updating customer with ID: {}", customerId);
        if (!RegexProcess.customerIdMatcher(customerId) || updateCustomerDTO == null) {
            logger.warn("Invalid input data for customer update");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            customerService.updateCustomer(customerId, updateCustomerDTO);
            logger.info("Customer updated successfully: {}", customerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            logger.error("Customer not found for ID: {}", customerId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
