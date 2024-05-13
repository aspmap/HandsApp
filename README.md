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
3. Открываем проект в IntelliJ IDEA и в переменных окружения указываем имя и пароль от вашей БД, имя БД, параметры Oauth2.0 (переменные окружения указаны ниже)
4. Настраиваем Tomcat
5. Запускаем приложение

:computer: **Переменные окружения:**

Oauth2.0 (application.properties)

spring.security.oauth2.client.registration.google.client-id = ${CLIENT_ID}

spring.security.oauth2.client.registration.google.client-secret = ${CLIENT_SECRET}

spring.security.oauth2.client.registration.google.scope = ${SCOPE}


DB (application.properties)

db.postgres.url = ${DB_URL}

db.postgres.login = ${DB_LOGIN}

db.postgres.password = ${DB_PASSWORD}



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

> Выборка постов по ключевому слову

GET http://handsapp.online/api/post?search=москва


> Выборка всех постов

GET http://handsapp.online/api/post


> Поиск поста по ID

GET http://handsapp.online/api/post/284


> Создание поста

POST http://handsapp.online/api/post/

> Пример запроса

```json
{

    "photo": "gfpdohum.png",
    
    "extFile": "png",
    
    "content": "Test API create",
    
    "createdAt": "2022-06-12 02:18:58"
}
```

> Редактирование поста

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

> Удаление поста

DELETE http://handsapp.online/api/post/295


:abcd: **CI/CD:**
Настроено автоматическое обновление, начиная с коммита и заканчивая деплоем приложения на хостинге с помощью Jenkins. 


:iphone: **Внешний вид приложения на текущий момент:**

> Страница авторизации

![2024-05-07_20-14-31](https://github.com/aspmap/HandsApp/assets/145023708/a17e3e6e-8621-4000-8fb8-e20a2876ac52)


> Страница регистрации

![2024-05-07_20-14-47](https://github.com/aspmap/HandsApp/assets/145023708/859609a3-05a3-4af7-9d7e-da3f4dbc7633)


> Страница пользователя без подписок

![2024-05-07_19-57-47](https://github.com/aspmap/HandsApp/assets/145023708/ac800b5e-7b6b-4004-9883-8061ed89b966)


> Создание и публикация поста

![2024-05-07_20-00-51](https://github.com/aspmap/HandsApp/assets/145023708/fcae4f76-6351-473e-baec-17564b3417d4)

![2024-05-07_20-01-43](https://github.com/aspmap/HandsApp/assets/145023708/cf4f0c20-f2d2-4ab2-96be-e0dbb087013f)

![2024-05-07_20-02-12](https://github.com/aspmap/HandsApp/assets/145023708/8a771034-ef39-4aae-b9b0-a17611e4f1db)

![2024-05-07_20-02-28](https://github.com/aspmap/HandsApp/assets/145023708/3460e893-ab1b-4e73-bb36-6ef2550614a2)


> Страница с постами текущего пользователя

![2024-05-07_20-05-55](https://github.com/aspmap/HandsApp/assets/145023708/d5766c66-d9b3-430c-ae2e-a70e7f1f7671)


> Изменение поста

![2024-05-07_20-06-56](https://github.com/aspmap/HandsApp/assets/145023708/13d988d7-0423-407e-afd3-2007dd54c100)


> Страница с постами по подписке

![2024-05-07_20-08-32](https://github.com/aspmap/HandsApp/assets/145023708/7f39a04b-979c-4c2f-9ac6-955ad003504c)


> Страница пользователя на которого можно подписаться или отписаться

![2024-05-07_20-09-29](https://github.com/aspmap/HandsApp/assets/145023708/43220bfc-8c77-4a24-8990-0c74b6ad8374)


> Страница с подписчиками

![2024-05-07_20-11-26](https://github.com/aspmap/HandsApp/assets/145023708/2a0b199e-d111-4faa-9c20-bd0f786b32e3)


> Страница с подписками

![2024-05-07_20-10-46](https://github.com/aspmap/HandsApp/assets/145023708/f4cbf40a-7641-4c65-b242-bc5db5127f8d)


> Поиск пользователей и постов по тегам

![2024-05-07_20-12-22](https://github.com/aspmap/HandsApp/assets/145023708/f1b79c6a-060a-4dab-abe7-7cd21aa95215)


> Страница c результатами поиска связей

![2024-05-07_19-54-36](https://github.com/aspmap/HandsApp/assets/145023708/d4e173f4-1a6e-4148-9a8c-000ce8305699)


> Редактирование профиля

![2024-05-07_19-59-19](https://github.com/aspmap/HandsApp/assets/145023708/c79413c1-6c42-4a70-acf8-842f5f2ae020)


> Удаление профиля


![2024-05-07_20-15-17](https://github.com/aspmap/HandsApp/assets/145023708/c9eccab4-9a3b-4404-80e9-1cd9074131fe)


> QR-код страницы пользователя

![2024-05-07_20-13-07](https://github.com/aspmap/HandsApp/assets/145023708/8fa55c30-fa5a-4d63-b1e3-3683bf07f49d)


> Обмен сообщениями

![2024-05-07_20-28-04](https://github.com/aspmap/HandsApp/assets/145023708/0e633c08-5336-4851-a624-0d07976bd769)

> Раздел "Мои лайки"

![2024-05-07_20-30-54](https://github.com/aspmap/HandsApp/assets/145023708/0251ac5a-587a-4ea7-a608-771cf79f78e0)




