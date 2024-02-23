package com.thatsgoodmoney.selectelhackbe.mappers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationPlanRequestDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationPlanEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DonationPlanMapper implements Mapper<DonationPlanEntity, DonationPlanRequestDto> {
    private final ModelMapper modelMapper;

    public DonationPlanMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public DonationPlanRequestDto mapTo(DonationPlanEntity donationPlanEntity) {
        return modelMapper.map(donationPlanEntity, DonationPlanRequestDto.class);
    }

    @Override
    public DonationPlanEntity mapFrom(DonationPlanRequestDto donationPlanRequestDto) {
        return modelMapper.map(donationPlanRequestDto, DonationPlanEntity.class);
    }
}
