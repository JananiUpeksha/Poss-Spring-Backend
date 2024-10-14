package lk.ijse.springbackend.service.impl;

import lk.ijse.springbackend.customStatusCode.SelectedCustomerErrorStatus;
import lk.ijse.springbackend.dao.CustomerDAO;
import lk.ijse.springbackend.dto.CustomerStatus;
import lk.ijse.springbackend.dto.impl.CustomerDTO;
import lk.ijse.springbackend.entity.impl.CustomerEntity;
import lk.ijse.springbackend.exception.CustomerNotFoundException;
import lk.ijse.springbackend.exception.DataPersistException;
import lk.ijse.springbackend.service.CustomerService;
import lk.ijse.springbackend.utill.AppUtill;
import lk.ijse.springbackend.utill.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        logger.info("Saving customer: {}", customerDTO);
        customerDTO.setId(AppUtill.generateCustomerId());
        CustomerEntity savedCustomer = customerDAO.save(mapping.toCustomerEntity(customerDTO));

        if (savedCustomer == null) {
            logger.error("Failed to save customer: {}", customerDTO);
            throw new DataPersistException("Customer not saved");
        }
        logger.info("Customer saved successfully: {}", savedCustomer.getId());
    }


    @Override
    public CustomerStatus getCustomer(String customerId) {
        logger.info("Fetching customer with ID: {}", customerId);
        if (customerDAO.existsById(customerId)) {
            CustomerEntity selectedCustomer = customerDAO.getReferenceById(customerId);
            logger.info("Customer retrieved successfully: {}", customerId);
            return mapping.toCustomerDTO(selectedCustomer);
        } else {
            logger.warn("Customer not found for ID: {}", customerId);
            return new SelectedCustomerErrorStatus(2, "Selected customer not found");
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        logger.info("Fetching all customers");
        List<CustomerDTO> customers = mapping.asCustomerDTOList(customerDAO.findAll());
        logger.info("Total customers retrieved: {}", customers.size());
        return customers;
    }

    @Override
    public void deleteNote(String customerId) {
        logger.info("Attempting to delete customer with ID: {}", customerId);
        Optional<CustomerEntity> foundCustomer = customerDAO.findById(customerId);

        if (!foundCustomer.isPresent()) {
            logger.error("Customer not found for ID: {}", customerId);
            throw new CustomerNotFoundException("Customer not found by id " + customerId);
        } else {
            customerDAO.deleteById(customerId);
            logger.info("Customer deleted successfully: {}", customerId);
        }
    }

    @Override
    public void updateCustomer(String customerId, CustomerDTO updateCustomerDTO) {
        logger.info("Updating customer with ID: {}", customerId);
        Optional<CustomerEntity> foundCustomer = customerDAO.findById(customerId);

        if (!foundCustomer.isPresent()) {
            logger.error("Customer not found for ID: {}", customerId);
            throw new CustomerNotFoundException("Customer not found");
        } else {
            CustomerEntity customerEntity = foundCustomer.get();
            customerEntity.setName(updateCustomerDTO.getName());
            customerEntity.setAddress(updateCustomerDTO.getAddress());
            customerEntity.setContact(updateCustomerDTO.getContact());
            customerDAO.save(customerEntity);
            logger.info("Customer updated successfully: {}", customerId);
        }
    }
}
