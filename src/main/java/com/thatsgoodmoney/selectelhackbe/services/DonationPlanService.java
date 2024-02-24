package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationPlanDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationPlanEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.DonationPlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
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

    public DonationPlanDto findClosestPlanByUser(Long userId) {
        List<DonationPlanDto> usersDonationPlans = ((List<DonationPlanEntity>) donationPlanRepository.findAll())
                .stream()
                .filter(donationPlanEntity -> Objects.equals(donationPlanEntity.getUser().getUserId(), userId))
                .map(mapper::mapTo).toList();
        LocalDate currentDate = LocalDate.now();
        long minDifference = Long.MAX_VALUE;
        DonationPlanDto closestDonationPlan = null;
        for (DonationPlanDto donationPlan: usersDonationPlans) {
            LocalDate donationDate = LocalDate.parse(donationPlan.getPlanDate());
            if (donationDate.isAfter(currentDate) && daysBetween(donationDate, currentDate) < minDifference) {
                minDifference = daysBetween(donationDate, currentDate);
                closestDonationPlan = donationPlan;
            }
        }
        return closestDonationPlan;
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

    private long daysBetween(LocalDate ld1, LocalDate ld2) {
        return Math.abs(ChronoUnit.DAYS.between(ld1, ld2));
    }

}
