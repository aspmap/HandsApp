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
-- Data for Name: todo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.todo (todo_id, user_id, todo_text, is_complete, deadline_at) FROM stdin;
1	4	Прогулка по Москве	f	2025-06-24 13:30:00
2	4	Тренировка	t	2025-06-24 13:30:00
4	105	30 июня начало течки	t	2025-06-30 21:39:00
5	105	968 mts	t	2025-06-30 23:36:00
6	105	Добавить сэмпл еще в конце в 666	t	2025-07-01 00:37:00
3	105	К неврологу	t	2025-07-01 20:30:00
7	105	30 июня начало течки	t	2025-08-07 16:24:00
\.


--
-- Name: todo_todo_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.todo_todo_id_seq', 7, true);


--
-- PostgreSQL database dump complete
--

