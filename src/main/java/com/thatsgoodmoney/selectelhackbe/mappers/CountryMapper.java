package com.thatsgoodmoney.selectelhackbe.mappers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.CountryDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.CountryEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class CountryMapper implements Mapper<CountryEntity, CountryDto> {
    private final ModelMapper modelMapper;

    public CountryMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public CountryDto mapTo(CountryEntity countryEntity) {
        return modelMapper.map(countryEntity, CountryDto.class);
    }

    @Override
    public CountryEntity mapFrom(CountryDto countryDto) {
        return modelMapper.map(countryDto, CountryEntity.class);
    }
}
