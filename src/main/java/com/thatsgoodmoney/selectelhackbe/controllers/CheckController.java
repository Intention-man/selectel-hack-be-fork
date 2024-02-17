package com.thatsgoodmoney.selectelhackbe.controllers;

import com.thatsgoodmoney.selectelhackbe.domain.dto.PointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CheckController {
    @GetMapping(path = "/health-check")
    public ResponseEntity<List<PointDto>> healthCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
