package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationDto;
import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationPlanDto;
import com.thatsgoodmoney.selectelhackbe.services.impl.DonationPlanService;
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
@RequestMapping("/donation_plan")
public class DonationPlanController {
    private final DonationPlanService donationPlanService;

//    @PostMapping
//    public ResponseEntity<DonationPlanDto> createDonationPlan(
//            @RequestAttribute Long userId,
//            @RequestBody DonationPlanDto donationPlanDto) {
//        donationPlanDto.setUserId(userId);
//        DonationPlanDto savedDonationDto = donationPlanService.save(donationPlanDto);
//        return new ResponseEntity<>(savedDonationDto, HttpStatus.CREATED);
//    }

    @GetMapping
    public ResponseEntity<List<DonationPlanDto>> getAllDonationPlans() {
        return new ResponseEntity<>(donationPlanService.findAllDonationPlans(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DonationPlanDto> getDonationPlan(
            @PathVariable("id") Long donationPlanId) {
        Optional<DonationPlanDto> foundDonationPlan = donationPlanService.findById(donationPlanId);
        return foundDonationPlan.map(donationPlanDto -> new ResponseEntity<>(donationPlanDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DonationPlanDto> save(
            @PathVariable("id") Long donationPlanId,
            @RequestBody DonationPlanDto donationPlanDto
    ) {
        donationPlanDto.setId(donationPlanId);
        DonationPlanDto savedUpdatedDonationPlan = donationPlanService.save(donationPlanDto);
        if (donationPlanService.isExists(donationPlanId))
            return new ResponseEntity<>(savedUpdatedDonationPlan, HttpStatus.OK);

        return new ResponseEntity<>(savedUpdatedDonationPlan, HttpStatus.CREATED);
    }

//    @PatchMapping(path = "/{id}")
//    public ResponseEntity<DonationPlanDto> partialUpdateDonationPlan(
//            @PathVariable("id") Long donationPlanId,
//            @RequestBody DonationPlanDto donationPlanDto
//    ) {
//        if (!donationPlanService.isExists(donationPlanId))
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//        donationPlanDto.setId(donationPlanId);
//        DonationPlanDto savedDonationPlanDto = donationPlanService.partialUpdate(donationPlanId, donationPlanDto);
//        return new ResponseEntity<>(savedDonationPlanDto, HttpStatus.OK);
//    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteDonationPlan(
            @PathVariable("id") Long donationPlanId
    ) {
        donationPlanService.delete(donationPlanId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
