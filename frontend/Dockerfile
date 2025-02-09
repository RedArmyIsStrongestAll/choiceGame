FROM node:20 AS frontend

WORKDIR /app

COPY package*.json ./

# Устанавливаем зависимости фронтенда
RUN npm install

# Копируем весь исходный код фронтенда в контейнер
COPY . .

# Собираем фронтенд
RUN npm run build

# Используем официальный образ для Nginx для обслуживания статических файлов
FROM nginx:alpine

# Копируем собранный фронтенд в директорию Nginx для обслуживания статических файлов
COPY --from=frontend /app/dist /usr/share/nginx/html

# Открываем порт для Nginx
EXPOSE 80

# Команда для запуска Nginx
CMD ["nginx", "-g", "daemon off;"]
