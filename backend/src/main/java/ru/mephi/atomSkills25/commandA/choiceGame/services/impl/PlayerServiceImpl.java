package ru.mephi.atomSkills25.commandA.choiceGame.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Game;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.NewPlayer;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Player;
import ru.mephi.atomSkills25.commandA.choiceGame.repositories.PlayerRepository;
import ru.mephi.atomSkills25.commandA.choiceGame.services.GameService;
import ru.mephi.atomSkills25.commandA.choiceGame.services.PlayerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameService gameService;

    public List<Player> getAllPlayers() {
        return playerRepository.getAllPlayers();
    }

    public Integer addPlayer(NewPlayer newPlayer) {
        Map<Game, Integer> gameRatings = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : newPlayer.getRatingsById().entrySet()) {
            int gameId = entry.getKey();
            int rating = entry.getValue();
            if (rating < 0 || rating > 4) {
                return null;
            }
            Game game = gameService.getGame(gameId);
            if (game != null) {
                gameRatings.put(game, rating);
            }
        }

        return playerRepository.addPlayer(newPlayer, gameRatings);
    }

    public void deletePlayer(Long id) {
        playerRepository.deletePlayer(id);
    }
}
