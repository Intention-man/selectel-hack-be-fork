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
    private Long id;
    private Long userId;

    private int bloodStationId;
    private int imageId;
    private int cityId;
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
