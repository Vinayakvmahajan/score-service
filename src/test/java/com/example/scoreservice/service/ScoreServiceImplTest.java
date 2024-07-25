package com.example.scoreservice.service;

import com.example.scoreservice.entity.Score;
import com.example.scoreservice.exception.ScoreNotFoundException;
import com.example.scoreservice.repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ScoreServiceImplTest {

    @Mock
    private ScoreRepository scoreRepository;

    @InjectMocks
    private ScoreServiceImpl scoreService;

    private Score score;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        score = new Score(1L, 1001L, "250/8");
    }

    @Test
    void testCreateScore() {
        when(scoreRepository.save(any(Score.class))).thenReturn(score);

        Mono<Score> createdScore = scoreService.createScore(score);

        StepVerifier.create(createdScore)
                .expectNextMatches(savedScore -> savedScore.equals(score))
                .verifyComplete();

        verify(scoreRepository, times(1)).save(any(Score.class));
    }

    @Test
    void testGetScoreById() {
        when(scoreRepository.findById(anyLong())).thenReturn(Optional.of(score));

        Mono<Score> retrievedScore = scoreService.getScoreById(1L);

        StepVerifier.create(retrievedScore)
                .expectNextMatches(foundScore -> foundScore.equals(score))
                .verifyComplete();

        verify(scoreRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetScoreByIdNotFound() {
        when(scoreRepository.findById(anyLong())).thenReturn(Optional.empty());

        Mono<Score> retrievedScore = scoreService.getScoreById(1L);

        StepVerifier.create(retrievedScore)
                .expectError(ScoreNotFoundException.class)
                .verify();

        verify(scoreRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetScoreByMatchId() {
        when(scoreRepository.findByMatchId(anyLong())).thenReturn(Optional.of(score));

        Mono<Score> retrievedScore = scoreService.getScoreByMatchId(1001L);

        StepVerifier.create(retrievedScore)
                .expectNextMatches(foundScore -> foundScore.equals(score))
                .verifyComplete();

        verify(scoreRepository, times(1)).findByMatchId(anyLong());
    }

    @Test
    void testGetScoreByMatchIdNotFound() {
        when(scoreRepository.findByMatchId(anyLong())).thenReturn(Optional.empty());

        Mono<Score> retrievedScore = scoreService.getScoreByMatchId(1001L);

        StepVerifier.create(retrievedScore)
                .expectError(ScoreNotFoundException.class)
                .verify();

        verify(scoreRepository, times(1)).findByMatchId(anyLong());
    }

    @Test
    void testGetAllScores() {
        when(scoreRepository.findAll()).thenReturn(Collections.singletonList(score));

        Flux<Score> allScores = scoreService.getAllScores();

        StepVerifier.create(allScores)
                .expectNext(score)
                .verifyComplete();

        verify(scoreRepository, times(1)).findAll();
    }

    @Test
    void testUpdateScore() {
        when(scoreRepository.findById(anyLong())).thenReturn(Optional.of(score));
        when(scoreRepository.save(any(Score.class))).thenReturn(score);

        Mono<Score> updatedScore = scoreService.updateScore(1L, score);

        StepVerifier.create(updatedScore)
                .expectNextMatches(savedScore -> savedScore.equals(score))
                .verifyComplete();

        verify(scoreRepository, times(1)).findById(anyLong());
        verify(scoreRepository, times(1)).save(any(Score.class));
    }

    @Test
    void testUpdateScoreNotFound() {
        when(scoreRepository.findById(anyLong())).thenReturn(Optional.empty());

        Mono<Score> updatedScore = scoreService.updateScore(1L, score);

        StepVerifier.create(updatedScore)
                .expectError(ScoreNotFoundException.class)
                .verify();

        verify(scoreRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteScore() {
        when(scoreRepository.findById(anyLong())).thenReturn(Optional.of(score));
        doNothing().when(scoreRepository).delete(any(Score.class));

        Mono<Void> deletedScore = scoreService.deleteScore(1L);

        StepVerifier.create(deletedScore)
                .verifyComplete();

        verify(scoreRepository, times(1)).findById(anyLong());
        verify(scoreRepository, times(1)).delete(any(Score.class));
    }

    @Test
    void testDeleteScoreNotFound() {
        when(scoreRepository.findById(anyLong())).thenReturn(Optional.empty());

        Mono<Void> deletedScore = scoreService.deleteScore(1L);

        StepVerifier.create(deletedScore)
                .expectError(ScoreNotFoundException.class)
                .verify();

        verify(scoreRepository, times(1)).findById(anyLong());
    }
}
