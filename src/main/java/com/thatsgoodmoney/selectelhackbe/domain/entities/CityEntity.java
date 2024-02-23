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
@Table(name = "cities")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;
    private String title;
    private String slug;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region;
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CountryEntity country;
    private Integer priority;
    private Double lat;
    private Double lng;
}
