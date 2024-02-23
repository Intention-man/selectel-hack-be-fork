package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.CityDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.CityEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final Mapper<CityEntity, CityDto> mapper;

    public List<CityDto> findAllCities() {
        List<CityEntity> entities = (List<CityEntity>) cityRepository.findAll();
        return entities.stream().map(mapper::mapTo).toList();
    }

    public Optional<CityDto> findById(Long id) {
        Optional<CityEntity> entity = cityRepository.findById(id);
        return entity.map(mapper::mapTo);
    }

    public Optional<CityDto> findByLocation(Double lat, Double lng) {
        Optional<CityEntity> entity = cityRepository.findByLatAndLng(lat, lng);
        return entity.map(mapper::mapTo);
    }
}
