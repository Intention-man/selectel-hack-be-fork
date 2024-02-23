package com.thatsgoodmoney.selectelhackbe.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;
    private String hasBloodGroup;
    @OneToMany(mappedBy = "bloodStationEntity", cascade = CascadeType.ALL)
    private List<Schedule> schedule;
    @OneToMany(mappedBy = "bloodStationEntity", cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;
    private Double lat;
    private Double lng;
//    @Type(type = "jsonb")
//    @Column(columnDefinition = "jsonb")
//    private List<String> bloodGroup;
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
