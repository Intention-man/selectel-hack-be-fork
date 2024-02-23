package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationPlanDto {
    private Long donationPlanId;
    private BloodStationDto bloodStationDto;
    private UserDto userDto;
    private String bloodClass;
    private String planDate;
    private String paymentType;
    private String status;
    private Boolean isOut;
    private Integer contentType;
    private Integer donation;
}
