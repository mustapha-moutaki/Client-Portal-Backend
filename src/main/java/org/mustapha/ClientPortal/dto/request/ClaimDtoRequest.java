//package org.mustapha.ClientPortal.dto.request;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//import org.mustapha.ClientPortal.enums.ClaimStatus;
//
//@Data
//public class ClaimDtoRequest {
//
//    @NotNull(message = "client is required")
//    private Long clientId;
//    @NotBlank(message = "title is required")
//    private String title;
//    private ClaimStatus status = ClaimStatus.IN_REVIEW;
//
//    private String description;
//
//    private String fileUrl; // PDF, JPG, PNG,  maps to attachmentUrl
//
//    private Long assignedStaffId;
//}
package org.mustapha.ClientPortal.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ClaimDtoRequest {

    @NotNull(message = "Client ID is required")
    private Long clientId;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private String status;

    private LocalDate claimDate;


    private String fileUrl;


     private Long assignedStaffId;
}