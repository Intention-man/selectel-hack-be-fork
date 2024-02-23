package com.thatsgoodmoney.selectelhackbe.mappers.impl;

import com.thatsgoodmoney.selectelhackbe.domain.dto.BloodStationDto;
import com.thatsgoodmoney.selectelhackbe.domain.dto.CityDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.BloodStationEntity;
import com.thatsgoodmoney.selectelhackbe.domain.entities.CityEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class BloodStationMapper implements Mapper<BloodStationEntity, BloodStationDto> {
    private final ModelMapper modelMapper;

    public BloodStationMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public BloodStationDto mapTo(BloodStationEntity bloodStationEntity) {
        return modelMapper.map(bloodStationEntity, BloodStationDto.class);
    }

    @Override
    public BloodStationEntity mapFrom(BloodStationDto bloodStationDto) {
        return modelMapper.map(bloodStationDto, BloodStationEntity.class);
    }
}
