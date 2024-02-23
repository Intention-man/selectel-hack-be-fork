package com.thatsgoodmoney.selectelhackbe.services.impl;

import com.thatsgoodmoney.selectelhackbe.domain.dto.CityDto;
import com.thatsgoodmoney.selectelhackbe.domain.dto.CountryDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.CityEntity;
import com.thatsgoodmoney.selectelhackbe.domain.entities.CountryEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.CityRepository;
import com.thatsgoodmoney.selectelhackbe.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final Mapper<CityEntity, CityDto> mapper;

    public CityService(CityRepository cityRepository, Mapper<CityEntity, CityDto> mapper) {
        this.cityRepository = cityRepository;
        this.mapper = mapper;
    }

    public List<CityDto> findAllCities() {
        List<CityEntity> entities = (List<CityEntity>) cityRepository.findAll();
        return entities.stream().map(mapper::mapTo).toList();
    }

    public Optional<CityDto> findById(Long id) {
        Optional<CityEntity> entity = cityRepository.findById(id);
        return entity.map(mapper::mapTo);
    }

    public Optional<CityDto> findByLocation(String lat, String lng) {
        Optional<CityEntity> entity = cityRepository.findByLatAndLng(lat, lng);
        return entity.map(mapper::mapTo);
    }
}
