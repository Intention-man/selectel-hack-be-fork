package com.thatsgoodmoney.selectelhackbe.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class BloodTypesDto {
    private Long blood;
    private Long plasma;
    private Long platelets;
    private Long erythrocytes;
    private Long granulocytes;

    public BloodTypesDto() {
        this.blood = 0L;
        this.plasma = 0L;
        this.platelets = 0L;
        this.erythrocytes = 0L;
        this.granulocytes = 0L;
    }

    public void incBlood() {
        blood++;
    }

    public void incPlasma() {
        plasma++;
    }

    public void incPlatelets() {
        platelets++;
    }

    public void incErythrocytes() {
        erythrocytes++;
    }

    public void incGranulocytes() {
        granulocytes++;
    }
}
