<template>
  <div class="column">
    <h2>🏆 Результаты</h2>
    <button @click="pickGame">Подобрать игру</button>
    <div v-if="bestGame">
      <h3>Лучшая игра: {{ bestGame.gameName }}</h3>
      <p v-if="bestGame.minPlayers !== undefined && bestGame.maxPlayers !== undefined">
        Подходит для {{ bestGame.minPlayers }}-{{ bestGame.maxPlayers }} игроков.
      </p>
    </div>
    <div v-else>
      <p>Невозможно выбрать игру 😢</p>
    </div>
  </div>
</template>

<script>
import gamesApi from '@/api/games'; // Импорт API-клиента

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
        const gamesWithPleasure = await gamesApi.calculatePleasure();

        console.log('Ответ API:', gamesWithPleasure);

        if (!Array.isArray(gamesWithPleasure) || gamesWithPleasure.length === 0) {
          this.bestGame = null;
          return;
        }

        const numPlayers = this.players.length;

        const filteredGames = gamesWithPleasure.filter(
          (game) =>
            game.minPlayers !== undefined &&
            game.maxPlayers !== undefined &&
            game.minPlayers <= numPlayers &&
            game.maxPlayers >= numPlayers
        );

        console.log('Отфильтрованные игры:', filteredGames);

        this.bestGame =
          filteredGames.sort((a, b) => b.totalPleasure - a.totalPleasure)[0] || null;

        console.log('Выбранная игра:', this.bestGame);
      } catch (error) {
        console.error('Ошибка при выборе игры:', error);
        alert('Не удалось выбрать игру.');
      }
    },
  },
};
</script>
