package com.example.scoreservice.controller;

import com.example.scoreservice.entity.Score;
import com.example.scoreservice.service.ScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ScoreControllerTest {

    @Mock
    private ScoreService scoreService;

    @InjectMocks
    private ScoreController scoreController;

    private Score score;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        score = new Score(1L, 1001L, "250/8");
    }

    @Test
    void testCreateScore() {
        when(scoreService.createScore(any(Score.class))).thenReturn(Mono.just(score));

        Mono<ResponseEntity<Score>> response = scoreController.createScore(score);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.CREATED && entity.getBody().equals(score))
                .verifyComplete();

        verify(scoreService, times(1)).createScore(any(Score.class));
    }

    @Test
    void testGetScoreById() {
        when(scoreService.getScoreById(anyLong())).thenReturn(Mono.just(score));

        Mono<ResponseEntity<Score>> response = scoreController.getScoreById(1L);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.OK && entity.getBody().equals(score))
                .verifyComplete();

        verify(scoreService, times(1)).getScoreById(anyLong());
    }

    @Test
    void testGetScoreByMatchId() {
        when(scoreService.getScoreByMatchId(anyLong())).thenReturn(Mono.just(score));

        Mono<ResponseEntity<Score>> response = scoreController.getScoreByMatchId(1001L);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.OK && entity.getBody().equals(score))
                .verifyComplete();

        verify(scoreService, times(1)).getScoreByMatchId(anyLong());
    }

    @Test
    void testGetAllScores() {
        when(scoreService.getAllScores()).thenReturn(Flux.just(score));

        Flux<Score> response = scoreController.getAllScores();

        StepVerifier.create(response)
                .expectNext(score)
                .verifyComplete();

        verify(scoreService, times(1)).getAllScores();
    }

    @Test
    void testUpdateScore() {
        when(scoreService.updateScore(anyLong(), any(Score.class))).thenReturn(Mono.just(score));

        Mono<ResponseEntity<Score>> response = scoreController.updateScore(1L, score);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.OK && entity.getBody().equals(score))
                .verifyComplete();

        verify(scoreService, times(1)).updateScore(anyLong(), any(Score.class));
    }

    @Test
    void testDeleteScore() {
        when(scoreService.deleteScore(anyLong())).thenReturn(Mono.empty());

        Mono<ResponseEntity<Object>> response = scoreController.deleteScore(1L);

        StepVerifier.create(response)
                .expectNextMatches(entity -> entity.getStatusCode() == HttpStatus.NO_CONTENT)
                .verifyComplete();

        verify(scoreService, times(1)).deleteScore(anyLong());
    }
}
