package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationDto;
import com.thatsgoodmoney.selectelhackbe.services.AuthService;
import com.thatsgoodmoney.selectelhackbe.services.DonationService;
import com.thatsgoodmoney.selectelhackbe.services.UserServiceImpl;
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
    private final UserServiceImpl userService;
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
    public ResponseEntity<DonationDto> getDonation(
            @PathVariable("id") Long donationId) {
        Optional<DonationDto> foundDonation = donationService.findById(donationId);
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
    public ResponseEntity<DonationDto> save(
            @PathVariable("id") Long donationId,
            @RequestBody DonationDto donationDto
    ) {
        donationDto.setId(donationId);
        DonationDto savedUpdatedDonation = donationService.save(donationDto);
        if (donationService.isExists(donationId))
            return new ResponseEntity<>(savedUpdatedDonation, HttpStatus.OK);

        return new ResponseEntity<>(savedUpdatedDonation, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<DonationDto> partialUpdateDonation(
            @PathVariable("id") Long donationId,
            @RequestBody DonationDto donationDto
    ) {
        if (!donationService.isExists(donationId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        donationDto.setId(donationId);
        DonationDto savedDonationDto = donationService.partialUpdate(donationId, donationDto);
        return new ResponseEntity<>(savedDonationDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteDonation(
            @PathVariable("id") Long donationId
    ) {
        donationService.delete(donationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}