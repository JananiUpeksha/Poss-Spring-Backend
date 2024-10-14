package lk.ijse.springbackend.entity.impl;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderItemId implements Serializable {
    private String orderId;
    private String itemId;

    public OrderItemId() {}

    public OrderItemId(String orderId, String itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemId)) return false;

        OrderItemId that = (OrderItemId) o;

        return orderId != null && orderId.equals(that.orderId) &&
                itemId != null && itemId.equals(that.itemId);
    }

    @Override
    public int hashCode() {
        return 31 * (orderId != null ? orderId.hashCode() : 0) +
                (itemId != null ? itemId.hashCode() : 0);
    }
}