package com.thatsgoodmoney.selectelhackbe.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "donations")
public class DonationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;

    private Long userId;
    private Long bloodStationId;

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
