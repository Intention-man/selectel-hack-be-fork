package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DonationService {
    DonationRequestDto save(DonationRequestDto donationRequestDto);

    List<DonationRequestDto> findAllUserDonations(Long userId);

    Optional<DonationRequestDto> findById(Long donationId);

    boolean isExists(Long donationId);
    boolean isExists(String donateAt);

    DonationRequestDto partialUpdate(Long donationId, DonationRequestDto donationRequestDto);

    void delete(Long donationId);
}
