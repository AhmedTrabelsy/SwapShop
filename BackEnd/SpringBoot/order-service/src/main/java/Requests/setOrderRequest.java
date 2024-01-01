package Requests;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
@Data
public class setOrderRequest {

    @NotNull
    private Long userId;
    @NotNull
    private String billingAdress;
    @NotNull
    private Long productId;
}
