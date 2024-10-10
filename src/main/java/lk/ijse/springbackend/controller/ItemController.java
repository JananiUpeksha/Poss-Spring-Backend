package lk.ijse.springbackend.controller;

import lk.ijse.springbackend.customStatusCode.SelectedItemErrorStatus;
import lk.ijse.springbackend.dto.ItemStatus;
import lk.ijse.springbackend.dto.impl.ItemDTO;
import lk.ijse.springbackend.exception.DataPersistException;
import lk.ijse.springbackend.exception.ItemNotFoundException;
import lk.ijse.springbackend.service.ItemService;
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
@RequestMapping("api/v1/item")
@CrossOrigin(origins = "http://localhost:63342")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveItem(@RequestBody ItemDTO itemDTO) {
        logger.info("Saving item: {}", itemDTO);
        try {
            itemService.saveItem(itemDTO);
            logger.info("Item saved successfully: {}", itemDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            logger.error("Error saving item: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while saving item: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemStatus getSelectedItem(@PathVariable("id") String itemId) {
        logger.info("Fetching item with ID: {}", itemId);
        if (!RegexProcess.ItemIdMatcher(itemId)) {
            logger.warn("Invalid Item ID: {}", itemId);
            return new SelectedItemErrorStatus(1, "Item ID is not valid");
        }
        return itemService.getItem(itemId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItems() {
        logger.info("Fetching all items");
        List<ItemDTO> items = itemService.getAllItems();
        logger.info("Total items retrieved: {}", items.size());
        return items;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable("id") String itemId) {
        logger.info("Attempting to delete item with ID: {}", itemId);
        try {
            if (!RegexProcess.ItemIdMatcher(itemId)) {
                logger.warn("Invalid Item ID: {}", itemId);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            itemService.deleteItem(itemId);
            logger.info("Item deleted successfully: {}", itemId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ItemNotFoundException e) {
            logger.error("Item not found for ID: {}", itemId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while deleting item: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateItem(@PathVariable("id") String itemId, @RequestBody ItemDTO updateItemDTO) {
        logger.info("Updating item with ID: {}", itemId);
        if (!RegexProcess.ItemIdMatcher(itemId) || updateItemDTO == null) {
            logger.warn("Invalid request to update item with ID: {}", itemId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            itemService.updateItem(itemId, updateItemDTO);
            logger.info("Item updated successfully: {}", itemId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ItemNotFoundException e) {
            logger.error("Item not found for ID: {}", itemId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while updating item: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
