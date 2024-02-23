package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.CountryDto;
import com.thatsgoodmoney.selectelhackbe.domain.dto.RegionDto;
import com.thatsgoodmoney.selectelhackbe.services.impl.CountryService;
import com.thatsgoodmoney.selectelhackbe.services.impl.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionDto>> getAllRegions() {
        return new ResponseEntity<>(regionService.findAllRegions(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<RegionDto> getRegion(
            @PathVariable("id") Long regionId) {
        Optional<RegionDto> foundRegion = regionService.findById(regionId);
        return foundRegion.map(regionDto -> new ResponseEntity<>(regionDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
