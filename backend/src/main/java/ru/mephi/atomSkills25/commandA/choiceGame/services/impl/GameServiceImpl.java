package ru.mephi.atomSkills25.commandA.choiceGame.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Game;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.NewGame;
import ru.mephi.atomSkills25.commandA.choiceGame.repositories.GameRepository;
import ru.mephi.atomSkills25.commandA.choiceGame.services.GameService;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> getAllGames() {
        return gameRepository.getAllGames();
    }

    public Integer addGame(NewGame newGame) {
        return gameRepository.addGame(newGame);
    }

    public void updateGame(Game game) {
        gameRepository.updateGame(game);
    }

    public void deleteGame(Long id) {
        gameRepository.deleteGame(id);
    }

    public Game getGame(Integer id) {
        return gameRepository.getGame(id);
    }
}
