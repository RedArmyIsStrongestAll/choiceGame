import axios from 'axios';

import { API_BASE_URL } from '@/config'; // Импортируем API_BASE_URL

export default {
  // Получить список всех игроков
  async getPlayers() {
    try {
      const response = await axios.get(`${API_BASE_URL}/players`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при получении списка игроков:', error);
      throw error;
    }
  },

  // Добавить нового игрока
  async addPlayer(player) {
    try {
      const response = await axios.post(`${API_BASE_URL}/players`, player);
      return response.data;
    } catch (error) {
      console.error('Ошибка при добавлении игрока:', error);
      throw error;
    }
  },

  // Удалить игрока
  async deletePlayer(id) {
    try {
      const response = await axios.delete(`${API_BASE_URL}/players/${id}`);
      return response.data;
    } catch (error) {
      console.error('Ошибка при удалении игрока:', error);
      throw error;
    }
  },
};