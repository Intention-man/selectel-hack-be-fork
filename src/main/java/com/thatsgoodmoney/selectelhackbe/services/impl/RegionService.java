package com.thatsgoodmoney.selectelhackbe.services.impl;

import com.thatsgoodmoney.selectelhackbe.domain.dto.RegionDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.RegionEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    private final RegionRepository regionRepository;
    private final Mapper<RegionEntity, RegionDto> mapper;

    public RegionService(RegionRepository regionRepository, Mapper<RegionEntity, RegionDto> mapper) {
        this.regionRepository = regionRepository;
        this.mapper = mapper;
    }

    public List<RegionDto> findAllRegions() {
        List<RegionEntity> entities = (List<RegionEntity>) regionRepository.findAll();
        return entities.stream().map(mapper::mapTo).toList();
    }

    public Optional<RegionDto> findById(Long id) {
        Optional<RegionEntity> entity = regionRepository.findById(id);
        return entity.map(mapper::mapTo);
    }
}
