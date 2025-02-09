package ru.mephi.atomSkills25.commandA.choiceGame.repositories;

import ru.mephi.atomSkills25.commandA.choiceGame.entity.Game;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.NewPlayer;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Player;

import java.util.List;
import java.util.Map;

public interface PlayerRepository {
    List<Player> getAllPlayers();

    Integer addPlayer(NewPlayer newPlayer, Map<Game, Integer> gameRatings);

    void deletePlayer(Long id);
}
