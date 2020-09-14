package com.dababy.social.web.controller;

import com.dababy.social.web.dto.SimpleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class RestController {

    @GetMapping(value = "/api/home")
    public ResponseEntity<SimpleDTO> getHomeInfo(){
        return ResponseEntity.ok(new SimpleDTO("Something"));
    }
}
