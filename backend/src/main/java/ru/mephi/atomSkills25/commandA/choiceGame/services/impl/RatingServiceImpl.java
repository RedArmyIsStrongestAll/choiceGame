package ru.mephi.atomSkills25.commandA.choiceGame.services.impl;

import org.springframework.stereotype.Service;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Game;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.GameWithPleasure;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Player;
import ru.mephi.atomSkills25.commandA.choiceGame.services.RatingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {


    public List<GameWithPleasure> calculatePleasure(List<Game> gameList,
                                                    List<Player> playerList) {
        int numPlayers = playerList.size();
        Map<Game, Double> gameScores = new HashMap<>();
        double maxScore = Double.MIN_VALUE;

        for (Game game : gameList) {
            if (game.getMinPlayers() > numPlayers
                    || game.getMaxPlayers() < numPlayers) {
                continue;
            }

            double totalScore = 0;
            boolean shouldExclude = false;

            int countWeakScores = 0;

            for (Player player : playerList) {
                Map<Game, Integer> ratings = player.getRatings();
                Integer score = ratings.get(game);

                if (score != null) {
                    if (score == 0) {
                        shouldExclude = true;
                        break;
                    }
                    if (score == 1) {
                        ++countWeakScores;
                    } else {
                        totalScore += score;
                    }
                } else if (player.isWillingToTryNew()) {
                    double avgScore = ratings.values().stream()
                            .mapToInt(Integer::intValue)
                            .average().orElse(1.5);
                    totalScore += avgScore;
                } else {
                    shouldExclude = true;
                }
            }

            if (!shouldExclude) {
                if (countWeakScores > 0) {
                    totalScore += countWeakScores / (1 + Math.log(countWeakScores + 1));
                }
                gameScores.put(game, totalScore);
                maxScore = Math.max(maxScore, totalScore);
            }
        }

        double finalMaxScore = maxScore;
        List<GameWithPleasure> result = gameScores.entrySet().stream()
                .filter(entry -> entry.getValue() == finalMaxScore)
                .map(entry -> new GameWithPleasure(entry.getKey().getId(), entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.toList());

        return result.isEmpty() ? List.of(new GameWithPleasure(-1, "Нет подходящих игр", 0)) : result;
    }
}
