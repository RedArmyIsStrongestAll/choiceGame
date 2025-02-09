import axios from 'axios';

import { API_BASE_URL } from '@/config'; // Импортируем API_BASE_URL

export default {
  // Получить список всех игр
  async getGames() {
    try {
      const response = await axios.get(`${API_BASE_URL}/games`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при получении списка игр:', error);
      throw error;
    }
  },

  // Добавить новую игру
  async addGame(game) {
    try {
      const response = await axios.post(`${API_BASE_URL}/games`, game);
      return response.data;
    } catch (error) {
      console.error('Ошибка при добавлении игры:', error);
      throw error;
    }
  },

  // Обновить игру
  async updateGame(id, game) {
    try {
      const response = await axios.put(`${API_BASE_URL}/games/${id}`, game);
      return response.data;
    } catch (error) {
      console.error('Ошибка при обновлении игры:', error);
      throw error;
    }
  },

  // Удалить игру
  async deleteGame(id) {
    try {
      const response = await axios.delete(`${API_BASE_URL}/games/${id}`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при удалении игры:', error);
      throw error;
    }
  },


    // Подсчитать суммарное удовольствие для всех игр
    async calculatePleasure() {
      try {
        const response = await axios.get(`${API_BASE_URL}/calculate`);
        return response.data;
      } catch (error) {
        console.error('Ошибка при подсчете удовольствия для игр:', error);
        throw error;
      }
    },
};