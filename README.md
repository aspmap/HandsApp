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
56. Раздел "Моя музыка"
57. Раздел "Мой TODO-лист"
58. Раздел "Мой вишлист"

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

![error_3](https://github.com/user-attachments/assets/60d61f30-a5bf-4de7-abbc-66c81363ab6f)


> Страница пользователя без подписок

![pod_3](https://github.com/user-attachments/assets/6e940522-86f8-4878-bd7c-dc6fa705cf81)


> Создание и публикация поста

![post_1](https://github.com/user-attachments/assets/acc6bbe7-862f-4f95-a2ac-b59a21ac6a8d)

![post_2](https://github.com/user-attachments/assets/c7abf6de-b0e9-4241-9b3d-90d2faa33225)

![post_3](https://github.com/user-attachments/assets/c6f8e8f5-2c2c-419b-b648-8bf490f73431)



> Страница с постами текущего пользователя

![main](https://github.com/user-attachments/assets/ac5178a0-3d63-4d34-abe5-aa9bf17dc52d)


> Изменение поста

![edit_post](https://github.com/user-attachments/assets/d981dbe2-999e-4d93-aca0-8ec9752ffc79)


> Страница с постами по подписке

![subs](https://github.com/user-attachments/assets/cf0ec229-aaea-4259-8c40-0b1ce2cf4d1e)


> Страница пользователя на которого можно подписаться или отписаться

![profile_2](https://github.com/user-attachments/assets/046c6267-9016-418d-85c3-b232e58c6c76)


> Страница с подписчиками

![pod_2](https://github.com/user-attachments/assets/92da386b-ef9b-4b3c-93b4-e53fe59f8a62)


> Страница с подписками

![pod_1](https://github.com/user-attachments/assets/fda420a8-51c5-44fe-a963-6e24c8226cff)


> Поиск пользователей и постов по тегам

![search](https://github.com/user-attachments/assets/b364f6b5-7cbd-4658-a243-fed1e080deab)


> Страница c результатами поиска связей

![handshakes_search_3](https://github.com/user-attachments/assets/a2e789f7-9890-4311-b6c2-7afcfc6795cc)

![handshakes_search_1](https://github.com/user-attachments/assets/c71bcaf0-ff7b-43c1-8ee8-8eeb0e74cc27)

![handshakes_search_2](https://github.com/user-attachments/assets/754a5c7b-84ae-4513-8514-733d94ba3bf7)


> Редактирование профиля

![profile](https://github.com/user-attachments/assets/d68c1e2f-3a46-4039-97cf-c8aba04ea030)


> Удаление профиля

![delete_profile](https://github.com/user-attachments/assets/8f4a1dde-ce29-4833-b7e8-121aa59c2591)


> QR-код страницы пользователя

![qr](https://github.com/user-attachments/assets/39a0f47e-cf77-42fc-b607-929378427710)


> Обмен сообщениями

![dialogs_2](https://github.com/user-attachments/assets/4d2d0167-a2d2-4123-9c47-e9d7e7560f4c)


> Галочка о прочтении

![chat_01](https://github.com/user-attachments/assets/197e4840-8d15-4709-9501-f988e87b1d96)


> Уведомление о непрочтенных сообщениях и чатах

![dialogs_1](https://github.com/user-attachments/assets/69514848-efe4-4133-ada8-ba5a186c710c)


> Отправка фото в чате

![dialogs_3](https://github.com/user-attachments/assets/8beca380-9241-495b-8a60-58ee6589ad0a)


> Раздел "Мои лайки"

![mylikes](https://github.com/user-attachments/assets/6b3c2058-20e5-49f8-893d-2b4aa7ccb668)


> Закрытый профиль

![close_profile](https://github.com/user-attachments/assets/1661fbf1-b858-4867-b1b9-1b3b32f65ca5)


> Пагинация постов

![pagination](https://github.com/user-attachments/assets/98baae70-5c1d-405b-8e52-17f0f99f4584)


> Карта смайлов

![smiles](https://github.com/user-attachments/assets/ae81362c-1c96-4932-a14f-06fab8868a81)


> TODO-задачи

![todo_1](https://github.com/user-attachments/assets/0745b35e-74e4-4489-8f70-53cbec4bb7de)

![todo_2](https://github.com/user-attachments/assets/5c86f5a8-4968-408d-b614-6ead05eff79c)

![todo_3](https://github.com/user-attachments/assets/a07a7a1c-a614-46f1-a0f8-04cf9da76b1a)


> Вишлисты

![wishlist_1](https://github.com/user-attachments/assets/fab3a1bd-a323-4cce-9e99-ef5fdd0dde02)

![wishlist_2](https://github.com/user-attachments/assets/989317ef-ae37-42a9-9da5-dc96d23c072d)

![wishlist_3](https://github.com/user-attachments/assets/75c9fad3-8f48-41e9-ae81-169ad10f6f4d)

![wishlist_4](https://github.com/user-attachments/assets/2a8f6764-eedb-42c1-a53e-89a7e2e91dff)

![wishlist_5](https://github.com/user-attachments/assets/7ff47e44-a016-43a2-ad7f-b7a96fc75f16)

![wishlist_6](https://github.com/user-attachments/assets/650a8ef7-ba52-479a-9808-0db89c734a12)


> Музыка

![music_1](https://github.com/user-attachments/assets/9d4170b2-d4c5-4969-8076-dadea2c1c11f)

![music_2](https://github.com/user-attachments/assets/f1a1dd49-be81-4639-a997-22d9e5a6f2e5)

![music_3](https://github.com/user-attachments/assets/d0bb8f2c-91bb-439d-ae4a-6407940fedd3)

![music_4](https://github.com/user-attachments/assets/4f4b23dc-8583-4663-815f-fecf19ad7ce5)

![music_5](https://github.com/user-attachments/assets/a3efeab9-b7aa-4db0-8ba6-4e9958f84b7c)


> Информационные страницы

![error_1](https://github.com/user-attachments/assets/be64a313-5491-4cb3-8c76-d284bdb9ab0d)

![error_2](https://github.com/user-attachments/assets/40e1f460-8832-476c-85ec-ee5231a42390)

