package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.DonationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DonationServiceImpl implements DonationService {
    private final DonationRepository donationRepository;
    private final Mapper<DonationEntity, DonationDto> donationMapper;

    @Override
    public DonationDto save(DonationDto donationDto) {
        DonationEntity donationEntity = donationMapper.mapFrom(donationDto);
        return donationMapper.mapTo(donationRepository.save(donationEntity));
    }

    @Override
    public List<DonationDto> findAllUserDonations(Long userId) {
        return StreamSupport.stream(donationRepository.findAll().spliterator(), false)
                .filter(donationEntity -> Objects.equals(donationEntity.getUser().getUserId(), userId))
                .map(donationMapper::mapTo).toList();
    }

    @Override
    public Optional<DonationDto> findById(Long donationId) {
        Optional<DonationEntity> optionalDonationDto = donationRepository.findById(donationId);
        return optionalDonationDto.map(donationMapper::mapTo);
    }

    @Override
    public boolean isExists(Long pointId) {
        return donationRepository.existsById(pointId);
    }

    @Override
    public boolean isExists(String donateAt) {
        return donationRepository.existsByDonateAt(donateAt);
    }


    @Override
    public DonationDto partialUpdate(Long donationId, DonationDto donationDto) {
        donationDto.setDonationId(donationId);
        return donationRepository.findById(donationId).map(existingDonation -> {

            Optional.of(donationDto.getBloodClass()).ifPresent(existingDonation::setBloodClass);
            Optional.of(donationDto.getDonateAt()).ifPresent(existingDonation::setDonateAt);
            Optional.of(donationDto.getPaymentType()).ifPresent(existingDonation::setPaymentType);
            Optional.of(donationDto.getIsOut()).ifPresent(existingDonation::setIsOut);
            Optional.of(donationDto.getWithImage()).ifPresent(existingDonation::setWithImage);


            return donationMapper.mapTo(donationRepository.save(existingDonation));
        }).orElseThrow(() -> new RuntimeException("Donation doesn't exists"));
    }

    @Override
    public void delete(Long donationId) {
        donationRepository.deleteById(donationId);
    }
}
