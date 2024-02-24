package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTopDto {
    private Long userId;
    private String firstName;
    private Long count;
    private Long blood;
    private Long plasma;
    private Long platelets;
    private Long erythrocytes;
    private Long granulocytes;

    public UserTopDto(BloodTypesDto bloodTypesDto) {
        this.blood = bloodTypesDto.getBlood();
        this.plasma = bloodTypesDto.getPlasma();
        this.platelets = bloodTypesDto.getPlatelets();
        this.erythrocytes = bloodTypesDto.getErythrocytes();
        this.granulocytes = bloodTypesDto.getGranulocytes();
        this.count = blood + plasma + platelets + erythrocytes + granulocytes;
    }
}
