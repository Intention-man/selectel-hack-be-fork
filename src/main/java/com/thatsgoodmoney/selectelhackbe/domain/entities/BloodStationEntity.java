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
@Table(name = "blood_stations")
public class BloodStationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bloodStationId;
    private Long cityId;
    private String hasBloodGroup;
    private Double lat;
    private Double lng;
    private String bloodStatus;
    private String title;
    private String parserUrl;
    private Boolean isGetFromParser;
    private String oPlus;
    private String oMinus;
    private String aPlus;
    private String aMinus;
    private String bPlus;
    private String bMinus;
    private String abPlus;
    private String abMinus;
    private String blood;
    private String plasma;
    private String platelets;
    private String erythrocytes;
    private String leukocytes;
    private String address;
    private String site;
    private String phones;
    private String email;
    private String worktime;
    private Boolean withoutRegistration;
    private Boolean withTyping;
    private Boolean forMoscow;
    private Boolean closed;
    private Integer priority;
}
