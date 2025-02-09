CREATE DATABASE board_games_db;

\c board_games_db; 

CREATE TABLE games (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    min_players INT NOT NULL CHECK (min_players > 0),
    max_players INT NOT NULL CHECK (max_players >= min_players)
);