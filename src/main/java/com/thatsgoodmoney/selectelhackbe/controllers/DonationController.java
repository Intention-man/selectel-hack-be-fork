package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationDto;
import com.thatsgoodmoney.selectelhackbe.services.DonationService;
import com.thatsgoodmoney.selectelhackbe.services.UserService;
import com.thatsgoodmoney.selectelhackbe.services.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/donations")
public class DonationController {
    private final DonationService donationService;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<DonationDto> createDonation(
            @RequestAttribute Long userId,
            @RequestBody DonationDto donationDto) {
        donationDto.setUserId(userId);
        DonationDto savedDonationDto = donationService.save(donationDto);
        return new ResponseEntity<>(savedDonationDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DonationDto>> listUsersDonations(
            @RequestAttribute Long userId) {
        return new ResponseEntity<>(donationService.findAllUserDonations(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DonationDto> getPoint(
            @PathVariable("id") Long donationId) {
        Optional<DonationDto> foundPoint = donationService.findById(donationId);
        return foundPoint.map(pointDto -> new ResponseEntity<>(pointDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DonationDto> save(
            @PathVariable("id") Long donationId,
            @RequestBody DonationDto donationDto
    ) {
        donationDto.setDonationId(donationId);
        DonationDto savedUpdatedDonation = donationService.save(donationDto);
        if (donationService.isExists(donationId))
            return new ResponseEntity<>(savedUpdatedDonation, HttpStatus.OK);

        return new ResponseEntity<>(savedUpdatedDonation, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<DonationDto> partialUpdatePoint(
            @PathVariable("id") Long donationId,
            @RequestBody DonationDto donationDto
    ) {
        if (!donationService.isExists(donationId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        donationDto.setDonationId(donationId);
        DonationDto savedDonationDto = donationService.partialUpdate(donationId, donationDto);
        return new ResponseEntity<>(savedDonationDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteBook(
            @PathVariable("id") Long donationId
    ) {
        donationService.delete(donationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}