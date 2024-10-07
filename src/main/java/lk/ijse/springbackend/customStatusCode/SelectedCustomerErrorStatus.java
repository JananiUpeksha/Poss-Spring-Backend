package lk.ijse.springbackend.customStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SelectedCustomerErrorStatus {
    private int statusCode;
    private String statusMessage;
}
