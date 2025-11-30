package org.mustapha.ClientPortal.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientProductDtoRequest {

    @NotNull
    private Long clientId;

    @NotNull
    private Long productId;

    @NotNull
    private BigDecimal sellingPrice;

}
