package ru.mephi.atomSkills25.commandA.choiceGame.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private Integer id;
    private String name;
    private Map<Game, Integer> ratings;
    private boolean willingToTryNew;
}
