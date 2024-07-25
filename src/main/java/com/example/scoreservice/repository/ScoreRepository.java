package com.example.scoreservice.repository;

import com.example.scoreservice.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findByMatchId(Long matchId);
}
