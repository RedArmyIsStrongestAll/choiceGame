package ru.mephi.atomSkills25.commandA.choiceGame.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Game;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.NewPlayer;
import ru.mephi.atomSkills25.commandA.choiceGame.entity.Player;
import ru.mephi.atomSkills25.commandA.choiceGame.repositories.PlayerRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {
    private final Map<Integer, Player> players = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public List<Player> getAllPlayers() {
        return players.values().stream().toList();
    }

    public Integer addPlayer(NewPlayer newPlayer, Map<Game, Integer> gameRatings) {
        int id = idCounter.getAndIncrement();
        Player player = new Player(id, newPlayer.getName(), gameRatings, newPlayer.isWillingToTryNew());
        players.put(id, player);
        return id;
    }

    public void deletePlayer(Integer id) {
        players.remove(id);
    }
}
