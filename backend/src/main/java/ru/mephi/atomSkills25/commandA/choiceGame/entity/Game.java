package ru.mephi.atomSkills25.commandA.choiceGame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private int id;
    private String name;
    private int minPlayers;
    private int maxPlayers;
}
