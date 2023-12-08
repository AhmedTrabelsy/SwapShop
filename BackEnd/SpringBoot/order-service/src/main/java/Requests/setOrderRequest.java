package Requests;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.NonNull;

import java.util.Date;

public class setOrderRequest {
    @NonNull
    private Long id;
    @NonNull
    private Long userId;
    @NonNull
    private Long trackId;
    private Date createdAt;
    @NonNull
    private Long proudctId;
}
