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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "blood_station_id", nullable = false)
    private BloodStationEntity bloodStation;
    private int imageId;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;
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
