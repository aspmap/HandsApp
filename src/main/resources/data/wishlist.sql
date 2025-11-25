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
-- Data for Name: wishlist; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.wishlist (wishlist_id, user_id, photo, link, name_wish, description, price, is_secret, is_done, is_booking, booking_user, created_at) FROM stdin;
2	4	05957d10-ace2-4fb2-a657-df9a3cb518d5.png	https://www.ozon.ru/product/struktury-dannyh-i-algoritmy-v-java-knigi-po-programmirovaniyu-lafore-robert-211439421/	Структуры данных и алгоритмы в Java / книги по программированию | Лафоре Роберт	Второе издание одной из самых авторитетных книг по программированию посвящено использованию структур данных и алгоритмов. Алгоритмы — это основа программирования, определяющая, каким образом разрабатываемое программное обеспечение будет использовать структуры данных.	2159	t	\N	\N	\N	2025-06-24 13:32:58.672614
3	105	604514bd-0405-469c-8e2b-018e234cb321.png	https://www.ozon.ru/product/western-digital-1-tb-vnutrenniy-ssd-disk-wds100t3g0a-633528840/	Western Digital 1 ТБ Внутренний SSD-диск	Благодаря высокому быстродействию и надежности твердотельных накопителей WD Green повседневные задачи на ноутбуках и настольных ПК выполняются значительно быстрее.	6237	f	\N	\N	\N	2025-06-29 23:29:37.449576
4	105	05ba5117-9b64-406c-bc74-a117e16c5e4c.png	https://www.ozon.ru/product/chasy-nastennye-bolshie-besshumnye-alga-time-34-sm-interernye-na-kuhnyu-derevyannye-naturalnyy-dub-1649348169/?oos_search=false	Часы настенные большие бесшумные Alga Time 34 см	Большие интерьерные настенные часы — это идеальный вариант в спальню для взрослых или детскую комнату. Можете повесить на кухню или в гостиную, круглые часики впишутся в любое помещение	2127	f	\N	\N	\N	2025-06-29 23:30:58.340741
5	105	d27ccd25-f1a0-49bf-9235-e3f2f2baf3ef.png	https://www.ozon.ru/product/western-digital-16-tb-vnutrenniy-zhestkiy-disk-wd161kryz-1869456288/	Western Digital 16 ТБ Внутренний жесткий диск	Жесткие диски корпоративного класса WD Gold выдерживают высокие нагрузки.	70882	f	\N	\N	\N	2025-06-29 23:32:12.53047
6	105	e0493079-aab0-44a7-840d-d9d9a1ebfaf7.png	https://www.ozon.ru/product/vinilovaya-plastinka-kar-men-karmaniya-coloured-blue-lp-1722084023/	Виниловая пластинка Кар-Мэн "Кармания" Coloured Blue LP	Лонгплей включает главные хиты пластинки — «Сан-Франциско», «Бомбей Буги», «В Багдаде всё спокойно» и «Bad Russians». Помимо регулярного трек-листа, виниловая версия «Кармании» дополнена композицией «Чао, бамбино». 	6774	t	\N	\N	\N	2025-06-29 23:34:05.462969
1	4	6c8f473d-1526-4136-840c-c709f64d2ca0.png	https://www.ozon.ru/product/mango-sushenoe-dattie-1-kg-2h500g-naturalnoe-bez-sahara-976799167/	Манго сушеное Dattie, 1 кг (2х500г) натуральное без сахара	Натуральное сушеное манго без сахара прямиком из солнечного Вьетнама, высушено тонкими дольками без обработки диоксидом серы и без добавления сахара.	807	f	\N	t	105	2025-06-24 13:32:15.636759
\.


--
-- Name: wishlist_wishlist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.wishlist_wishlist_id_seq', 6, true);


--
-- PostgreSQL database dump complete
--

