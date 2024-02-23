package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.CountryDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.CountryEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final Mapper<CountryEntity, CountryDto> mapper;

    public CountryService(CountryRepository countryRepository, Mapper<CountryEntity, CountryDto> mapper) {
        this.countryRepository = countryRepository;
        this.mapper = mapper;
    }

    public List<CountryDto> findAllCountries() {
        List<CountryEntity> entities = (List<CountryEntity>) countryRepository.findAll();
        return entities.stream().map(mapper::mapTo).toList();
    }

    public Optional<CountryDto> findById(Long id) {
        Optional<CountryEntity> entity = countryRepository.findById(id);
        return entity.map(mapper::mapTo);
    }
}
