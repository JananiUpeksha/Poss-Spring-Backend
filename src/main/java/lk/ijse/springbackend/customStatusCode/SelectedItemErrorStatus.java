package lk.ijse.springbackend.customStatusCode;

import lk.ijse.springbackend.dto.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SelectedItemErrorStatus implements ItemStatus {
    private int statusCode;
    private String statusMessage;
}
