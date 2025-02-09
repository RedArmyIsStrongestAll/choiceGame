package ru.mephi.atomSkills25.commandA.choiceGame.repositories.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Game;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.NewGame;
import ru.mephi.atomSkills25.commandA.choiceGame.repositories.GameRepository;

import java.util.List;

@Repository
@Slf4j
public class GameRepositoryImpl implements GameRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_GAMES = "SELECT id, name, min_players, max_players FROM games";
    private static final String INSERT_GAME = "INSERT INTO games (name, min_players, max_players) VALUES (?, ?, ?) RETURNING id";
    private static final String UPDATE_GAME = "UPDATE games SET name = ?, min_players = ?, max_players = ? WHERE id = ?";
    private static final String DELETE_GAME = "DELETE FROM games WHERE id = ?";
    private static final String SELECT_GAME = "SELECT id, name, min_players, max_players FROM games WHERE id = ?";

    public List<Game> getAllGames() {
        try {
            return jdbcTemplate.query(SELECT_ALL_GAMES, (rs, rowNum) -> {
                Game game = new Game();
                game.setId(rs.getInt("id"));
                game.setName(rs.getString("name"));
                game.setMinPlayers(rs.getInt("min_players"));
                game.setMaxPlayers(rs.getInt("max_players"));
                return game;
            });
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Integer addGame(NewGame newGame) {
        try {
            return jdbcTemplate.queryForObject(INSERT_GAME, Integer.class,
                    newGame.getName(), newGame.getMinPlayers(), newGame.getMaxPlayers());
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void updateGame(Game game) {
        try {
            jdbcTemplate.update(UPDATE_GAME, game.getName(), game.getMinPlayers(), game.getMaxPlayers(), game.getId());
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void deleteGame(Long id) {
        try {
            jdbcTemplate.update(DELETE_GAME, id);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Game getGame(Integer id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_GAME, (rs, rowNum) -> {
                Game game = new Game();
                game.setId(rs.getInt("id"));
                game.setName(rs.getString("name"));
                game.setMinPlayers(rs.getInt("min_players"));
                game.setMaxPlayers(rs.getInt("max_players"));
                return game;
            }, id);
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
