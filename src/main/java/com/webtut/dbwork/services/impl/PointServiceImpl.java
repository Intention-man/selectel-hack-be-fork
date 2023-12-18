package com.webtut.dbwork.services.impl;

import com.webtut.dbwork.domain.entities.PointEntity;
import com.webtut.dbwork.repositories.PointRepository;
import com.webtut.dbwork.services.PointService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class PointServiceImpl implements PointService {
    private final PointRepository pointRepository;

    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    public PointEntity save(PointEntity pointEntity) {
        return pointRepository.save(pointEntity);
    }

    @Override
    public List<PointEntity> findAllUserPoints(Long userId) {
        return StreamSupport.stream(pointRepository.findAll().spliterator(), false).filter(pointEntity -> Objects.equals(pointEntity.getUserEntity().getUserId(), userId)).toList();
    }

    @Override
    public Page<PointEntity> findAllPoints(Long userId, Pageable pageable) {
        return pointRepository.findAll(pageable);
    }

    @Override
    public Optional<PointEntity> findById(Long pointId) {
        return pointRepository.findById(pointId);
    }

    @Override
    public boolean isExists(Long pointId) {
        return pointRepository.existsById(pointId);
    }

    @Override
    public PointEntity partialUpdate(Long pointId, PointEntity pointEntity) {
        pointEntity.setPointId(pointId);
        return pointRepository.findById(pointId).map(existingPoint -> {
            Optional.of(pointEntity.getX()).ifPresent(existingPoint::setX);
            Optional.of(pointEntity.getY()).ifPresent(existingPoint::setY);
            Optional.of(pointEntity.getR()).ifPresent(existingPoint::setR);
            Optional.of(pointEntity.isInside()).ifPresent(existingPoint::setInside);
            return pointRepository.save(existingPoint);
        }).orElseThrow(() -> new RuntimeException("Point doesn't exists"));
    }

    @Override
    public void delete(Long pointId) {
        pointRepository.deleteById(pointId);
    }

}
