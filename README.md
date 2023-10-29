# HandsApp
Социальная сеть для размещения фото, видео (веб-версия)

Адрес в сети интернет: http://handsapp.online

**Старт проекта:** 15 марта 2022 г.

**Выпуск первой стабильной версии:** 30 сентября 2023 г. (в текущем репозитории), 08 ноября 2022 г. (в старом репозитории)

**Участие в проекте:**
В проекте может участвовать любой желающий. Нужны дизайнер и верстальщик

:computer: **Требования к софту:**
1. Spring Framework
2. Apache Maven 3.8.3
3. Apache Tomcat 9.0.37
4. Java 15.0.1
5. PostgreSQL 12
6. Apache Kafka 3.3.1
7. Логирование Slf4j-log4j

:computer: **Разворачивание и запуск проекта:**
1. Клонируем проект: git clone https://github.com/pavlov150/HandsApp.git
2. Создаем БД в PostgreSQL и запускаем скрипт HandsAppDB.sql для создания всех необходимых таблиц и их первичного наполнения
3. Открываем проект в IntelliJ IDEA и в JpaConfig.java указываем имя и пароль от вашей БД и имя БД
4. Настраиваем Tomcat
5. Запускаем приложение

:computer: **Переменные окружения:**

Oauth2.0 (application.properties)
1. ${CLIENT_ID} - spring.security.oauth2.client.registration.google.client-id
2. ${CLIENT_SECRET} - spring.security.oauth2.client.registration.google.client-secret
3. ${SCOPE} - spring.security.oauth2.client.registration.google.scope

DB:
1. ${DB_URL} - db.postgres.url
2. ${DB_LOGIN} - db.postgres.login
3. ${DB_PASSWORD} - db.postgres.password


:white_check_mark: **Что реализовано:**
1. Регистрация пользователя
2. Авторизация и аутентификация пользователя
3. Создание поста
4. Редактирование поста
5. Удаление поста
6. Добавление/удаление комментариев к постам
7. Просмотр постов (3 варианта): плитка, список, просмотр одного поста
8. Редактирование профиля
9. Добавление аватарки в профиле
10. Подсчет количества постов у пользователя
11. Изменение размера изображения
12. Обрезка изображения
13. Форма обратной связи
14. Аватарки пользователей в комментариях
15. Просмотр страницы другого пользователя
16. Подписка на пользователей
17. Отписка от пользователей
18. Страница с постами по подписке
19. Тексты по умолчанию при отсутствии подписок и постов
20. Лайки
21. Добавление видео в формате mp4, mov
22. Поиск пользователей
23. Поиск постов по тегам
24. QR-код
25. REST-API
26. Реализована интернационализация (локализация) - Русский и Английский языки
27. Удаление профиля пользователя
28. Подписка на пользователей в рекомендациях
29. Форма обратной связи (переделана отправка сообщений через Kafka + отправка через REST API в Kafka)
30. Простой чат между пользователями
31. Просмотр постов, которым были поставлены лайки
32. Поиск связей, основанный на теории шести рукопожатий. Осуществляется поиск пользователя и выводится наглядная визуализация связей на странице
33. Авторизация через Google
34. Работа приложения по протоколу https.

:abcd: **REST-API:**

Используется Basic-Auth

Выборка постов по ключевому слову

GET http://handsapp.online/api/post?search=москва


Выборка всех постов

GET http://handsapp.online/api/post


Поиск поста по ID

GET http://handsapp.online/api/post/284


Создание поста

POST http://handsapp.online/api/post/

Пример запроса

```json
{

    "photo": "gfpdohum.png",
    
    "extFile": "png",
    
    "content": "Test API create",
    
    "createdAt": "2022-06-12 02:18:58"
}
```

Редактирование поста

PUT http://handsapp.online/api/post/294

Пример запроса

```json
{

    "photo": "gfpdohum.png",
    
    "extFile": "png",
    
    "content": "Test API edit",
    
    "createdAt": "2022-06-12 02:18:59"
}
```

