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
        
        <!-- Если поле willingToTryNew существует, выводим сообщение -->
        <span v-if="player.willingToTryNew !== undefined">
          ({{ player.willingToTryNew ? 'Готов к новым играм' : 'Не готов к новым играм' }})
        </span>
        
        <button @click="deletePlayer(player.id)">🗑️</button>

        <!-- Если есть рейтинги, то выводим их -->
        <ul v-if="player.ratings && Object.keys(player.ratings).length > 0">
          <li v-for="(rating, game) in player.ratings" :key="game">
            {{ game }}: {{ ratingText[rating] }}
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
          <select v-model="newPlayer.ratings[game.id]">
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
import playersApi from '@/api/players';

export default {
  props: {
    games: Array, // Список игр передается в компонент
  },
  data() {
    return {
      isModalOpen: false,
      newPlayer: {
        name: '',
        willingToTryNew: false,
        ratings: {},
      },
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
    await this.loadPlayers();
  },
  methods: {
    async loadPlayers() {
      try {
        const playersData = await playersApi.getPlayers();
        console.log(playersData);
        this.players = playersData.map(player => ({
          id: player.id,
          name: player.name,
        }));
      } catch (error) {
        alert('Не удалось загрузить список игроков.');
      }
    },

    openModal() {
      this.isModalOpen = true;
    },
    closeModal() {
      this.isModalOpen = false;
    },

    async addPlayer() {
      try {
        console.log(this.newPlayer)
        await playersApi.addPlayer(this.newPlayer);
        await this.loadPlayers();
        this.closeModal(); // Закрыть модальное окно после добавления игрока
        this.newPlayer = { name: '', willingToTryNew: false, ratings: {} };
      } catch (error) {
        // Выводим подробную информацию о том, что не так с запросом
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
