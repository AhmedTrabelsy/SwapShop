package Requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePositionRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long productId;

    private Float longi;

    private Float lat;
}
