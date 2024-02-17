package com.thatsgoodmoney.selectelhackbe.mappers.impl;

import com.thatsgoodmoney.selectelhackbe.domain.dto.PointDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.PointEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PointMapperImpl implements Mapper<PointEntity, PointDto> {
    private final ModelMapper modelMapper;

    public PointMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public PointDto mapTo(PointEntity pointEntity) {
        return modelMapper.map(pointEntity, PointDto.class);
    }

    @Override
    public PointEntity mapFrom(PointDto pointDto) {
        return modelMapper.map(pointDto, PointEntity.class);
    }
}
