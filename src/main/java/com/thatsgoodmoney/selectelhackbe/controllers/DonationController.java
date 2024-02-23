package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationRequestDto;
import com.thatsgoodmoney.selectelhackbe.services.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/donations")
public class DonationController {
    private final DonationService donationService;

    @PostMapping
    public ResponseEntity<DonationRequestDto> createDonation(
            @RequestAttribute Long userId,
            @RequestBody DonationRequestDto donationRequestDto) {
        donationRequestDto.setUserId(userId);
        DonationRequestDto savedDonationRequestDto = donationService.save(donationRequestDto);
        return new ResponseEntity<>(savedDonationRequestDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DonationRequestDto>> listUsersDonations(
            @RequestAttribute Long userId) {
        return new ResponseEntity<>(donationService.findAllUserDonations(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DonationRequestDto> getDonation(
            @PathVariable("id") Long donationId) {
        Optional<DonationRequestDto> foundDonation = donationService.findById(donationId);
        return foundDonation.map(donationDto -> new ResponseEntity<>(donationDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/is-exists")
    public ResponseEntity<Map<String, Boolean>> getDonation(
            @RequestParam("id") String donateAt) {
        boolean exists = donationService.isExists(donateAt);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DonationRequestDto> save(
            @PathVariable("id") Long donationId,
            @RequestBody DonationRequestDto donationRequestDto
    ) {
        donationRequestDto.setDonationId(donationId);
        DonationRequestDto savedUpdatedDonation = donationService.save(donationRequestDto);
        if (donationService.isExists(donationId))
            return new ResponseEntity<>(savedUpdatedDonation, HttpStatus.OK);

        return new ResponseEntity<>(savedUpdatedDonation, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<DonationRequestDto> partialUpdateDonation(
            @PathVariable("id") Long donationId,
            @RequestBody DonationRequestDto donationRequestDto
    ) {
        if (!donationService.isExists(donationId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        donationRequestDto.setDonationId(donationId);
        DonationRequestDto savedDonationRequestDto = donationService.partialUpdate(donationId, donationRequestDto);
        return new ResponseEntity<>(savedDonationRequestDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteDonation(
            @PathVariable("id") Long donationId
    ) {
        donationService.delete(donationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}