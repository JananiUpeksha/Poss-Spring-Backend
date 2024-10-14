package lk.ijse.springbackend.controller;

import lk.ijse.springbackend.dto.impl.OrderDTO;
import lk.ijse.springbackend.exception.DataPersistException;
import lk.ijse.springbackend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/order")
@CrossOrigin(origins = "http://localhost:63342")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveOrder(@RequestBody OrderDTO orderDTO) {
        try {
            logger.info("Saving order: {}", orderDTO);
            orderService.saveOrder(orderDTO);
            logger.info("Order saved successfully: {}", orderDTO.getOrderId());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            logger.error("Failed to save order: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Internal server error: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
