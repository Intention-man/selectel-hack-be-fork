package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityDto {
    private Long cityId;
    private String title;
    private String slug;
    private RegionDto regionDto;
    private CountryDto countryDto;
    private Integer priority;
    private Double lat;
    private Double lng;
}
