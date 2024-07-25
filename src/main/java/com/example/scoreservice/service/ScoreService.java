package com.example.scoreservice.service;

import com.example.scoreservice.entity.Score;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ScoreService {
    Mono<Score> createScore(Score score);
    Mono<Score> getScoreById(Long id);
    Mono<Score> getScoreByMatchId(Long matchId);
    Flux<Score> getAllScores();
    Mono<Score> updateScore(Long id, Score score);
    Mono<Void> deleteScore(Long id);
}
