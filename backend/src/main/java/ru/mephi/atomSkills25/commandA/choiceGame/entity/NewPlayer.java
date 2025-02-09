package ru.mephi.atomSkills25.commandA.choiceGame.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class NewPlayer {
    private String name;
    private Map<Integer, Integer> ratingsById;
    private boolean willingToTryNew;
}
