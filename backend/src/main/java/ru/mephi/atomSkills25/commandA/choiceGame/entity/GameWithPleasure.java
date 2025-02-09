package ru.mephi.atomSkills25.commandA.choiceGame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GameWithPleasure {
    private int gameId;
    private String gameName;
    private double totalPleasure;
}
