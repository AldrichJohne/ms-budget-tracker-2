package com.aldrichjohne.budget.tracker.controller;

import com.aldrichjohne.budget.tracker.model.entity.dto.ActorDTO;
import com.aldrichjohne.budget.tracker.service.ActorService;
import com.aldrichjohne.budget.tracker.util.mapper.model.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/v1")
@RequiredArgsConstructor
public class ActorController {
    private final ActorService actorService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> addActor(@RequestBody ActorDTO actorDTO) {
        return ResponseEntity.ok(actorService.addActor(actorDTO));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<ResponseWrapper> removeActor(@PathVariable UUID uuid) {
        return ResponseEntity.ok(actorService.removeActor(uuid));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateActorInfo(
            @RequestBody ActorDTO actorDTO) {
        return ResponseEntity.ok(actorService.updateActor(actorDTO));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ResponseWrapper> retrieveActorInfo(@PathVariable UUID uuid) {
        return ResponseEntity.ok(actorService.getActor(uuid));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper> retrieveAllActors() {
        return ResponseEntity.ok(actorService.getActors());
    }
}
