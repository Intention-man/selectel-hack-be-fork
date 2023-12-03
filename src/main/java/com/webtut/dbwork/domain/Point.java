package com.webtut.dbwork.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Point {
    private Long pointId;

    private double x;
    private double y;
    private double r;

    private Long userId;
}
