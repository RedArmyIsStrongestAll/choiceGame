<template>
  <MainScreen />
</template>

<script>
import MainScreen from './screens/MainScreen.vue';

export default {
  components: {
    MainScreen,
  },
};
</script>

<style>
/* Общие стили для всех компонентов */
.column {
  border: 1px solid #ddd;
  padding: 20px;
  border-radius: 8px;
  background-color: #f9f9f9;
}

input, select, button {
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
  background-color: #369f6e;
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

.player {
  margin-bottom: 20px;
}

.preference {
  margin-bottom: 10px;
}
</style>

