package com.thatsgoodmoney.selectelhackbe.domain.dto;

import com.thatsgoodmoney.selectelhackbe.domain.entities.PhoneNumber;
import com.thatsgoodmoney.selectelhackbe.domain.entities.Schedule;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodStationDto {
    private Long bloodStationId;
    private Long cityId;
    private String hasBloodGroup;

    private List<Schedule> schedule;
    private List<PhoneNumber> phoneNumbers;
    private Double lat;
    private Double lng;
//    private String bloodGroup;
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
