package com.thatsgoodmoney.selectelhackbe.mappers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationRequestDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DonationMapperImpl implements Mapper<DonationEntity, DonationRequestDto> {
    private final ModelMapper modelMapper;

    public DonationMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public DonationRequestDto mapTo(DonationEntity donationEntity) {
        return modelMapper.map(donationEntity, DonationRequestDto.class);
    }

    @Override
    public DonationEntity mapFrom(DonationRequestDto donationRequestDto) {
        return modelMapper.map(donationRequestDto, DonationEntity.class);
    }
}
