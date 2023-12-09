package com.example.productsservice.projection;

import com.example.productsservice.entity.product;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
@Projection(name = "fullProduct",types = product.class)
public interface productProjection {
    public Long getId();
    public String getName();
    public String getDescription();
    public String getImage();
    public Double getPrice();
    public Date getCreated_at();
    public Date getUpdated_at();
    public Date getDeleted_at();
}
