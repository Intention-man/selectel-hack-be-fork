package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationDto {
    private Long donationId;
    private UserDto userDto;
    private BloodStationDto bloodStationDto;
    private String firstName;
    private String lastName;
    private String middleName;
    private String donateAt;
    private String bloodClass;
    private String paymentType;
    private Boolean isOut;
    private Double volume;
    private Double paymentCost;
    private String onModerationDate;
    private Boolean withImage;
    private Boolean createdUsingOcr;
    private Long imageId;
}
