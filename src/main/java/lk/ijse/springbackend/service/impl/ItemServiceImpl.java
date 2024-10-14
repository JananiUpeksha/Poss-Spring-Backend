package lk.ijse.springbackend.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.springbackend.customStatusCode.SelectedItemErrorStatus;
import lk.ijse.springbackend.dao.ItemDAO;
import lk.ijse.springbackend.dto.ItemStatus;
import lk.ijse.springbackend.dto.impl.ItemDTO;
import lk.ijse.springbackend.entity.impl.ItemEntity;
import lk.ijse.springbackend.exception.DataPersistException;
import lk.ijse.springbackend.exception.ItemNotFoundException;
import lk.ijse.springbackend.service.ItemService;
import lk.ijse.springbackend.utill.AppUtill;
import lk.ijse.springbackend.utill.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveItem(ItemDTO itemDTO) {
        logger.info("Saving item: {}", itemDTO);
        itemDTO.setId(AppUtill.generateItemId());
        ItemEntity savedItem = itemDAO.save(mapping.toItemEntity(itemDTO));
        if (savedItem == null) {
            logger.error("Failed to save item: {}", itemDTO);
            throw new DataPersistException("Item not saved");
        }
        logger.info("Item saved successfully: {}", savedItem);
    }

    @Override
    public ItemStatus getItem(String itemId) {
        logger.info("Fetching item with ID: {}", itemId);
        if (itemDAO.existsById(itemId)) {
            var selectedItem = itemDAO.getReferenceById(itemId);
            logger.info("Item found: {}", selectedItem);
            return mapping.toItemDTO(selectedItem);
        } else {
            logger.warn("Item not found for ID: {}", itemId);
            return new SelectedItemErrorStatus(2, "Selected item not found");
        }
    }

    @Override
    public List<ItemDTO> getAllItems() {
        logger.info("Fetching all items");
        List<ItemDTO> items = mapping.asItemDTOList(itemDAO.findAll());
        logger.info("Total items retrieved: {}", items.size());
        return items;
    }

    @Override
    public void deleteItem(String itemId) {
        logger.info("Attempting to delete item with ID: {}", itemId);
        Optional<ItemEntity> foundItem = itemDAO.findById(itemId);
        if (!foundItem.isPresent()) {
            logger.error("Item not found for ID: {}", itemId);
            throw new ItemNotFoundException("Item not found by id " + itemId);
        } else {
            itemDAO.deleteById(itemId);
            logger.info("Item deleted successfully: {}", itemId);
        }
    }

    @Override
    public void updateItem(String itemId, ItemDTO updateItemDTO) {
        logger.info("Updating item with ID: {}", itemId);
        Optional<ItemEntity> foundItem = itemDAO.findById(itemId);
        if (!foundItem.isPresent()) {
            logger.error("Item not found for ID: {}", itemId);
            throw new ItemNotFoundException("Item not found");
        } else {
            ItemEntity itemEntity = foundItem.get();
            itemEntity.setName(updateItemDTO.getName());
            itemEntity.setPrice(updateItemDTO.getPrice());
            itemEntity.setQty(updateItemDTO.getQty());
            itemDAO.save(itemEntity);
            logger.info("Item updated successfully: {}", itemEntity);
        }
    }


    @Override
    public void updateItemQuantity(String id, Integer qty) {
        logger.info("Updating quantity for item ID: {} by {}", id, qty);
        ItemEntity itemEntity = itemDAO.findById(id)
                .orElseThrow(() -> {
                    logger.error("Item not found for ID: {}", id);
                    return new ItemNotFoundException("Item not found by id " + id);
                });

        if (itemEntity.getQty() + qty < 0) {
            logger.error("Insufficient quantity for item ID: {}. Current: {}, Requested: {}", id, itemEntity.getQty(), qty);
            throw new IllegalArgumentException("Insufficient quantity for item ID: " + id);
        }

        itemEntity.setQty(itemEntity.getQty() + qty);
        itemDAO.save(itemEntity);
        logger.info("Item quantity updated successfully: {}", itemEntity);
    }
}
