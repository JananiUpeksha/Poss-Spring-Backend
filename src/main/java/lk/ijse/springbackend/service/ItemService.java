package lk.ijse.springbackend.service;

import lk.ijse.springbackend.dto.ItemStatus;
import lk.ijse.springbackend.dto.impl.ItemDTO;

import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);

    ItemStatus getItem(String itemId);

    List<ItemDTO> getAllItems();

    void deleteItem(String itemId);

    void updateItem(String itemId, ItemDTO updateItemDTO);
}
