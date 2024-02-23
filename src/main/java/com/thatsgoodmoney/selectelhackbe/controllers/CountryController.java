package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.CountryDto;
import com.thatsgoodmoney.selectelhackbe.domain.dto.DonationDto;
import com.thatsgoodmoney.selectelhackbe.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountryController {
    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        return new ResponseEntity<>(countryService.findAllCountries(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CountryDto> getCountry(
            @PathVariable("id") Long countryId) {
        Optional<CountryDto> foundCountry = countryService.findById(countryId);
        return foundCountry.map(countryDto -> new ResponseEntity<>(countryDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto countryDto) {
        CountryDto savedCountryDto = countryService.save(countryDto);
        return new ResponseEntity<>(savedCountryDto, HttpStatus.CREATED);
    }
}
