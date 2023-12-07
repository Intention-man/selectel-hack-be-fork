package com.webtut.dbwork.mappers.impl;

import com.webtut.dbwork.domain.dto.PointDto;
import com.webtut.dbwork.domain.entities.PointEntity;
import com.webtut.dbwork.mappers.Mapper;
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
