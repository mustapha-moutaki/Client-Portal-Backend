package org.mustapha.ClientPortal.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import org.mustapha.ClientPortal.enums.ProductType;

import java.math.BigDecimal;

@Data
public class ProductDtoRequest {

    @NotBlank(message = "product name is required")
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal basePrice;

}
