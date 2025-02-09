package ru.mephi.atomSkills25.commandA.choiceGame.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.*;
import ru.mephi.atomSkills25.commandA.choiceGame.services.GameService;
import ru.mephi.atomSkills25.commandA.choiceGame.services.PlayerService;
import ru.mephi.atomSkills25.commandA.choiceGame.services.RatingService;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @PostMapping("/games")
    public Integer addGame(@RequestBody NewGame newGame) {
        return gameService.addGame(newGame);
    }

    @PutMapping("/games/{id}")
    public void updateGame(@RequestBody Game game) {
        gameService.updateGame(game);
    }

    @DeleteMapping("/games/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }

    @GetMapping("/players")
    public List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @DeleteMapping("/players/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }

    @PostMapping("/players")
    public ResponseDTO<Integer> addPlayer(@RequestBody NewPlayer newPlayer) {
        Integer id = playerService.addPlayer(newPlayer);
        if (id != null) {
            return new ResponseDTO<>(200, id);
        } else {
            return new ResponseDTO<>(400, "Некоректный ввод пожеланий");
        }
    }

    @GetMapping("/calculate")
    public List<GameWithPleasure> calculatePleasure() {
        return ratingService.calculatePleasure(gameService.getAllGames(),
                playerService.getAllPlayers());
    }
}
