package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodStationDto {
    private Long id;
    private Long cityId;
    private String hasBloodGroup;
    // private ???[] schedule;
    private String lat;
    private String lng;
    private String bloodGroup;
    private String bloodStatus;
    private String title;
    private String parserUrl;
    private boolean isGetFromParser;
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
    private boolean withoutRegistration;
    private boolean withTyping;
    private boolean forMoscow;
    private boolean closed;
    private int priority;
}
