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
-- Data for Name: likes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.likes (like_id, user_id, post_id) FROM stdin;
3	4	158
5	20	164
8	19	164
14	20	162
15	20	161
16	20	160
18	21	162
19	21	159
20	21	164
21	4	28
22	18	28
32	4	171
33	4	122
34	4	178
35	29	152
36	29	150
38	29	151
39	29	153
41	4	208
43	4	203
47	4	159
48	4	164
52	4	149
55	4	227
56	4	226
57	4	147
58	4	144
59	4	293
63	4	92
65	4	142
66	4	292
67	4	328
68	4	398
69	4	397
70	4	396
71	4	395
72	4	538
73	4	555
74	4	542
75	4	561
76	4	581
77	4	576
80	105	581
81	105	590
82	105	576
83	105	574
84	105	328
85	105	607
86	113	607
87	133	607
88	4	746
89	4	745
90	4	744
91	105	752
92	105	750
93	105	710
\.


--
-- Name: likes_like_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.likes_like_id_seq', 93, true);


--
-- PostgreSQL database dump complete
--

