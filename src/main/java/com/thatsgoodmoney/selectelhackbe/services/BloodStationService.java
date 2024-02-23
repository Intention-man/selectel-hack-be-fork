package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.BloodStationDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.BloodStationEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.BloodStationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BloodStationService {
    private final BloodStationRepository bloodStationRepository;
    private final Mapper<BloodStationEntity, BloodStationDto> mapper;

    public List<BloodStationDto> findAllBloodStations() {
        List<BloodStationEntity> entities = (List<BloodStationEntity>) bloodStationRepository.findAll();
        return entities.stream().map(mapper::mapTo).toList();
    }

    public Optional<BloodStationDto> findById(Long id) {
        Optional<BloodStationEntity> entity = bloodStationRepository.findById(id);
        return entity.map(mapper::mapTo);
    }

}
