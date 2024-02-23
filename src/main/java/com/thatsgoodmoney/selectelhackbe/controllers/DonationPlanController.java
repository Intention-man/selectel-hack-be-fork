package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationPlanRequestDto;
import com.thatsgoodmoney.selectelhackbe.services.DonationPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/donation_plan")
public class DonationPlanController {
    private final DonationPlanService donationPlanService;

    @PostMapping
    public ResponseEntity<DonationPlanRequestDto> createDonationPlan(
            @RequestBody DonationPlanRequestDto donationPlanRequestDto) {
        DonationPlanRequestDto savedDonationDto = donationPlanService.save(donationPlanRequestDto);
        return new ResponseEntity<>(savedDonationDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DonationPlanRequestDto>> getAllDonationPlans() {
        return new ResponseEntity<>(donationPlanService.findAllDonationPlans(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DonationPlanRequestDto> getDonationPlan(
            @PathVariable("id") Long donationPlanId) {
        Optional<DonationPlanRequestDto> foundDonationPlan = donationPlanService.findById(donationPlanId);
        return foundDonationPlan.map(donationPlanRequestDto -> new ResponseEntity<>(donationPlanRequestDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DonationPlanRequestDto> save(
            @PathVariable("id") Long donationPlanId,
            @RequestBody DonationPlanRequestDto donationPlanRequestDto
    ) {
        donationPlanRequestDto.setDonationPlanId(donationPlanId);
        DonationPlanRequestDto savedUpdatedDonationPlan = donationPlanService.save(donationPlanRequestDto);
        if (donationPlanService.isExists(donationPlanId))
            return new ResponseEntity<>(savedUpdatedDonationPlan, HttpStatus.OK);

        return new ResponseEntity<>(savedUpdatedDonationPlan, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<DonationPlanRequestDto> partialUpdateDonationPlan(
            @PathVariable("id") Long donationPlanId,
            @RequestBody DonationPlanRequestDto donationPlanRequestDto
    ) {
        if (!donationPlanService.isExists(donationPlanId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        donationPlanRequestDto.setDonationPlanId(donationPlanId);
        DonationPlanRequestDto savedDonationPlanRequestDto = donationPlanService.partialUpdate(donationPlanId, donationPlanRequestDto);
        return new ResponseEntity<>(savedDonationPlanRequestDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteDonationPlan(
            @PathVariable("id") Long donationPlanId
    ) {
        donationPlanService.delete(donationPlanId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
