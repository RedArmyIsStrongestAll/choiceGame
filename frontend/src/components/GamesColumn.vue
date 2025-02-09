<template>
  <div class="column">
    <h2>🎮 Игры</h2>
    <form @submit.prevent="addGame">
      <input v-model="newGame.name" placeholder="Название игры" required />
      <input v-model="newGame.minPlayers" type="number" placeholder="Минимум игроков" required />
      <input v-model="newGame.maxPlayers" type="number" placeholder="Максимум игроков" required />
      <button type="submit">Добавить игру</button>
    </form>
    <ul>
      <li v-for="game in games" :key="game.id">
        {{ game.name }} ({{ game.minPlayers }}-{{ game.maxPlayers }} игроков)
        <button @click="openEditModal(game)">✏️</button>
        <button @click="deleteGame(game.id)">🗑️</button>
      </li>
    </ul>

    <!-- Модальное окно для редактирования -->
    <div v-if="isEditModalOpen" class="modal">
      <div class="modal-content">
        <h3>Редактировать игру</h3>
        <form @submit.prevent="updateGame">
          <input v-model="editGame.name" placeholder="Название игры" required />
          <input v-model="editGame.minPlayers" type="number" placeholder="Минимум игроков" required />
          <input v-model="editGame.maxPlayers" type="number" placeholder="Максимум игроков" required />
          <button type="submit">Сохранить</button>
          <button type="button" @click="closeEditModal">Отмена</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import gamesApi from '@/api/games'; // Импортируем API-клиент

export default {
  data() {
    return {
      newGame: {
        name: '',
        minPlayers: '',
        maxPlayers: '',
      },
      games: [],
      isEditModalOpen: false,
      editGame: {
        id: null,
        name: '',
        minPlayers: '',
        maxPlayers: '',
      },
    };
  },
  async created() {
    // Загружаем список игр при создании компонента
    await this.loadGames();
  },
  methods: {
    // Загрузить список игр
    async loadGames() {
      try {
        this.games = await gamesApi.getGames();
      } catch (error) {
        alert('Не удалось загрузить список игр.');
      }
    },

    // Добавить игру
    async addGame() {
      try {
        const newGame = await gamesApi.addGame(this.newGame);
        this.games.push(newGame);
        this.newGame = { name: '', minPlayers: 0, maxPlayers: 0 };
      } catch (error) {
        alert('Не удалось добавить игру.');
      }
    },

    // Открыть модальное окно для редактирования
    openEditModal(game) {
      if (!game.id) {
        alert('Ошибка: ID игры не найден.');
        return;
      }
      this.editGame = { ...game };
      this.isEditModalOpen = true;
    },

    // Закрыть модальное окно
    closeEditModal() {
      this.isEditModalOpen = false;
      this.editGame = { id: null, name: '', minPlayers: 0, maxPlayers: 0 };
    },

    // Обновить игру
    async updateGame() {
      try {
        await gamesApi.updateGame(this.editGame.id, this.editGame);
        const index = this.games.findIndex((game) => game.id === this.editGame.id);
        if (index !== -1) {
          this.games[index] = { ...this.editGame };
        }
        this.closeEditModal();
      } catch (error) {
        alert('Не удалось обновить игру.');
      }
    },

          // Удалить игру
    async deleteGame(id) {
      try {
        await gamesApi.deleteGame(id);
        this.games = this.games.filter((game) => game.id !== id); // Удаляем игру из списка
      } catch (error) {
        alert('Не удалось удалить игру.');
      }
    },
  },



};
</script>

<style scoped>
/* Уникальные стили для модального окна */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 300px;
}

.modal-content h3 {
  margin-top: 0;
}

.modal-content button {
  margin: 5px;
}

/* Стили для кнопки удаления */
button {
  margin: 5px;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  opacity: 0.8;
}

button[type="submit"] {
  background-color: #42b983;
  color: white;
}

button[type="button"] {
  background-color: #ff4d4d;
  color: white;
}
</style>