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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDAO customerDAO;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveCustomer(CustomerDTO customerDTO) {
        customerDTO.setId(AppUtill.generateCustomerId());
        CustomerEntity saveCustomer = customerDAO.save(mapping.toCustomerEntity(customerDTO));
        if (saveCustomer == null){
            throw new DataPersistException("Customer not saved");
        }
    }
    @Override
    public CustomerStatus getCustomer(String customerId) {
        if (customerDAO.existsById(customerId)) {
            var selectedCustomer = customerDAO.getReferenceById(customerId);
            return mapping.toCustomerDTO(selectedCustomer); // No need for casting now
        } else {
            return new SelectedCustomerErrorStatus(2, "Selected customer not found");
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return mapping.asCustomerDTOList( customerDAO.findAll());
    }

    @Override
    public void deleteNote(String customerId) {
        Optional<CustomerEntity> foundCustomer = customerDAO.findById(customerId);
        if (!foundCustomer.isPresent()){
            throw new CustomerNotFoundException("Customer not found by id " + customerId);
        }else {
            customerDAO.deleteById(customerId);
        }
    }

    @Override
    public void updateCustomer(String customerId, CustomerDTO updateCustomerDTO) {
        Optional<CustomerEntity> findCustomer = customerDAO.findById(customerId);
        if (!findCustomer.isPresent()) {
            throw new CustomerNotFoundException("Customer not found");
        } else {
            CustomerEntity customerEntity = findCustomer.get();
            customerEntity.setName(updateCustomerDTO.getName());
            customerEntity.setAddress(updateCustomerDTO.getAddress());
            customerEntity.setContact(updateCustomerDTO.getContact());
            customerDAO.save(customerEntity);
        }
    }


}
