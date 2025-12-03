package org.mustapha.ClientPortal.dto.response;

import lombok.Data;
import org.mustapha.ClientPortal.enums.ProductType;

import java.math.BigDecimal;

@Data
public class ProductDtoResponse {

    private Long id;
    private String name;
    private BigDecimal basePrice;
    private ProductType type;
    private String description;

}
