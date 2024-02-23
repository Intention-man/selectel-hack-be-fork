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
    private Long id;
    private String title;
    private String slug;
    private Long regionId;
    private Long countryId;
    private int priority;
    private String lat;
    private String lng;
}
