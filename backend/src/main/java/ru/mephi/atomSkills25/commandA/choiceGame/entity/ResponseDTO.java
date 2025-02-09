package ru.mephi.atomSkills25.commandA.choiceGame.entity;

import lombok.Data;

@Data
public class ResponseDTO<T> {
    int code;
    String message;
    T body;

    public ResponseDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseDTO(int code, T body) {
        this.code = code;
        this.body = body;
    }
}
