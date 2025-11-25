--
-- PostgreSQL database dump
--

-- Dumped from database version 14.6 (Debian 14.6-1.pgdg110+1)
-- Dumped by pg_dump version 14.6 (Debian 14.6-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: bugs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bugs (bug_id, user_id, username, bug_text, created_at) FROM stdin;
11	4	\N	Ошибок пока вроде нет. Всё работает исправно	2025-06-26 16:53:14.693812
12	105	\N	Как у Рината получилось 5 одинаковых подписок? Если нажал несколько раз пока тупит сайт	2025-06-30 08:36:43.570803
13	105	\N	Не добавляется в плейлист песня NonUniqueResultException: query did not return a unique result: 2	2025-06-30 08:37:03.214506
14	105	\N	Вишлист 107801428559539759311 - исправить	2025-06-30 08:37:16.829632
15	4	\N	Не работает бронь желания	2025-06-30 12:35:01.044157
16	98	\N	Для гугл учетки текст в посте подписчика выводится номер а не email	2025-06-30 14:41:57.075978
17	105	\N	Объединить отображение постов post и post_subscribe, можно глюк с иконкой сейчас словить	2025-06-30 21:26:46.607788
18	105	\N	Или просто подправить отображение иконки, имяже правильно отображается	2025-06-30 21:28:19.646328
19	105	\N	Все таки сделать активными картинку и логин на своих страницах	2025-06-30 21:47:52.205784
20	26	\N	Будет ли кнопочка «перевод текста/комментариев» к посту? 	2025-08-04 20:10:17.066458
21	26	\N	Видео не выкладываются :(	2025-08-04 20:20:34.770285
\.


--
-- Name: bugs_bug_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bugs_bug_id_seq', 21, true);


--
-- PostgreSQL database dump complete
--

