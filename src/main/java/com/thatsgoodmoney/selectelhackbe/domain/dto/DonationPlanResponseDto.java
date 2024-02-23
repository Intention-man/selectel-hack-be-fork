package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationPlanResponseDto {
    private Long donationPlanId;
    private String event;
    private Long bloodStationId;
    private BloodStationDto bloodStation;
    private Long cityId;
    private CityDto city;
    private String createdAt;
    private String updatedAt;
    private String bloodClass;
    private String planDate;
    private String paymentType;
    private String status;
    private boolean isOut;
    private int contentType;
    private int donation;
    private Long userId;
}