Удаление поста

DELETE http://handsapp.online/api/post/295


:iphone: **Внешний вид приложения на текущий момент:**

Страница c результатами поиска связей

![photo_2023-01-29_13-06-36](https://user-images.githubusercontent.com/15989675/224484659-9fd00c35-0e5f-4882-b5b9-7be9bc7aa1de.jpg)

Страница пользователя без подписок

![2022-12-11_16-09-23](https://user-images.githubusercontent.com/15989675/206905853-dcd85916-4795-4070-8738-f49a0cb8144f.jpg)

![2022-12-11_16-13-47](https://user-images.githubusercontent.com/15989675/206905867-8f90f421-6bc6-4792-96fd-b0034deec64c.jpg)

Редактирование профиля

![2022-12-11_16-12-18](https://user-images.githubusercontent.com/15989675/206905873-f514dc47-253a-454b-9599-fd446d311a8e.jpg)

Создание поста

![2022-12-11_16-14-06](https://user-images.githubusercontent.com/15989675/206905885-ffe85b44-8787-46ce-9838-bfff98a3ad6e.jpg)

![2022-06-11_02-17-23](https://user-images.githubusercontent.com/15989675/173162100-e1d511b5-d6eb-46e4-a581-c821376ea52c.jpg)

![2022-06-11_02-17-52](https://user-images.githubusercontent.com/15989675/173162104-92728f23-4513-44bf-820a-aa80d1ba76ab.jpg)

![2022-06-11_02-18-09](https://user-images.githubusercontent.com/15989675/173162139-71dc521b-af7b-4e61-ad51-15b13dd21d26.jpg)

Страница с постами текущего пользователя

![2022-12-11_16-11-14](https://user-images.githubusercontent.com/15989675/206905905-f1f8a51f-8c6d-4c6b-9b44-66336cf63700.jpg)

Изменение поста

![2022-06-11_02-18-29](https://user-images.githubusercontent.com/15989675/173162194-d93f375d-b7b9-41f9-aa32-1e3cb000c1ba.jpg)

Страница с постами по подписке

![2022-12-11_16-09-23](https://user-images.githubusercontent.com/15989675/206905928-7cb3ed88-a8fc-4ac8-a67c-8ed057b3431c.jpg)

Страница пользователя на которого можно подписаться или отписаться

![2022-06-11_02-19-06](https://user-images.githubusercontent.com/15989675/173162299-cf8b966c-f69a-47ad-be12-d0912da7ee66.jpg)

Страница с подписчиками

![2022-12-11_16-19-44](https://user-images.githubusercontent.com/15989675/206906063-94b7bfbc-e142-495d-a1dd-eaa4fb47716e.jpg)

Страница с подписками

![2022-12-11_16-09-55](https://user-images.githubusercontent.com/15989675/206906031-75b605de-0004-4904-b7ed-dd7dd2792818.jpg)

Поиск пользователей и постов по тегам

![2022-12-11_16-10-36](https://user-images.githubusercontent.com/15989675/206905966-ab7f90a1-f2ad-4189-b85c-9910fb08f8e7.jpg)

QR-код страницы пользователя

![2022-06-11_02-20-55](https://user-images.githubusercontent.com/15989675/173162398-5de577e3-aef2-413e-9548-57ea1e6b943f.jpg)

Страница авторизации

![2022-12-11_16-08-54](https://user-images.githubusercontent.com/15989675/206905990-7460c7a7-1508-4504-9248-cb779fba9149.jpg)

Страница регистрации

![2022-12-11_16-18-36](https://user-images.githubusercontent.com/15989675/206906008-748b61e3-5c4f-46ac-9309-cc375f70d205.jpg)

Удаление профиля

![2022-12-11_16-12-36](https://user-images.githubusercontent.com/15989675/206906100-d49f3e03-48b1-49a6-9295-ec584d1de4c9.jpg)
