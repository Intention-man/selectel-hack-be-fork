package com.webtut.dbwork.services;

import com.webtut.dbwork.domain.dto.PointDto;
import com.webtut.dbwork.domain.entities.PointEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PointService {
    PointEntity save(PointEntity pointEntity);
    PointDto save(PointDto pointDto);

    List<PointDto> findAllUserPoints(Long userId);

    Page<PointEntity> findAllPointsOfPage(Long userId, Pageable pageable);

    Optional<PointDto> findById(Long pointId);

    boolean isExists(Long pointId);

    PointDto partialUpdate(Long pointId, PointDto pointDto);

    void delete(Long pointId);
}
