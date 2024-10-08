package lk.ijse.springbackend.utill;

import java.util.Base64;
import java.util.UUID;

public class AppUtill {
    public static String generateCustomerId(){
        return "Customer-"+ UUID.randomUUID();
    }
    public static String generateItemId(){
        return "Item-"+ UUID.randomUUID();
    }
    public static String generateOrderId(){
        return "Order-"+ UUID.randomUUID();
    }

}
