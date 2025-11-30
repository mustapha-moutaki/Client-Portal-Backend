package org.mustapha.ClientPortal.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDtoResponse {

    private Long id;
    private String name;
    private BigDecimal price;

}
