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

            Optional.of(donationPlanDto.getBloodClass()).ifPresent(existingDonationPlan::setBloodClass);
            Optional.of(donationPlanDto.getPlanDate()).ifPresent(existingDonationPlan::setPlanDate);
            Optional.of(donationPlanDto.getPaymentType()).ifPresent(existingDonationPlan::setPaymentType);
            Optional.of(donationPlanDto.getIsOut()).ifPresent(existingDonationPlan::setIsOut);
            return mapper.mapTo(donationPlanRepository.save(existingDonationPlan));
        }).orElseThrow(() -> new RuntimeException("Donation Plan doesn't exist"));
    }

    public void delete(Long donationPlanId) {
        donationPlanRepository.deleteById(donationPlanId);
    }

}
