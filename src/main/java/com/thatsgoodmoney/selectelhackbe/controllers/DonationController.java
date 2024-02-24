package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.BloodTypesDto;
import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationDto;
import com.thatsgoodmoney.selectelhackbe.services.DonationServiceImpl;
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
    private final DonationServiceImpl donationService;

    @PostMapping
    public ResponseEntity<DonationDto> createDonation(
            @RequestBody DonationDto donationDto) {
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

    @GetMapping(path = "/by_type")
    public ResponseEntity<BloodTypesDto> getUsersDonationsByType(@RequestAttribute Long userId) {
        return new ResponseEntity<>(donationService.findUsersDonationsByType(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/is-honorary-donor")
    public ResponseEntity<Boolean> getIsHonoraryDonor(@RequestAttribute Long userId) {
        return new ResponseEntity<>(donationService.isHonoraryDonor(userId), HttpStatus.OK);
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
    public ResponseEntity<DonationDto> partialUpdateDonation(
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
    public ResponseEntity<HttpStatus> deleteDonation(
            @PathVariable("id") Long donationId
    ) {
        donationService.delete(donationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}