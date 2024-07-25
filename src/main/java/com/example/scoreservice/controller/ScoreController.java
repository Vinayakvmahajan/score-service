package com.example.scoreservice.controller;

import com.example.scoreservice.service.ScoreService;
import com.example.scoreservice.entity.Score;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/scores")
@Tag(name = "Score Management System", description = "Operations pertaining to scores in the Score Management System")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Operation(summary = "Add a new score")
    @PostMapping
    public Mono<ResponseEntity<Score>> createScore(@RequestBody Score score) {
        return scoreService.createScore(score)
                .map(createdScore -> ResponseEntity.status(HttpStatus.CREATED).body(createdScore));
    }

    @Operation(summary = "Get a score by Id")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Score>> getScoreById(@PathVariable Long id) {
        return scoreService.getScoreById(id)
                .map(score -> ResponseEntity.ok(score))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get a score by match Id")
    @GetMapping("/match/{matchId}")
    public Mono<ResponseEntity<Score>> getScoreByMatchId(@PathVariable Long matchId) {
        return scoreService.getScoreByMatchId(matchId)
                .map(score -> ResponseEntity.ok(score))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all scores")
    @GetMapping
    public Flux<Score> getAllScores() {
        return scoreService.getAllScores();
    }

    @Operation(summary = "Update a score")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Score>> updateScore(@PathVariable Long id, @RequestBody Score scoreDetails) {
        return scoreService.updateScore(id, scoreDetails)
                .map(updatedScore -> ResponseEntity.ok(updatedScore))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a score")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> deleteScore(@PathVariable Long id) {
        return scoreService.deleteScore(id)
                .then(Mono.just(ResponseEntity.noContent().build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
