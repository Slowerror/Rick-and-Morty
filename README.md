# Rick-and-Morty
Простое приложение, которое использует [The Rick and Morty API](https://rickandmortyapi.com/) для получения информации о персонажах и эпизодах. Основная цель приложения - показать, как можно загружать данные постранично, с использованием Paging library.

<b>Функционал приложения:</b>
- Получение списка персонажей, эпизодов;
- Получение подробной информации о персонаже;
- Поиск персонажа через запрос к Api.

## Стэк
- Язык: Kotlin
- Архитектура: Clean Architecture, MVVM
- DI: Hilt
- Сеть: Retrofit, OkHttp, Moshi
- Многопоточность: Kotin Coroutines & Flow
- Jetpack: Paging library, Navigation Component
- Загрузка изображений: Coil
