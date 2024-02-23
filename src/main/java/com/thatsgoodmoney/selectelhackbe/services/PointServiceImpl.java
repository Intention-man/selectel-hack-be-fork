package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.dto.PointDto;
import com.thatsgoodmoney.selectelhackbe.domain.entities.PointEntity;
import com.thatsgoodmoney.selectelhackbe.mappers.Mapper;
import com.thatsgoodmoney.selectelhackbe.repositories.PointRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class PointServiceImpl{
    private final PointRepository pointRepository;
    private final Mapper<PointEntity, PointDto> pointMapper;

    public PointServiceImpl(PointRepository pointRepository, Mapper<PointEntity, PointDto> pointMapper) {
        this.pointRepository = pointRepository;
        this.pointMapper = pointMapper;
    }


    public PointEntity save(PointEntity pointEntity) {
        return pointRepository.save(pointEntity);
    }

    public PointDto save(PointDto pointDto) {
        PointEntity pointEntity = pointMapper.mapFrom(pointDto);
        return pointMapper.mapTo(pointRepository.save(pointEntity));
    }

    public List<PointDto> findAllUserPoints(Long userId) {
        return StreamSupport.stream(pointRepository.findAll().spliterator(), false)
                .filter(pointEntity -> Objects.equals(pointEntity.getUserId(), userId))
                .map(pointMapper::mapTo).toList();
    }


    public Page<PointEntity> findAllPointsOfPage(Long userId, Pageable pageable) {
        return pointRepository.findAll(pageable);
    }

    public Optional<PointDto> findById(Long pointId) {
        Optional<PointEntity> optionalPointDto = pointRepository.findById(pointId);
        return optionalPointDto.map(pointMapper::mapTo);
    }

    public boolean isExists(Long pointId) {
        return pointRepository.existsById(pointId);
    }

    public PointDto partialUpdate(Long pointId, PointDto pointDto) {
        pointDto.setPointId(pointId);
        return pointRepository.findById(pointId).map(existingPoint -> {
            Optional.of(pointDto.getX()).ifPresent(existingPoint::setX);
            Optional.of(pointDto.getY()).ifPresent(existingPoint::setY);
            Optional.of(pointDto.getR()).ifPresent(existingPoint::setR);
            Optional.of(pointDto.isInside()).ifPresent(existingPoint::setInside);
            return pointMapper.mapTo(pointRepository.save(existingPoint));
        }).orElseThrow(() -> new RuntimeException("Point doesn't exists"));
    }

    public void delete(Long pointId) {
        pointRepository.deleteById(pointId);
    }
}
