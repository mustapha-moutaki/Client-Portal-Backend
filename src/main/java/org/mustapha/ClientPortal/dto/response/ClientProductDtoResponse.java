package org.mustapha.ClientPortal.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ClientProductDtoResponse {

    private Long id;
    private Long clientId;
    private Long productId;
    private BigDecimal sellingPrice;

}
