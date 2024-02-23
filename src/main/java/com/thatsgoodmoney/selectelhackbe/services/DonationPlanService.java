package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationPlanRequestDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationPlanEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.DonationPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationPlanService {
    private final DonationPlanRepository donationPlanRepository;
    private final Mapper<DonationPlanEntity, DonationPlanRequestDto> mapper;

    public DonationPlanService(DonationPlanRepository donationPlanRepository, Mapper<DonationPlanEntity, DonationPlanRequestDto> mapper) {
        this.donationPlanRepository = donationPlanRepository;
        this.mapper = mapper;
    }

    public DonationPlanRequestDto save(DonationPlanRequestDto donationPlanRequestDto) {
        DonationPlanEntity donationPlanEntity = mapper.mapFrom(donationPlanRequestDto);
        return mapper.mapTo(donationPlanRepository.save(donationPlanEntity));
    }

    public List<DonationPlanRequestDto> findAllDonationPlans() {
        List<DonationPlanEntity> entities = (List<DonationPlanEntity>) donationPlanRepository.findAll();
        return entities.stream().map(mapper::mapTo).toList();
    }

    public Optional<DonationPlanRequestDto> findById(Long donationPlanId) {
        Optional<DonationPlanEntity> optionalDonationPlanDto = donationPlanRepository.findById(donationPlanId);
        return optionalDonationPlanDto.map(mapper::mapTo);
    }

    public boolean isExists(Long pointId) {
        return donationPlanRepository.existsById(pointId);
    }

    public DonationPlanRequestDto partialUpdate(Long donationPlanId, DonationPlanRequestDto donationPlanRequestDto) {
        donationPlanRequestDto.setDonationPlanId(donationPlanId);
        return donationPlanRepository.findById(donationPlanId).map(existingDonationPlan -> {

            Optional.of(donationPlanRequestDto.getBloodClass()).ifPresent(existingDonationPlan::setBloodClass);
            Optional.of(donationPlanRequestDto.getPlanDate()).ifPresent(existingDonationPlan::setPlanDate);
            Optional.of(donationPlanRequestDto.getPaymentType()).ifPresent(existingDonationPlan::setPaymentType);
            Optional.of(donationPlanRequestDto.isOut()).ifPresent(existingDonationPlan::setOut);
            //Optional.of(donationPlanDto.getBloodStationId()).ifPresent(existingDonationPlan::setBloodStation);
            //Optional.of(donationPlanDto.getCityId()).ifPresent(existingDonationPlan::setCity);
            return mapper.mapTo(donationPlanRepository.save(existingDonationPlan));
        }).orElseThrow(() -> new RuntimeException("Donation Plan doesn't exist"));
    }

    public void delete(Long donationPlanId) {
        donationPlanRepository.deleteById(donationPlanId);
    }

}
