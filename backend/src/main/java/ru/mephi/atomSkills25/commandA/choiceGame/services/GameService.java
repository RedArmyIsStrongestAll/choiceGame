package ru.mephi.atomSkills25.commandA.choiceGame.services;

import ru.mephi.atomSkills25.commandA.choiceGame.entity.Game;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.NewGame;

import java.util.List;

public interface GameService {
    List<Game> getAllGames();

    Integer addGame(NewGame newGame);

    void updateGame(Game game);

    void deleteGame(Long id);

    Game getGame(Integer id);
}
