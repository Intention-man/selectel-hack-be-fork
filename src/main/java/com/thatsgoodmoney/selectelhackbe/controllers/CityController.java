package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.CityDto;
import com.thatsgoodmoney.selectelhackbe.domain.dto.RegionDto;
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
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<List<CityDto>> getAllCities() {
        return new ResponseEntity<>(cityService.findAllCities(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CityDto> getCity(
            @PathVariable("id") Long cityId) {
        Optional<CityDto> foundCity = cityService.findById(cityId);
        return foundCity.map(cityDto -> new ResponseEntity<>(cityDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/by_location")
    public ResponseEntity<CityDto> getCityByLocation(@RequestParam Double lat, @RequestParam Double lng) {
        Optional<CityDto> foundCity = cityService.findByLocation(lat, lng);
        return foundCity.map(cityDto -> new ResponseEntity<>(cityDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
