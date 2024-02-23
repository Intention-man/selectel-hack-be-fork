package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationPlanRequestDto {
    private Long donationPlanId;
    private Long bloodStationId;
    private Long cityId;
    private String bloodClass;
    private String planDate;
    private String paymentType;
    private String status;
    private boolean isOut;
    private int contentType;
    private int donation;
}
