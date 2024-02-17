package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.PointDto;
import com.thatsgoodmoney.selectelhackbe.services.PointService;
import com.thatsgoodmoney.selectelhackbe.services.UserService;
import com.thatsgoodmoney.selectelhackbe.services.impl.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping(path = "/points")
    public ResponseEntity<PointDto> createPoint(
            @RequestAttribute Long userId,
            @RequestBody PointDto pointDto)
    {
        pointDto.setUserId(userId);
        PointDto savedPointDto = pointService.save(pointDto);
        return new ResponseEntity<>(savedPointDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/points")
    public ResponseEntity<List<PointDto>> listUsersPoints(
            @RequestAttribute Long userId)
    {
        return new ResponseEntity<>(pointService.findAllUserPoints(userId), HttpStatus.OK);
    }

    @GetMapping(path = "/points/{point_id}")
    public ResponseEntity<PointDto> getPoint(
            @PathVariable("point_id") Long pointId
    ){
        Optional<PointDto> foundPoint = pointService.findById(pointId);
        return foundPoint.map(pointDto -> new ResponseEntity<>(pointDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/points/{point_id}")
    public ResponseEntity<PointDto> save(
            @PathVariable("point_id") Long pointId,
            @RequestBody PointDto pointDto
    ){
        pointDto.setPointId(pointId);
        PointDto savedUpdatedPoint = pointService.save(pointDto);
        if (pointService.isExists(pointId))
            return new ResponseEntity<>(savedUpdatedPoint, HttpStatus.OK);

        return new ResponseEntity<>(savedUpdatedPoint, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/points/{point_id}")
    public ResponseEntity<PointDto> partialUpdatePoint(
            @PathVariable("point_id") Long pointId,
            @RequestBody PointDto pointDto
    ){
        if (!pointService.isExists(pointId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        pointDto.setPointId(pointId);
        PointDto savedPointDto = pointService.partialUpdate(pointId, pointDto);
        return new ResponseEntity<>(savedPointDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "/points/{point_id}")
    public ResponseEntity<HttpStatus> deleteBook(
            @PathVariable("point_id") Long pointId
    ){
        pointService.delete(pointId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}