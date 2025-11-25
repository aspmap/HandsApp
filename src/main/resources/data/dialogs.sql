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
-- Data for Name: dialogs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.dialogs (dialog_id, name_dialog, img_dialog, created_at, updated_at) FROM stdin;
4	Диалог shurrik77 и nmokretsova	\N	2023-01-20 23:03:55.891686	\N
5	Диалог pavlov15046@gmail.com и nmokretsova	\N	2023-03-02 14:23:38.319759	\N
7	Диалог shurrik77 и student1	\N	2024-08-15 19:40:59.046175	\N
8	Диалог aspmap77@gmail.com и student1	\N	2024-08-23 22:05:08.421034	\N
2	Диалог shurrik77 и sam_wowa@mail.ru	\N	2023-01-20 21:25:26.390154	2025-06-24 13:58:33.69427
3	Диалог shurrik77 и shurrik_music	\N	2023-01-20 21:35:21.567168	2025-06-24 14:27:43.192596
9	Диалог shurrik77 и 117451245895044495399	\N	2025-06-24 17:09:11.17616	2025-06-24 17:09:15.540373
10	Диалог shurrik77 и Natali	\N	2025-06-24 19:00:05.470077	2025-06-24 19:00:13.180052
11	Диалог shurrik77 и handsapp	\N	2025-06-24 22:20:39.827523	\N
6	Диалог shurrik77 и itlife	\N	2023-03-12 17:08:27.5169	2025-06-25 02:09:19.331168
12	Диалог shurrik77 и margarin	\N	2025-06-25 03:13:36.779197	2025-06-25 03:13:46.988784
13	Диалог shurrik77 и margarin28	\N	2025-06-25 03:23:56.765425	2025-06-25 03:24:04.958492
14	Диалог shurrik77 и car-man	\N	2025-06-25 14:08:48.735854	\N
15	Диалог shurrik77 и Pavlusha	\N	2025-06-25 15:13:32.173041	2025-06-25 15:20:32.932168
16	Диалог humor и shurrik77	\N	2025-06-26 20:36:36.257628	2025-06-26 20:49:08.940864
17	Диалог ScooterSingles и margarin	\N	2025-06-27 00:11:41.465091	2025-06-27 00:11:53.488895
18	Диалог shurrik77 и Brooklyn_Bounce	\N	2025-06-28 15:36:42.066186	\N
19	Диалог aspmap и margarin	\N	2025-06-30 22:57:03.71814	2025-06-30 22:58:09.90009
1	Диалог shurrik77 и kittanamm	\N	2023-01-20 21:24:32.416948	2025-08-04 21:17:16.129067
20	Диалог car-man и aspmap	\N	2025-08-07 16:21:13.797424	2025-08-07 16:23:54.869032
21	Диалог aspmap и (G)I-DLE	\N	2025-10-14 00:59:22.264853	\N
\.


--
-- Name: dialogs_dialog_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.dialogs_dialog_id_seq', 21, true);


--
-- PostgreSQL database dump complete
--

