package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationRequestDto {
    private Long donationId;
    private Long userId;

    private Long bloodStationId;
    private Long imageId;
    private Long cityId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String donateAt;
    private String bloodClass;
    private String paymentType;
    private boolean isOut;
    private double volume;
    private double paymentCost;
    private String onModerationDate;
    private boolean withImage;
    private boolean createdUsingOcr;
}
