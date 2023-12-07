package com.webtut.dbwork.services;

import com.webtut.dbwork.domain.entities.PointEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface PointService {
    PointEntity save(PointEntity pointEntity);

    List<PointEntity> findAll();

    Page<PointEntity> findAll(Pageable pageable);

    Optional<PointEntity> findById(Long pointId);

    boolean isExists(Long pointId);

    PointEntity partialUpdate(Long pointId, PointEntity pointEntity);

    void delete(Long pointId);
}
