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
-- Data for Name: playlist_music; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.playlist_music (playlist_music_id, music_id, playlist_id) FROM stdin;
1	1	1
2	4	1
3	3	1
4	6	1
5	7	1
6	8	1
7	9	1
8	10	1
9	11	1
10	19	2
11	20	2
12	21	2
13	22	2
14	23	2
15	24	3
16	27	3
17	26	3
18	25	3
19	28	3
20	28	4
21	29	4
22	30	4
23	31	4
24	24	4
25	25	4
26	26	4
27	32	4
28	33	4
29	34	4
30	35	4
31	36	4
32	37	4
33	38	4
34	39	4
\.


--
-- Name: playlist_music_playlist_music_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.playlist_music_playlist_music_id_seq', 34, true);


--
-- PostgreSQL database dump complete
--

