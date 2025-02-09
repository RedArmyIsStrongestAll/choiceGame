<template>
  <div class="main-screen">
    <h1>Подбор настольных игр</h1>
    <div class="content">
      <GamesColumn @game-added="handleGameAdded" class="column" />
      <PlayersColumn :games="games" :players="players" class="column" />
      <ResultsColumn :games="games" :players="players" class="column" />
    </div>
  </div>
</template>

<script>
import GamesColumn from '../components/GamesColumn.vue';
import PlayersColumn from '../components/PlayersColumn.vue';
import ResultsColumn from '../components/ResultsColumn.vue';
import gamesApi from '@/api/games'; // Импортируем API-клиент для игр
import playersApi from '@/api/players'; // Импортируем API-клиент для игроков

export default {
  components: {
    GamesColumn,
    PlayersColumn,
    ResultsColumn,
  },
  data() {
    return {
      games: [], // Список игр
      players: [], // Список игроков
    };
  },
  async created() {
    // Загружаем список игр и игроков при создании компонента
    await this.loadGames();
    await this.loadPlayers();
  },
  methods: {
    // Метод для обработки добавления новой игры
    handleGameAdded(newGame) {
      this.games.push(newGame); // Добавляем новую игру в список
    },

    // Метод для загрузки списка игроков через API
    async loadPlayers() {
      try {
        const playersData = await playersApi.getPlayers(); // Получаем список игроков
        this.players = playersData.map(player => ({
          id: player.id,
          name: player.name,
          willingToTryNew: player.willingToTryNew,
          ratings: player.ratings || {}, // Если рейтинги есть, сохраняем их
        }));
      } catch (error) {
        alert('Не удалось загрузить список игроков.');
      }
    },

    // Метод для загрузки списка игр через API
    async loadGames() {
      try {
        this.games = await gamesApi.getGames(); // Получаем список игр
      } catch (error) {
        alert('Не удалось загрузить список игр.');
      }
    },
  },
};
</script>

<style scoped>
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
