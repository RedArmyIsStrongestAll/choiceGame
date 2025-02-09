<template>
  <div class="column">
    <h2>👥 Игроки</h2>

    <form @submit.prevent="openModal">
      <input v-model="newPlayer.name" placeholder="Имя игрока" required />
      <label>
        <input type="checkbox" v-model="newPlayer.willingToTryNew" />
        Готов пробовать новые игры
      </label>
      <button type="submit">Добавить игрока</button>
    </form>

    <ul>
      <li v-for="player in players" :key="player.id">
        {{ player.name }}
        
        <span v-if="player.willingToTryNew !== undefined">
          ({{ player.willingToTryNew ? 'Готов к новым играм' : 'Не готов к новым играм' }})
        </span>
        
        <button @click="deletePlayer(player.id)">🗑️</button>

        <ul v-if="player.ratingsById && Object.keys(player.ratingsById).length > 0">
          <li v-for="(rating, gameId) in player.ratingsById" :key="gameId">
            {{ getGameName(gameId) }}: {{ ratingText[rating] }}
          </li>
        </ul>
      </li>
    </ul>

    <!-- Всплывающее окно -->
    <div v-if="isModalOpen" class="modal-overlay">
      <div class="modal-content">
        <h3>Оцените игры для {{ newPlayer.name }}</h3>

        <div v-for="game in games" :key="game.id" class="game-rating">
          <span>{{ game.name }}</span>
          <select v-model="newPlayer.ratingsById[game.id]">
            <option :value="null">Не выбрано</option>
            <option :value="3">Любимая</option>
            <option :value="2">Приятная</option>
            <option :value="1">Нежелательная</option>
            <option :value="0">Ненавистная</option>
          </select>
        </div>

        <button @click="addPlayer">Подтвердить</button>
        <button @click="closeModal">Отмена</button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, watchEffect } from 'vue'; // Убрали onMounted
import playersApi from '@/api/players';
import gamesApi from '@/api/games';

export default {
  props: {
    loadGames: Function,
  },
  setup() { // Убрали props, так как они не используются
    const games = ref([]);
    const newPlayer = ref({
      name: '',
      willingToTryNew: false,
      ratingsById: {},
    });

    watchEffect(() => {
      if (Array.isArray(games.value)) {
        games.value.forEach(game => {
          if (!(game.id in newPlayer.value.ratingsById)) {
            newPlayer.value.ratingsById[game.id] = null;
          }
        });
      }
    });

    return {
      games,
      newPlayer,
    };
  },
  data() {
    return {
      isModalOpen: false,
      players: [],
      ratingText: {
        3: 'Любимая',
        2: 'Приятная',
        1: 'Нежелательная',
        0: 'Ненавистная',
      },
    };
  },
  async created() {
    try {
      await this.loadPlayers();
    } catch (error) {
      console.error('Ошибка при загрузке игроков:', error);
    }
  },
  methods: {
    async loadPlayers() {
      try {
        const playersData = await playersApi.getPlayers();
        this.players = playersData.map(player => ({
          id: player.id,
          name: player.name,
          willingToTryNew: player.willingToTryNew,
          ratingsById: player.ratingsById || {},
        }));
      } catch (error) {
        alert('Не удалось загрузить список игроков.');
      }
    },
    async loadGames2() {
      try {
        this.games = await gamesApi.getGames();
      } catch (error) {
        alert('Не удалось загрузить список игр.');
      }
    },
    openModal() {
      this.loadGames2();
      this.isModalOpen = true;
    },
    closeModal() {
      this.isModalOpen = false;
    },
    async addPlayer() {
      try {
        const playerData = {
          name: this.newPlayer.name,
          willingToTryNew: this.newPlayer.willingToTryNew,
          ratingsById: this.newPlayer.ratingsById,
        };
        await playersApi.addPlayer(playerData);
        await this.loadPlayers();
        await this.loadGames();
        this.closeModal();
        this.newPlayer = { name: '', willingToTryNew: false, ratingsById: {} };
      } catch (error) {
        const errorMessage = error.response?.data?.detail || 'Не удалось добавить игрока.';
        alert(`Ошибка при добавлении игрока: ${errorMessage}`);
      }
    },
    async deletePlayer(id) {
      try {
        await playersApi.deletePlayer(id);
        this.players = this.players.filter(player => player.id !== id);
      } catch (error) {
        alert('Не удалось удалить игрока.');
      }
    },
    getGameName(gameId) {
      const game = this.games.find(game => game.id === gameId);
      return game ? game.name : `Игра #${gameId}`;
    },
  },
  watch: {
    games: {
      handler(newGames) {
        console.log('Список игр обновился:', newGames);
      },
      deep: true,
    },
  },
};
</script>

<style scoped>
.column {
  border: 1px solid #ddd;
  padding: 20px;
  border-radius: 8px;
  background-color: #f9f9f9;
}

input, button, select {
  margin: 5px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  background-color: #42b983;
  color: white;
  cursor: pointer;
}

button:hover {
  opacity: 0.8;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  background-color: #fff;
  padding: 10px;
  margin-bottom: 5px;
  border-radius: 4px;
  border: 1px solid #ddd;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
}

.game-rating {
  margin-bottom: 10px;
}
</style>