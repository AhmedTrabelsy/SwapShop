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
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long trackId;
    private Date createdAt;
    private Date updatedAt;
    @NotNull
    private Long proudctId;
}
