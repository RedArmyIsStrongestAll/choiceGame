package ru.mephi.atomSkills25.commandA.choiceGame.repositories;

import ru.mephi.atomSkills25.commandA.choiceGame.entity.Game;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.NewGame;

import java.util.List;

public interface GameRepository {
    List<Game> getAllGames();

    Integer addGame(NewGame newGame);

    void updateGame(Game game);

    void deleteGame(Integer id);

    Game getGame(Integer id);
}
