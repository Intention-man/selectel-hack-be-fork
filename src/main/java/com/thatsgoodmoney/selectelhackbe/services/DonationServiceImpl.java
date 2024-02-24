package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.*;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationEntity;
import com.thatsgoodmoney.selectelhackbe.domain.entities.DonationPlanEntity;
import com.thatsgoodmoney.selectelhackbe.domain.entities.UserEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.mappers.UserMapperImpl;
import com.thatsgoodmoney.selectelhackbe.repositories.DonationRepository;
import com.thatsgoodmoney.selectelhackbe.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DonationServiceImpl implements DonationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final Mapper<DonationEntity, DonationDto> donationMapper;
    private final UserMapperImpl userMapper;

    @Override
    public DonationDto save(DonationDto donationDto) {
        DonationEntity donationEntity = donationMapper.mapFrom(donationDto);
        return donationMapper.mapTo(donationRepository.save(donationEntity));
    }

    @Override
    public List<DonationDto> findAllUserDonations(Long userId) {
        return StreamSupport.stream(donationRepository.findAll().spliterator(), false)
                .filter(donationEntity -> Objects.equals(donationEntity.getUserId(), userId))
                .map(donationMapper::mapTo).toList();
    }

    @Override
    public Optional<DonationDto> findById(Long donationId) {
        Optional<DonationEntity> optionalDonationDto = donationRepository.findById(donationId);
        return optionalDonationDto.map(donationMapper::mapTo);
    }

    public BloodTypesDto findUsersDonationsByType(Long userId) {
        List<DonationDto> usersDonations = ((List<DonationEntity>) donationRepository.findAll())
                .stream()
                .filter(donationEntity -> Objects.equals(donationEntity.getUserId(), userId))
                .map(donationMapper::mapTo).toList();
        BloodTypesDto types = new BloodTypesDto();
        for (DonationDto donationDto: usersDonations) {
            if (donationDto.getBloodClass().equals("blood")) types.incBlood();
            if (donationDto.getBloodClass().equals("plasma")) types.incPlasma();
            if (donationDto.getBloodClass().equals("platelets")) types.incPlatelets();
            if (donationDto.getBloodClass().equals("erythrocytes")) types.incErythrocytes();
            if (donationDto.getBloodClass().equals("granulocytes")) types.incGranulocytes();
        }
        return types;
    }

    public boolean isHonoraryDonor(Long userId) {
        BloodTypesDto types = findUsersDonationsByType(userId);
        long blood = types.getBlood();
        long plasma = types.getPlasma();
        return (blood >= 40) || (plasma >= 60) || (blood >= 25 && (plasma + blood) >= 40) || (blood < 25 && (plasma + blood) >= 60);
    }

    public List<UserTopDto> getUsersTop() {
        List<UserDto> users = ((List<UserEntity>) userRepository.findAll())
                .stream().map(userMapper::entityToUserDto).toList();
        List<UserTopDto> top = new ArrayList<>();
        for (UserDto user: users) {
            BloodTypesDto bloodTypesDto = findUsersDonationsByType(user.getUserId());
            UserTopDto userTopDto = new UserTopDto(bloodTypesDto);
            userTopDto.setUserId(user.getUserId());
            userTopDto.setFirstName(user.getFirstName());
            top.add(userTopDto);
        }
        top.sort(Comparator.comparingLong(UserTopDto::getCount).reversed());
        return top;
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
            DonationDto existingDonationDto = donationMapper.mapTo(existingDonation);
            Optional.of(donationDto.getBloodClass()).ifPresent(existingDonationDto::setBloodClass);
            Optional.of(donationDto.getDonateAt()).ifPresent(existingDonationDto::setDonateAt);
            Optional.of(donationDto.getPaymentType()).ifPresent(existingDonationDto::setPaymentType);
            Optional.of(donationDto.getIsOut()).ifPresent(existingDonationDto::setIsOut);
            Optional.of(donationDto.getBloodStationId()).ifPresent(existingDonationDto::setBloodStationId);
            Optional.of(donationDto.getWithImage()).ifPresent(existingDonationDto::setWithImage);
            return donationMapper.mapTo(donationRepository.save(donationMapper.mapFrom(existingDonationDto)));
        }).orElseThrow(() -> new RuntimeException("Donation doesn't exists"));
    }

    @Override
    public void delete(Long donationId) {
        donationRepository.deleteById(donationId);
    }
}
