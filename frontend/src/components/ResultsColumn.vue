<template>
  <div class="column">
    <h2>🏆 Результаты</h2>
    <button @click="pickGame">Подобрать игру</button>
    <div v-if="bestGame">
      <h3>Лучшая игра: {{ bestGame.name }}</h3>
      <p>Подходит для {{ bestGame.minPlayers }}-{{ bestGame.maxPlayers }} игроков.</p>
    </div>
    <div v-else>
      <p>Невозможно выбрать игру 😢</p>
    </div>
  </div>
</template>

<script>
import gamesApi from '@/api/games'; // Импортируйте ваш API-клиент

export default {
  props: {
    games: {
      type: Array,
      required: true,
    },
    players: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      bestGame: null,
    };
  },
  methods: {
    async pickGame() {
      try {
        // Запрос на подсчет суммарного удовольствия для всех игр
        const gamesWithPleasure = await gamesApi.calculatePleasure();

        // Выбор игры с наибольшим уровнем удовольствия, которая подходит по количеству игроков
        const numPlayers = this.players.length;
        this.bestGame = gamesWithPleasure
          .filter(
            (game) => game.minPlayers <= numPlayers && game.maxPlayers >= numPlayers
          )
          .sort((a, b) => b.totalPleasure - a.totalPleasure)[0];
      } catch (error) {
        alert('Не удалось выбрать игру.');
      }
    },
  },
};
</script>
