package com.example.scoreservice.service;

import com.example.scoreservice.entity.Score;
import com.example.scoreservice.repository.ScoreRepository;
import com.example.scoreservice.exception.ScoreNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public Mono<Score> createScore(Score score) {
        return Mono.fromCallable(() -> scoreRepository.save(score))
                   .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Score> getScoreById(Long id) {
        return Mono.fromCallable(() -> scoreRepository.findById(id)
                                                      .orElseThrow(() -> new ScoreNotFoundException("Score not found with id " + id)))
                   .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Score> getScoreByMatchId(Long matchId) {
        return Mono.fromCallable(() -> scoreRepository.findByMatchId(matchId)
                                                      .orElseThrow(() -> new ScoreNotFoundException("Score not found with match id " + matchId)))
                   .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<Score> getAllScores() {
        return Mono.fromCallable(() -> scoreRepository.findAll())
                   .flatMapMany(Flux::fromIterable)
                   .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Score> updateScore(Long id, Score score) {
        return Mono.fromCallable(() -> {
            Score existingScore = scoreRepository.findById(id)
                                                 .orElseThrow(() -> new ScoreNotFoundException("Score not found with id " + id));
            existingScore.setMatchId(score.getMatchId());
            existingScore.setScore(score.getScore());
            return scoreRepository.save(existingScore);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> deleteScore(Long id) {
        return Mono.fromRunnable(() -> {
            Score existingScore = scoreRepository.findById(id)
                                                 .orElseThrow(() -> new ScoreNotFoundException("Score not found with id " + id));
            scoreRepository.delete(existingScore);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}
