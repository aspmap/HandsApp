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
-- Data for Name: user_dialog; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_dialog (ud_id, user_id, dialog_id) FROM stdin;
1	4	1
2	26	1
3	29	2
4	4	2
5	4	3
6	36	3
7	4	4
8	30	4
9	30	5
11	4	6
12	41	6
14	4	7
17	4	9
19	9	10
20	4	10
21	40	11
22	4	11
24	4	12
25	4	13
26	64	13
27	37	14
28	4	14
29	67	15
30	4	15
31	73	16
32	4	16
33	64	17
34	76	17
35	4	18
36	92	18
37	105	19
38	64	19
39	37	20
40	105	20
41	105	21
42	70	21
\.


--
-- Name: user_dialog_ud_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_dialog_ud_id_seq', 42, true);


--
-- PostgreSQL database dump complete
--

