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
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.messages (message_id, dialog_id, user_id, message_text, message_file, is_read, created_at, extention) FROM stdin;
2	2	4	Привет	\N	f	2023-01-20 21:25:33.509736	\N
4	3	4	Привет	\N	f	2023-01-20 21:35:33.774784	\N
15	7	4	erhgerthtrhrthr	\N	f	2024-08-19 23:52:34.6567	\N
16	2	4	Как дела?	fd2bdb1f-2b0a-4c34-b79f-fe3ab0ff30ad.png	f	2025-06-24 13:58:33.69428	png
5	3	36	Привет	\N	t	2023-01-20 21:35:59.553654	\N
6	3	36	Как дела?	\N	t	2023-01-20 21:36:07.644602	\N
7	3	36	Отлично	\N	t	2023-01-20 21:36:13.728208	\N
8	3	36	Ароо	\N	t	2023-01-20 21:36:21.930621	\N
9	3	36	Ррллл	\N	t	2023-01-20 21:36:25.796564	\N
10	3	36	Рмапр	\N	t	2023-01-20 21:36:30.756668	\N
11	3	36	Рплггн	\N	t	2023-01-20 21:36:35.409952	\N
17	3	4	Hi	\N	f	2025-06-24 14:27:43.192597	\N
18	9	4	Привет	\N	t	2025-06-24 17:09:15.540383	\N
19	10	4	Привет	\N	f	2025-06-24 19:00:13.180053	\N
14	6	4	Привет	\N	t	2023-03-12 17:08:37.157161	\N
21	6	4	Привет	\N	t	2025-06-24 19:00:39.955613	\N
22	6	4	Как дела	\N	t	2025-06-24 20:36:34.074686	\N
23	6	4	Рррр	\N	t	2025-06-24 20:36:39.383614	\N
24	6	4	Ееее	\N	t	2025-06-24 20:36:44.051324	\N
25	6	4	Ииоо	\N	t	2025-06-24 20:36:49.74545	\N
26	6	4	Привет	\N	t	2025-06-25 00:06:58.162966	\N
27	6	4	🤣🤣🤣	\N	t	2025-06-25 00:07:08.150749	\N
28	6	41	Привет	\N	t	2025-06-25 00:07:55.713805	\N
29	6	4		60a14cdb-c3ab-4fb4-b930-e4f77f237c41.png	t	2025-06-25 01:59:36.037489	png
30	6	4	Привет	\N	t	2025-06-25 02:09:19.331169	\N
31	12	4	Привет🐰	\N	f	2025-06-25 03:13:46.988785	\N
32	13	4	Привет🐰	\N	f	2025-06-25 03:24:04.958493	\N
33	15	4	Привет	\N	t	2025-06-25 15:13:37.723991	\N
34	15	67	Зарегистрировалась по новому	\N	t	2025-06-25 15:15:53.534138	\N
35	15	67	Не могу ничего тут пока загрузить	\N	t	2025-06-25 15:16:15.739056	\N
36	15	4	🥳🥳💃💃🎉🎉	\N	t	2025-06-25 15:20:32.932169	\N
37	16	73	Привет!	\N	t	2025-06-26 20:36:47.817271	\N
38	16	4	Привет!😀	\N	t	2025-06-26 20:37:47.34044	\N
40	17	76	Привет	\N	f	2025-06-27 00:11:53.488895	\N
39	16	4	Phantasm	792748b5-20b7-4e89-834a-c6880eac6b1c.png	t	2025-06-26 20:49:08.940865	png
41	19	105	Привет	\N	f	2025-06-30 22:57:11.535044	\N
42	19	105		951911a6-bd05-4007-9dfd-feba005c047e.png	f	2025-06-30 22:58:05.115691	png
43	19	105	🤣	\N	f	2025-06-30 22:58:09.900091	\N
1	1	4	Привет	\N	t	2023-01-20 21:24:39.309451	\N
3	1	4	Теперь можно писать сообщения 🕺🥳	\N	t	2023-01-20 21:34:47.91067	\N
20	1	4	Привет	\N	t	2025-06-24 19:00:29.695225	\N
44	1	26	🥳 привет! На связи 🤙🏼 	\N	t	2025-08-04 20:02:54.819398	\N
45	1	4	🥳🥳🥳🥳	\N	f	2025-08-04 21:17:16.129068	\N
46	20	37	Привет😁	\N	t	2025-08-07 16:21:29.49573	\N
47	20	105	Хай	\N	t	2025-08-07 16:23:54.869033	\N
\.


--
-- Name: messages_message_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messages_message_id_seq', 47, true);


--
-- PostgreSQL database dump complete
--

