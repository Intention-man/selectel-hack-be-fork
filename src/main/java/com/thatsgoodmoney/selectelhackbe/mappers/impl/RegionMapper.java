package com.thatsgoodmoney.selectelhackbe.mappers.impl;

import com.thatsgoodmoney.selectelhackbe.domain.dto.RegionDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.RegionEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper implements Mapper<RegionEntity, RegionDto> {
    private final ModelMapper modelMapper;

    public RegionMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public RegionDto mapTo(RegionEntity regionEntity) {
        return modelMapper.map(regionEntity, RegionDto.class);
    }

    @Override
    public RegionEntity mapFrom(RegionDto regionDto) {
        return modelMapper.map(regionDto, RegionEntity.class);
    }
}
