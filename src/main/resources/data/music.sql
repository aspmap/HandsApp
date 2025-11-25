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
-- Data for Name: music; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.music (music_id, user_id, file_name, project_name, song_name, song_year) FROM stdin;
1	4	a5e5122e-24f5-432b-ade9-e68e8ed31eab.mp3	Кар-Мэн	Жми на газ	2025
3	4	e8602c06-e624-4ad8-9a9c-95300047da65.mp3	Кар-Мэн	Танец теней	2025
4	4	be052791-f57b-4c5a-a42b-28e9a68ddeaf.mp3	Scooter x Harris & Ford with Shibui	Gimme that noise	2025
6	4	27b5c784-4003-480f-8ace-3fc5c0b36d4c.mp3	666	Rhythm takes control [Mike Bound remix Altermuun version]	2025
7	4	b1f4688e-931c-47e7-9103-8be139e614aa.mp3	Размер Project feat. Sound-X-Monster	Rockstar	2025
8	4	8e0e96e6-5935-4fe4-aade-f393443fdb1e.mp3	Кар-Мэн	Али-Баба [ремикс 2025]	2025
9	4	8194cc46-dff3-4abb-a4ce-374ec31e47df.mp3	Кира Тимралеева, Мусим Ашуров, MCКАРАМЕЛЬКА	Скам	2024
10	4	20dbfcae-cc10-48e0-b647-d576cfcd20f1.mp3	Brooklyn Bounce	Bring it back [DJ Dean & DJ T.H. remix]	2025
11	4	d1e86799-2440-432d-8122-a9a8d77ab59d.mp3	Scooter	Riot [Altermuun club mix]	2025
12	4	532f8188-ae6c-4252-8bb7-12122657e87a.mp3	Scooter	Let's do it again	2024
13	4	f97fc80b-6818-4a02-a074-03d901bd1b67.mp3	Scooter x Harris & Ford	Rave & shout [special extended mix]	2023
14	4	8492b903-0368-47bb-a281-64517a1f05fe.mp3	Scooter x Harris & Ford	Techno is back [special extended mix]	2023
15	4	af7a5da0-a406-42d5-8091-c9f7bdb01f71.mp3	Scooter	Posse (I need you on the floor) [club mix]	2001
16	4	719e753c-ea48-475f-b2ea-180d86fc7fbf.mp3	Scooter	Posse (I need you on the floor) [Tee Bee mix]	2001
17	4	9f896e1c-6f1d-446b-9096-ded3e13760ab.mp3	Scooter	Call me mañana [heavy horses extended]	1999
18	4	6a9ccad0-5764-4899-9ee1-9599180ef6db.mp3	Scooter	Imaginary battle	2007
19	4	2737b1f7-a442-41ee-b108-89e44f1e7b28.mp3	Scooter	Let's do it again [Altermuun extended mix]	2024
20	4	5362b6f2-e922-4d32-9be4-75a0dfd4d6d7.mp3	(G)I-DLE	I want that	2023
21	4	f80da69d-faf9-490f-922e-c3ecb8e49b31.mp3	Scooter	Children of the rave	2024
22	4	3863503d-241e-4b43-b822-e782f615c3a6.mp3	Jax Jones and Zoe Wees	Never be lonely [Scooter extended remix]	2024
23	4	d9dc73db-87e9-4ad2-a7ea-9792b82d08a9.mp3	DJ Aligator x Darwich	I have a dollar [extended version]	2024
24	105	f63e645c-bd07-4cd4-9238-a5c98589468d.mp3	Кар-Мэн	Жми на газ	2025
25	105	4141dac6-1d04-4516-be52-c9cfc7210d9d.mp3	Кар-Мэн	Али-Баба [ремикс 2025]	2025
26	105	5cf5fe12-cb78-4fba-bf6a-2b06719cba89.mp3	Кар-Мэн	Танец теней	2025
27	105	795db099-0e00-4b50-989a-2dd062ee466d.mp3	Кар-Мэн	Moscow City	2022
28	105	c80de719-2c64-49c6-86ef-a9da5c1fb411.mp3	Кар-Мэн	Я и ты (2024)	2024
29	105	047d7468-e711-4a02-ac24-68cdcbe7b926.mp3	666	Rhythm takes control [Mike Bound remix Altermuun version]	2025
30	105	d9ad08ac-4dfb-4c9e-9fce-315289f78314.mp3	Scooter x Harris & Ford with Shibui	Gimme that noise	2025
31	105	3585689e-f627-4c1e-9830-c8ba3c413c6d.mp3	Размер Project feat. Sound-X-Monster	Rockstar	2025
32	105	7c26cbbe-f45a-49de-9404-87dc093896ac.mp3	Midnight Danger feat. Max Cruise	Fatal attraction [alternate mix]	2021
33	105	7c8b384f-bc38-415d-8955-0cca292ce92c.mp3	Кира Тимралеева, Мусим Ашуров, MCКАРАМЕЛЬКА	Скам	2024
34	105	94fe3103-dcc1-467f-93c9-6fd1c1911d13.mp3	Brooklyn Bounce	Bring it back [DJ Dean & DJ T.H. remix]	2025
35	105	6d84d855-fba9-49b9-b68a-9a9ee6457546.mp3	XS Project	The time is now	2024
36	105	e56f86a2-651b-44e2-9b54-4aefba2f4e28.mp3	Scooter	Riot [Altermuun club mix]	2015
37	105	6a4d764e-7813-47b2-9825-ecd32ceeb7f5.mp3	Кар-Мэн	Эй, ухнем vs. Чио-Чио-сан	2025
38	105	a8b5ad6c-4c8f-4990-853b-29c98a5a61fc.mp3	Woody van Eyden & Brooklyn Bounce	The at-mos-phere [extended mix]	2025
39	105	2a256dcc-b0f9-49e6-8f25-ffe31e6786f8.mp3	Betsy, Мария Янковская, Boris Redwall	Sigma H.P. [Techno remix]	2024
\.


--
-- Name: music_music_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.music_music_id_seq', 39, true);


--
-- PostgreSQL database dump complete
--

