package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationRequestDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.DonationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class DonationServiceImpl implements DonationService {
    private final DonationRepository donationRepository;
    private final Mapper<DonationEntity, DonationRequestDto> donationMapper;

    public DonationServiceImpl(DonationRepository donationRepository, Mapper<DonationEntity, DonationRequestDto> donationMapper) {
        this.donationRepository = donationRepository;
        this.donationMapper = donationMapper;
    }

    @Override
    public DonationRequestDto save(DonationRequestDto donationRequestDto) {
        DonationEntity donationEntity = donationMapper.mapFrom(donationRequestDto);
        return donationMapper.mapTo(donationRepository.save(donationEntity));
    }

    @Override
    public List<DonationRequestDto> findAllUserDonations(Long userId) {
        return StreamSupport.stream(donationRepository.findAll().spliterator(), false)
                .filter(donationEntity -> Objects.equals(donationEntity.getUser().getUserId(), userId))
                .map(donationMapper::mapTo).toList();
    }

    @Override
    public Optional<DonationRequestDto> findById(Long donationId) {
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
    public DonationRequestDto partialUpdate(Long donationId, DonationRequestDto donationRequestDto) {
        donationRequestDto.setDonationId(donationId);
        return donationRepository.findById(donationId).map(existingDonation -> {

            Optional.of(donationRequestDto.getBloodClass()).ifPresent(existingDonation::setBloodClass);
            Optional.of(donationRequestDto.getDonateAt()).ifPresent(existingDonation::setDonateAt);
            Optional.of(donationRequestDto.getPaymentType()).ifPresent(existingDonation::setPaymentType);
            Optional.of(donationRequestDto.isOut()).ifPresent(existingDonation::setOut);
            //Optional.of(donationDto.getBloodStationId()).ifPresent(existingDonation::setBloodStation);
            Optional.of(donationRequestDto.isWithImage()).ifPresent(existingDonation::setWithImage);
            //Optional.of(donationDto.getCityId()).ifPresent(existingDonation::setCity);

            return donationMapper.mapTo(donationRepository.save(existingDonation));
        }).orElseThrow(() -> new RuntimeException("Donation doesn't exists"));
    }

    @Override
    public void delete(Long donationId) {
        donationRepository.deleteById(donationId);
    }
}
