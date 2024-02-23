package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationResponseDto {
    private Long donationId;
    private Long bloodStationId;
    private BloodStationDto bloodStation;
    private String image;
    private Long imageId;
    private Long cityId;
    private CityDto city;
    private String legacyImage;
    private String hasReply;
    private String replyViewed;
    private String allowedModify;
    private String firstName;
    private String lastName;
    private String middleName;
    private String donateAt;
    private String rejectReason;
    private String bloodClass;
    private String paymentType;
    private boolean isOut;
    private double volume;
    private double paymentCost;
    private String onModerationDate;
    private boolean withImage;
    private boolean createdUsingOcr;
}
