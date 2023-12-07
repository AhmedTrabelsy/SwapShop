package Requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;
@Data
public class SetPositionRequest {
    @NotNull
    @Min(1)
    private Long userId;
    @NotNull
    @Min(1)
    private Long productId;
    @NotNull
    private Float longi;
    @NotNull
    private Float lat;
}
