package lk.ijse.springbackend.customStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SelectedItemErrorStatus {
    private int statusCode;
    private String statusMessage;
}
