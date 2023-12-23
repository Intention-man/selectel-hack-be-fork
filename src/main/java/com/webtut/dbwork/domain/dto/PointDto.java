package com.webtut.dbwork.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointDto {
    private Long pointId;

    private double x;
    private double y;
    private double r;
    private boolean inside;
    private Long userId;
}
