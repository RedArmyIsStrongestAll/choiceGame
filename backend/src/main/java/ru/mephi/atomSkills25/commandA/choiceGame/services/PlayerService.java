package ru.mephi.atomSkills25.commandA.choiceGame.services;

import ru.mephi.atomSkills25.commandA.choiceGame.entity.NewPlayer;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Player;

import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayers();

    Integer addPlayer(NewPlayer newPlayer);

    void deletePlayer(Integer id);
}
