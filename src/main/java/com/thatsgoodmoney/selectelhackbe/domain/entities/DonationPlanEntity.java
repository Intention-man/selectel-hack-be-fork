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
@Table(name = "donation_plans")
public class DonationPlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationPlanId;
    @ManyToOne
    @JoinColumn(name = "blood_station_id", nullable = false)
    private BloodStationEntity bloodStation;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    private String bloodClass;
    private String planDate;
    private String paymentType;
    private String status;
    private Boolean isOut;
    private Integer contentType;
    private Integer donation;

}
