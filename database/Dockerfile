FROM postgres:latest

ENV POSTGRES_DB=board_games_db
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres

COPY dump.sql /docker-entrypoint-initdb.d/


