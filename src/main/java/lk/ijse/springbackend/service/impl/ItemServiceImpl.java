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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveItem(ItemDTO itemDTO) {
        itemDTO.setId(AppUtill.generateItemId());
        ItemEntity savedItem = itemDAO.save(mapping.toItemEntity(itemDTO));
        if (savedItem == null) {
            throw new DataPersistException("Item not saved");
        }
    }

    @Override
    public ItemStatus getItem(String itemId) {
        if (itemDAO.existsById(itemId)) {
            var selectedItem = itemDAO.getReferenceById(itemId);
            return mapping.toItemDTO(selectedItem);
        } else {
            return new SelectedItemErrorStatus(2, "Selected item not found");
        }
    }

    @Override
    public List<ItemDTO> getAllItems() {
        return mapping.asItemDTOList(itemDAO.findAll());
    }

    @Override
    public void deleteItem(String itemId) {
        Optional<ItemEntity> foundItem = itemDAO.findById(itemId);
        if (!foundItem.isPresent()) {
            throw new ItemNotFoundException("Item not found by id " + itemId);
        } else {
            itemDAO.deleteById(itemId);
        }
    }

    @Override
    public void updateItem(String itemId, ItemDTO updateItemDTO) {
        Optional<ItemEntity> foundItem = itemDAO.findById(itemId);
        if (!foundItem.isPresent()) {
            throw new ItemNotFoundException("Item not found");
        } else {
            ItemEntity itemEntity = foundItem.get();
            itemEntity.setName(updateItemDTO.getName());
            itemEntity.setPrice(updateItemDTO.getPrice());
            itemEntity.setQty(updateItemDTO.getQty());
            itemDAO.save(itemEntity);
        }
    }
}
