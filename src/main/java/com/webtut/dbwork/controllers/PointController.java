package com.webtut.dbwork.controllers;

import com.webtut.dbwork.domain.dto.PointDto;
import com.webtut.dbwork.domain.entities.PointEntity;
import com.webtut.dbwork.mappers.Mapper;
import com.webtut.dbwork.services.PointService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PointController {

    private final PointService pointService;

    private final Mapper<PointEntity, PointDto> pointMapper;

    public PointController(PointService pointService, Mapper<PointEntity, PointDto> pointMapper) {
        this.pointService = pointService;
        this.pointMapper = pointMapper;
    }

    @PostMapping(path = "/points")
    public ResponseEntity<PointDto> createPoint(@RequestBody PointDto pointDto) {
        PointEntity pointEntity = pointMapper.mapFrom(pointDto);
        PointEntity savedPointEntity = pointService.save(pointEntity);
        PointDto savedPointDto = pointMapper.mapTo(savedPointEntity);
        return new ResponseEntity<>(savedPointDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/points")
    public Page<PointDto> listPoints(Pageable pageable) {
        Page<PointEntity> points = pointService.findAll(pageable);
        return points.map(pointMapper::mapTo) ;
    }

    @GetMapping(path = "/points/{point_id}")
    public ResponseEntity<PointDto> getPoint(@PathVariable("point_id") Long pointId) {
        Optional<PointEntity> foundPoint = pointService.findById(pointId);
        return foundPoint.map(pointEntity -> {
            PointDto pointDto = pointMapper.mapTo(pointEntity);
            return new ResponseEntity<>(pointDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/points/{point_id}")
    public ResponseEntity<PointDto> save(
            @PathVariable("point_id") Long pointId,
            @RequestBody PointDto pointDto){

        PointEntity pointEntity = pointMapper.mapFrom(pointDto);
        boolean pointExist = pointService.isExists(pointId);
        pointEntity.setPointId(pointId);
        PointEntity savedPointEntity = pointService.save(pointEntity);
        PointDto savedUpdatedPoint = pointMapper.mapTo(savedPointEntity);

        if (pointExist){
            return new ResponseEntity<>(savedUpdatedPoint, HttpStatus.OK);
        }
        return new ResponseEntity<>(savedUpdatedPoint, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/points/{point_id}")
    public ResponseEntity<PointDto> partialUpdatePoint(
            @PathVariable("point_id") Long pointId,
            @RequestBody PointDto pointDto){

        if (pointService.isExists(pointId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        pointDto.setPointId(pointId);
        PointEntity pointEntity = pointMapper.mapFrom(pointDto);
        PointEntity savedPointEntity = pointService.partialUpdate(pointId, pointEntity);
        return new ResponseEntity<>(pointMapper.mapTo(savedPointEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/points/{point_id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("point_id") Long pointId) {
        pointService.delete(pointId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}