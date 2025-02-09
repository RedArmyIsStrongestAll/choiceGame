CREATE DATABASE choice_games_db;
SET search_path TO choice_games_db, public;

\c choice_games_db;

CREATE TABLE public.games (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    min_players INT NOT NULL CHECK (min_players > 0),
    max_players INT NOT NULL CHECK (max_players >= min_players)
);
ALTER TABLE public.games OWNER TO postgres;