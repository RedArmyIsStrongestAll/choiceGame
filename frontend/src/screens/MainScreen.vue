<template>
  <div class="main-screen">
    <h1>Подбор настольных игр</h1>
    <div class="content">
      <GamesColumn @game-added="handleGameAdded" class="column" />
      <PlayersColumn :games="games" class="column" />
      <ResultsColumn :games="games" :players="players" class="column" />
    </div>
  </div>
</template>

<script>
import GamesColumn from '../components/GamesColumn.vue';
import PlayersColumn from '../components/PlayersColumn.vue';
import ResultsColumn from '../components/ResultsColumn.vue';
import gamesApi from '@/api/games'; // Импортируем API-клиент
import playersApi from '@/api/players'; // Импортируем API-клиент

export default {
  components: {
    GamesColumn,
    PlayersColumn,
    ResultsColumn,
  },
  data() {
    return {
      games: [],
      players: [],
    };
  },
  async created() {
    // Загружаем список игр при создании компонента
    await this.loadGames();
  },
  methods: {
    handleGameAdded(newGame) {
      this.games.push(newGame);
    },

    async loadPlayers() {
      try {
        const playersData = await playersApi.getPlayers();
        this.players = playersData.map(player => ({
          id: player.id,
          name: player.name,
          willingToTryNew: player.willingToTryNew,
          ratings: player.ratings || {},
        }));
      } catch (error) {
        alert('Не удалось загрузить список игроков.');
      }
    },

    // Метод для загрузки списка игр через API
    async loadGames() {
      try {
        this.games = await gamesApi.getGames(); // Загружаем игры с сервера
      } catch (error) {
        alert('Не удалось загрузить список игр.');
      }
    },
  },
};
</script>

<style>
.main-screen {
  padding: 20px;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  color: #2c3e50;
}

.content {
  display: flex;
  gap: 20px;
}

.column {
  flex: 1; /* Равномерное распределение ширины */
  border: 1px solid #ddd;
  padding: 20px;
  border-radius: 8px;
  background-color: #f9f9f9;
}
</style>