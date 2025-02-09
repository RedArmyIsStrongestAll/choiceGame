package ru.mephi.atomSkills25.commandA.choiceGame.services;

import ru.mephi.atomSkills25.commandA.choiceGame.entity.Game;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.GameWithPleasure;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Player;

import java.util.List;

public interface RatingService {
    List<GameWithPleasure> calculatePleasure(List<Game> gameList, List<Player> playerList);
}

