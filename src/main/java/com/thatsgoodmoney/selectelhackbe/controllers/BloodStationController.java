package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.BloodStationDto;
import com.thatsgoodmoney.selectelhackbe.domain.dto.CityDto;
import com.thatsgoodmoney.selectelhackbe.services.BloodStationService;
import com.thatsgoodmoney.selectelhackbe.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/blood_stations")
public class BloodStationController {
    private final BloodStationService bloodStationService;

    @GetMapping
    public ResponseEntity<List<BloodStationDto>> getAllBloodStations() {
        return new ResponseEntity<>(bloodStationService.findAllBloodStations(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BloodStationDto> getBloodStation(
            @PathVariable("id") Long bloodStationId) {
        Optional<BloodStationDto> foundBloodStation = bloodStationService.findById(bloodStationId);
        return foundBloodStation.map(bloodStationDto -> new ResponseEntity<>(bloodStationDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // мб что-то еще

}
