package com.thatsgoodmoney.selectelhackbe.mappers.impl;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationPlanDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationPlanEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DonationPlanMapper implements Mapper<DonationPlanEntity, DonationPlanDto> {
    private final ModelMapper modelMapper;

    public DonationPlanMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public DonationPlanDto mapTo(DonationPlanEntity donationPlanEntity) {
        return modelMapper.map(donationPlanEntity, DonationPlanDto.class);
    }

    @Override
    public DonationPlanEntity mapFrom(DonationPlanDto donationPlanDto) {
        return modelMapper.map(donationPlanDto, DonationPlanEntity.class);
    }
}
