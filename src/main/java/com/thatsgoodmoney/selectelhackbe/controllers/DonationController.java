package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.BloodTypesDto;
import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationDto;
import com.thatsgoodmoney.selectelhackbe.services.DonationServiceImpl;
import com.thatsgoodmoney.selectelhackbe.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final ImageService imageService;

    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<DonationDto> createDonation(
            @RequestPart("donationDto") DonationDto donationDto,
            @RequestPart(name = "file", required = false) MultipartFile file) {
        DonationDto savedDonationDto = donationService.save(donationDto);
        if (file != null) {
            savedDonationDto.setImageId(imageService.storeFile(file));
        }
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

    @PutMapping(path = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<DonationDto> save(
            @PathVariable("id") Long donationId,
            @RequestPart("donationDto") DonationDto donationDto,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        donationDto.setDonationId(donationId);
        if (file != null) {
            donationDto.setImageId(imageService.storeFile(file));
        }
        DonationDto savedUpdatedDonation = donationService.save(donationDto);
        if (donationService.isExists(donationId))
            return new ResponseEntity<>(savedUpdatedDonation, HttpStatus.OK);

        return new ResponseEntity<>(savedUpdatedDonation, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<DonationDto> partialUpdateDonation(
            @PathVariable("id") Long donationId,
            @RequestPart("donationDto") DonationDto donationDto,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        if (!donationService.isExists(donationId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        donationDto.setDonationId(donationId);
        if (file != null) {
            donationDto.setImageId(imageService.storeFile(file));
        }
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

    // заглушка
    @GetMapping(path = "/get_file")
    public ResponseEntity<Resource> getFile() {
        String fileName = "file.txt";
        ByteArrayResource resource = imageService.loadFileAsResource(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .headers(headers)
                .body(resource);
    }
}