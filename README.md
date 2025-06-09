# HandsApp
Социальная сеть для размещения фото, видео (веб-версия)

Адрес в сети интернет: http://handsapp.top (временно отключен)

**Старт проекта:** 15 марта 2022 г.

**Выпуск первой стабильной версии:** 08 ноября 2022 г.

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
1. Клонируем проект: git clone https://github.com/aspmap/HandsApp.git
2. Создаем БД в PostgreSQL и запускаем скрипт HandsAppDB.sql для создания всех необходимых таблиц и их первичного наполнения
3. Открываем проект в IntelliJ IDEA и в переменных окружения указываем имя и пароль от вашей БД, имя БД, параметры Oauth2.0 (переменные окружения указаны ниже)
4. Настраиваем Tomcat
5. Указываем переменные окружения
6. Запускаем приложение

:computer: **Переменные окружения:**

Oauth2.0 (application.properties)

spring.security.oauth2.client.registration.google.client-id = ${CLIENT_ID}

spring.security.oauth2.client.registration.google.client-secret = ${CLIENT_SECRET}

spring.security.oauth2.client.registration.google.scope = ${SCOPE}


DB (application.properties)

db.postgres.url = ${DB_URL}

db.postgres.login = ${DB_LOGIN}

db.postgres.password = ${DB_PASSWORD}

S3 (Yandex) (application.properties)

application.bucket.name = ${BUCKET_NAME_S3}

cloud.aws.credentials.access-key = ${ACCESS_KEY_S3}

cloud.aws.credentials.secret-key = ${SECRET_KEY_S3}

