package com.thatsgoodmoney.selectelhackbe.mappers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.CityDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.CityEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class CityMapper implements Mapper<CityEntity, CityDto> {
    private final ModelMapper modelMapper;

    public CityMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public CityDto mapTo(CityEntity cityEntity) {
        return modelMapper.map(cityEntity, CityDto.class);
    }

    @Override
    public CityEntity mapFrom(CityDto cityDto) {
        return modelMapper.map(cityDto, CityEntity.class);
    }
}
