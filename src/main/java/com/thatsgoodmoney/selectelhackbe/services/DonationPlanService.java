package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationPlanDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationPlanEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.DonationPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DonationPlanService {
    private final DonationPlanRepository donationPlanRepository;
    private final Mapper<DonationPlanEntity, DonationPlanDto> mapper;

    public DonationPlanDto save(DonationPlanDto donationPlanDto) {
        DonationPlanEntity donationPlanEntity = mapper.mapFrom(donationPlanDto);
        return mapper.mapTo(donationPlanRepository.save(donationPlanEntity));
    }

    public List<DonationPlanDto> findAllDonationPlans() {
        List<DonationPlanEntity> entities = (List<DonationPlanEntity>) donationPlanRepository.findAll();
        return entities.stream().map(mapper::mapTo).toList();
    }

    public Optional<DonationPlanDto> findById(Long donationPlanId) {
        Optional<DonationPlanEntity> optionalDonationPlanDto = donationPlanRepository.findById(donationPlanId);
        return optionalDonationPlanDto.map(mapper::mapTo);
    }

    public boolean isExists(Long pointId) {
        return donationPlanRepository.existsById(pointId);
    }

    public DonationPlanDto partialUpdate(Long donationPlanId, DonationPlanDto donationPlanDto) {
        donationPlanDto.setDonationPlanId(donationPlanId);
        return donationPlanRepository.findById(donationPlanId).map(existingDonationPlan -> {
            DonationPlanDto existingDonationPlanDto = mapper.mapTo(existingDonationPlan);
            Optional.of(donationPlanDto.getBloodClass()).ifPresent(existingDonationPlanDto::setBloodClass);
            Optional.of(donationPlanDto.getPlanDate()).ifPresent(existingDonationPlanDto::setPlanDate);
            Optional.of(donationPlanDto.getPaymentType()).ifPresent(existingDonationPlanDto::setPaymentType);
            Optional.of(donationPlanDto.getIsOut()).ifPresent(existingDonationPlanDto::setIsOut);
            Optional.of(donationPlanDto.getBloodStationDto()).ifPresent(existingDonationPlanDto::setBloodStationDto);
            return mapper.mapTo(donationPlanRepository.save(mapper.mapFrom(existingDonationPlanDto)));
        }).orElseThrow(() -> new RuntimeException("Donation Plan doesn't exist"));
    }

    public void delete(Long donationPlanId) {
        donationPlanRepository.deleteById(donationPlanId);
    }

}
