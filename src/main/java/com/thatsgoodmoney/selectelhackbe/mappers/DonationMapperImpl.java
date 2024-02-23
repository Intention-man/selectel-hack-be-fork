package com.thatsgoodmoney.selectelhackbe.mappers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DonationMapperImpl implements Mapper<DonationEntity, DonationDto> {
    private final ModelMapper modelMapper;

    public DonationMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public DonationDto mapTo(DonationEntity donationEntity) {
        return modelMapper.map(donationEntity, DonationDto.class);
    }

    @Override
    public DonationEntity mapFrom(DonationDto donationDto) {
        return modelMapper.map(donationDto, DonationEntity.class);
    }
}
