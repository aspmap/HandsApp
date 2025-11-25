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
-- Data for Name: wishlist_private; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.wishlist_private (private_id, wishlist_id, user_id) FROM stdin;
1	2	9
2	2	64
3	6	64
4	6	67
\.


--
-- Name: wishlist_private_private_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.wishlist_private_private_id_seq', 4, true);


--
-- PostgreSQL database dump complete
--

