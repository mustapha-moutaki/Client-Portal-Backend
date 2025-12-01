package org.mustapha.ClientPortal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDtoRequest {

    @NotBlank(message = "product name is required")
    private String name;

    private String Description;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

}
