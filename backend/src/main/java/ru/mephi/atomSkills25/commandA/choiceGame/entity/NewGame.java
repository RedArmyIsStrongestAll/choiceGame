package ru.mephi.atomSkills25.commandA.choiceGame.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewGame {
    private String name;
    private int minPlayers;
    private int maxPlayers;
}
