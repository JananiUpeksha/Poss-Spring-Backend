package lk.ijse.springbackend.customStatusCode;

import lk.ijse.springbackend.dto.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SelectedCustomerErrorStatus implements CustomerStatus {
    private int statusCode;
    private String statusMessage;
}
