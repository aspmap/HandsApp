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
-- Data for Name: subscriptions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.subscriptions (sub_id, user_id, user_sub_id) FROM stdin;
148	105	64
70	19	26
149	105	66
150	105	67
153	4	2
76	36	18
154	1	75
156	1	19
157	133	37
158	163	125
159	167	37
160	179	178
161	178	179
162	105	179
163	105	178
164	37	167
94	40	4
98	4	26
100	4	9
103	41	40
107	4	41
110	4	64
112	4	66
113	4	67
114	4	68
115	4	69
116	4	70
117	4	71
118	4	73
119	4	74
121	4	76
122	4	75
123	71	4
124	69	4
125	4	91
126	4	90
127	4	89
128	4	88
129	4	86
130	4	85
131	4	92
136	105	92
137	106	37
146	105	37
\.


--
-- Name: subscriptions_sub_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.subscriptions_sub_id_seq', 164, true);


--
-- PostgreSQL database dump complete
--

