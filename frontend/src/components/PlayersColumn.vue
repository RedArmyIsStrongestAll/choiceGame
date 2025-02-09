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
import { watch, watchEffect } from 'vue';

import playersApi from '@/api/players';


export default {


watchEffect(() => {
  // Когда изменяется список игр, добавляем их в newPlayer.ratingsById
  if (Array.isArray(this.games)) {
    this.games.forEach(game => {
      if (!(game.id in this.newPlayer.ratingsById)) {
        this.newPlayer.ratingsById[game.id] = null;
      }
    });
  }
});

  props: {
    loadGames: Function, // Функция загрузки игр передается из MainScreen
  },
  data() {
    return {
      isModalOpen: false,
      newPlayer: {
        name: '',
        willingToTryNew: false,
        ratingsById: {}, // Используем ratingsById вместо ratings
      },
      players: [],
      games: [], // Теперь храним игры здесь, а не в props
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
          willingToTryNew: player.willingToTryNew,
          ratingsById: player.ratingsById || {}, // Обрабатываем рейтинг
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
      this.loadGames2()
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
          ratingsById: this.newPlayer.ratingsById, // Передаем ratingsById
        };
        await playersApi.addPlayer(playerData);
        await this.loadPlayers();
        await this.loadGames(); // Загружаем новые игры, чтобы обновить их в списке
        this.closeModal(); // Закрыть модальное окно после добавления игрока
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