cloud.aws.region.static = ${REGION_S3}



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
32. Поиск связей, основанный на теории шести рукопожатий (применение алгоритма поиска в ширину). Осуществляется поиск пользователя и выводится наглядная визуализация связей на странице
33. Авторизация через Google
34. Работа приложения по протоколу https
35. Создание поста с использованием хранилища S3 (Yandex)
36. Поиск пользователей по E-Mail
37. Сообщение о неверно введенных логине и пароле
38. Проверка длины пароля при регистрации
39. Заголовки страниц
40. Сообщение о превышение допустимого размера файла при загрузке
41. Несколько простых пробных Unit-тестов
42. Пробная собственная библиотека CheckObjectsForNull
43. Закрытый и активный профиль
44. Время последнего и предыдущего посещения
45. Получение JWT-токена, авторизация и аутентификация пользователя с помощью полученного токена, получение постов текущего пользователя через HandsApp API (https://github.com/aspmap/HandsApp_API) с помощью полученного токена
46. Пагинация постов
47. Бесконечная прокрутка постов
48. Представление (View) в БД для списка рекомендаций 
49. Галочка о прочтении в чате, количество непрочитанных сообщений и чатов
50. Карта смайлов
51. Формирование архива пользователя (многопоточность)
52. Отправка фото в чате
53. Динамическое обновление счетчика непрочитанных чатов в верхнем меню
54. Сортировка диалогов по дате последнего сообщения
55. Дата последнего сообщения в списке диалогов

:abcd: **REST-API:**

Используется Basic-Auth

> Выборка постов по ключевому слову

GET http://handsapp.top/api/post?search=москва


> Выборка всех постов

GET http://handsapp.top/api/post


> Поиск поста по ID

GET http://handsapp.top/api/post/284


> Создание поста

POST http://handsapp.top/api/post/

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

PUT http://handsapp.top/api/post/294

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

DELETE http://handsapp.top/api/post/295


:abcd: **CI/CD:**
Настроено автоматическое обновление, начиная с коммита и заканчивая деплоем приложения на хостинге с помощью Jenkins. 


:iphone: **Внешний вид приложения на текущий момент:**

> Страница авторизации

![login](https://github.com/user-attachments/assets/e58f27a7-f437-4003-b78a-37d799b87b65)


> Страница регистрации

![register](https://github.com/user-attachments/assets/9029b4bc-5931-4f2a-ba93-ddd5f7762bf8)


> Страница пользователя без подписок

![without_sub](https://github.com/user-attachments/assets/c3597719-be1d-4167-9162-1adaa7c26c1f)



> Создание и публикация поста

![create_post_1](https://github.com/user-attachments/assets/7bdcfdf3-71e2-4ceb-8066-865b4ece9c23)

![create_post_2](https://github.com/user-attachments/assets/b63ddff5-1dd1-405d-bf89-ac72b5120d2b)

![create_post_3](https://github.com/user-attachments/assets/0814cf73-40ed-419a-93af-92a7fc3f266c)

![create_post_4](https://github.com/user-attachments/assets/53de6770-c56e-4da0-b659-125a1bb505c8)

![create_post_5](https://github.com/user-attachments/assets/794c8b3a-0cf4-4666-8f50-ede4d6451b6c)


> Страница с постами текущего пользователя

![my_posts](https://github.com/user-attachments/assets/ed533b1f-ed99-4e37-bdeb-dcfb675d878b)



> Изменение поста

![edit_post](https://github.com/user-attachments/assets/d981dbe2-999e-4d93-aca0-8ec9752ffc79)



> Страница с постами по подписке

![subs](https://github.com/user-attachments/assets/cf0ec229-aaea-4259-8c40-0b1ce2cf4d1e)



> Страница пользователя на которого можно подписаться или отписаться

![sub_1](https://github.com/user-attachments/assets/18dcd297-a563-47ea-aeb4-aca62e7c672a)

![sub_2](https://github.com/user-attachments/assets/37ae082d-b361-4118-97ea-05179121ca6f)



> Страница с подписчиками

![subs_page_1](https://github.com/user-attachments/assets/a9497b4c-6e6f-4b6a-9303-af08051f7775)



> Страница с подписками

![subs_page_2](https://github.com/user-attachments/assets/7004c859-7a31-4a01-b324-eb3b4ea1605d)



> Поиск пользователей и постов по тегам

![search_tags](https://github.com/user-attachments/assets/445d8cb6-e553-496a-8ff1-89bb462c40cf)

![search_users](https://github.com/user-attachments/assets/f563ecec-914c-4426-82cb-4b691e532838)




> Страница c результатами поиска связей

![handshakes](https://github.com/user-attachments/assets/d1abc6dc-7f8e-489e-8060-d499e1409310)



> Редактирование профиля

![edit_profile_1](https://github.com/user-attachments/assets/76752882-4160-44bd-8bca-43c697169089)

![edit_profile_2](https://github.com/user-attachments/assets/c56f2771-72de-41d5-bfdf-42f11765be53)




> Удаление профиля

![delete_profile_1](https://github.com/user-attachments/assets/d3ecd69f-536b-406f-9fb2-15cea81cbbb0)

![delete_profile_2](https://github.com/user-attachments/assets/5bd50894-ad67-4ec9-b7e4-c9d1e81d4f72)



> QR-код страницы пользователя

![qr_code](https://github.com/user-attachments/assets/7bd9624c-54bd-4467-8d14-f542631ad93c)



> Обмен сообщениями

![chat](https://github.com/user-attachments/assets/4eb584eb-8376-4511-9285-517b075ec167)



> Галочка о прочтении

![chat_01](https://github.com/user-attachments/assets/197e4840-8d15-4709-9501-f988e87b1d96)




> Уведомление о непрочтенных сообщениях и чатах

![chat_02](https://github.com/user-attachments/assets/2eeb241f-60ae-443e-b0a0-00c6af978e54)


> Отправка фото в чате

![send_photo](https://github.com/user-attachments/assets/c82dfaec-aaef-4612-b8d1-a8c735710159)



> Раздел "Мои лайки"

![my_likes](https://github.com/user-attachments/assets/8f6ab092-9a08-4e0c-8a7d-546bce7ad582)


> Закрытый профиль

![close_profile](https://github.com/user-attachments/assets/555f51b1-a712-4bc3-b693-188d9371cd8e)




> Пагинация постов

![pagination](https://github.com/user-attachments/assets/5b38ea8d-2afb-4af1-aa87-fc35e3094234)


> Карта смайлов

![smiles](https://github.com/user-attachments/assets/ae81362c-1c96-4932-a14f-06fab8868a81)



> Информационные страницы

![info_1](https://github.com/user-attachments/assets/10146796-92f4-4667-90e3-2e5c46d7d17a)

![info_2](https://github.com/user-attachments/assets/0bd93fc5-90e5-4241-9597-96b0056e72db)

![info_3](https://github.com/user-attachments/assets/3858d79e-efe8-480c-9b3b-5eecaafb724d)

![info_4](https://github.com/user-attachments/assets/f19cf7f0-8d7d-489e-a3fe-2e7ae83b1193)

![info_5](https://github.com/user-attachments/assets/29ca0bea-8d40-404e-923e-a9d5d43c64aa)
