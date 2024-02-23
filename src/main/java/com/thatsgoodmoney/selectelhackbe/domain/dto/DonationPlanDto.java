package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DonationPlanDto {
    private Long id;
    private String event;

}
