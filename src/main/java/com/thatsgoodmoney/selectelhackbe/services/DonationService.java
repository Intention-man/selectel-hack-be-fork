package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DonationService {
    DonationDto save(DonationDto donationDto);

    List<DonationDto> findAllUserDonations(Long userId);

    Optional<DonationDto> findById(Long donationId);

    boolean isExists(Long donationId);

    DonationDto partialUpdate(Long donationId, DonationDto donationDto);

    void delete(Long donationId);
}
