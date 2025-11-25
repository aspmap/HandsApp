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
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, username, password, surname, firstname, photo, info, www, email, phone, sex, created_at, is_active, is_google, last_visit, is_closed, is_hidden, previous_visit) FROM stdin;
25	Vibe_girl17	$2a$10$nOB873NfJfvm6ABSOICe3eatxwf8pllG8yRfp7aUntTbyK55L30OC	Vibe	Polina	egjgyqsu.jpg	\N	\N	\N	\N	FEMALE	2022-05-30 00:07:32.855687	t	f	2025-07-16 16:26:40.380875	f	f	\N
85	Kraimbrery	$2a$10$gxrzeGmLqnq/ZH8pIFd09O93NFrQjXw1Y5pNY.MUjetgYViYSMDEu			851738e8-2035-46d8-ad4e-b83f140c29c8.jpg	\N	\N			MALE	2025-06-27 11:03:10.159325	t	f	2025-06-27 11:03:23.753947	f	f	\N
20	kuzminki_msk	$2a$10$/usDopjYZpbhb3ULt20sQ.1jWkYAA25jQ01c2gxJ9beyT.ltlenHW	Кузьминки	Москва	vyufyhyv.jpg	\N	\N	\N	\N	MALE	2022-05-28 13:20:33.821634	t	f	2025-07-16 16:26:59.670236	f	f	\N
103	104977204604406410364	$2a$10$RsgEH2aEmhiUQUcYZ454yeqR84GJFxxBnIxo4PV2SB0yJ9b9UHNri	Павлов	Александр	https://lh3.googleusercontent.com/a/ACg8ocJ4QfHvZr3TToiTTtubbto0RxkBSPV6sb0n9ay0BlCWr2AjCRKoKQ=s96-c	\N	\N	pavlov15046@gmail.com	\N	\N	2025-06-29 17:07:31.938613	t	t	2025-06-29 17:47:42.168573	f	f	2025-06-29 17:08:28.209287
89	Squid_Game	$2a$10$cCluE/O8cvBAg/TRRUsBUuHBJ4hSc5cAN0O6P4AOpGsHr54xKAnYq			e0fc15fc-edfb-4a89-a6fb-a84ece5d5a0a.jpg	\N	\N			MALE	2025-06-27 14:15:18.010237	t	f	2025-06-27 14:15:39.040458	f	f	\N
70	(G)I-DLE	$2a$10$Q.HBCEs5Qzgp4lxrmJ7nBOE4twl8FLb6lHNsX0uYx.d4.qkWiEDGG			c53a2e2a-a1db-40bc-85fd-8241540dcc9c.jpg	\N	\N			MALE	2025-06-25 22:03:28.118081	t	f	2025-06-25 22:28:16.367981	f	f	2025-06-25 22:04:15.352125
119	116138651558648030601	$2a$10$RkeycJTDrgw.6gBMIeAW7eaEQ7UEtMq9lwksBogGMxVg8sKKucCye	Kozlov	Aleksey	https://lh3.googleusercontent.com/a/ACg8ocKljrzhajl2miJp_9e0171Ssvw-Een7lNjKe3LBHo2VNJpbLA=s96-c	\N	\N	marseille9293@gmail.com	\N	\N	2025-07-02 02:37:58.525007	t	t	2025-07-02 02:37:58.590259	f	f	\N
91	RoboCop	$2a$10$iKQX.z564sbhuxgikrQ0SOfbSucpiiAqhoecpLaSpb/8RX/V9ynvu			9b962a5e-53c8-49f8-94d8-4b0530a053e1.jpg	\N	\N			MALE	2025-06-27 14:26:44.374871	t	f	2025-06-27 14:26:58.739337	f	f	\N
18	Rammstein	$2a$10$Dmg/.h57ypmuqYMWo2vhneMRylOlBjLVa2nqU6VRd1vBlXa/0iL4i	\N	\N	dpjbrmxi.jpg	\N	\N	\N	\N	MALE	2022-05-28 04:57:20.830306	t	f	2025-07-16 16:27:11.712429	f	f	\N
64	margarin	$2a$10$n/L1K3Cwp117v5bsM9xu/.JCKb3sVVYVlERaMrcB.A2O5XxLunu2y	Маргаринова	Маргарита	\N	\N	\N	gonya201109@gmail.com	89638159308	MALE	2025-06-25 03:22:07.560069	t	f	2025-06-25 10:13:41.808107	f	f	2025-06-25 03:22:24.291174
22	moscowcity	$2a$10$/tABVRdUalV2tx5uqJVUDeMfkVPyD8g6qeeGPpmhBJzNQFhPU/mjS	Moscow	City	kcztdkqd.jpg	\N	\N	\N	\N	MALE	2022-05-28 14:02:26.793107	t	f	2025-07-16 16:28:19.048364	f	f	\N
19	metro_msk	$2a$10$41vKLydpURkkAJr056KlperGc1R6nwskQyfb2YSqMR2HTy/8IbLlq	Метро	Москвы	byrlmnxb.jpg	\N	\N	\N	\N	MALE	2022-05-28 13:01:45.464702	t	f	2025-07-16 16:28:48.675274	f	f	\N
97	107801428559539759311	$2a$10$w.cmnpNf7igu/Dz1Ou.SUu3H3S.p3FAJBrqYayI2TQ0ZncNoU9uR2	Ницкая	Вероника	https://lh3.googleusercontent.com/a/ACg8ocJ5Nb7bwCNWGGngb8Po9GGWDQ5F70kVJSONyocOfGKFQwQQ3i0=s96-c	\N	\N	nikatop15@gmail.com	\N	\N	2025-06-28 23:28:28.704693	t	t	2025-07-20 22:00:24.346357	f	f	2025-07-05 17:40:22.599306
74	Sound-X-Monster	$2a$10$S0xxy8DlQbc6vV4n7YtMFuFLOSYZ5dA5uPsa3qDvaXE6FZWIpa7S.			654fda01-d29f-4041-9953-fcb1a15f48c2.jpg	\N	\N			MALE	2025-06-26 12:18:36.552349	t	f	2025-06-26 13:01:30.258207	f	f	2025-06-26 12:18:50.977901
66	novograd_pavlino	$2a$10$taWbald8G9Kfov6.djZX6eBIQ.mZrUUKxJ0U3HDaO/V13qvuqlzci			9a3b7425-feaa-4f53-ab38-966a42a16d99.jpg	\N	\N			MALE	2025-06-25 14:36:30.307058	t	f	2025-06-25 14:58:40.786913	f	f	2025-06-25 14:44:32.299091
9	Natali	$2a$10$5wrDPYg8Slz013E0dphCPOKGY0vaz0VLYCMsSFbQZLb51OiO9PXPe	Павлова	Наталья	wpotmulm.jpg	\N	\N	\N	\N	MALE	2022-05-21 21:48:49.277957	t	f	2025-06-25 15:05:24.955282	f	f	2025-06-25 14:59:31.288458
68	cinema90	$2a$10$L46hDqjBACO0yLzrQMEOdeqtaDQQ6PB/gG3e1YFFT.1Iswl/u1nUe			9099a587-8272-484b-9778-e46a5af2887a.jpg	\N	\N			MALE	2025-06-25 19:08:20.300433	t	f	2025-06-25 19:08:41.437781	f	f	\N
172	109768989478898240953	$2a$10$ZRPSBLYh2Yql0hh6HKhKQ.rJj75cpxnIwsCvEXPaGDWmsvpys9VBy	Shiyafetdinov	Timur	https://lh3.googleusercontent.com/a/ACg8ocLROQUmK7lb_r-FUQzD1JjcVLSlfrGpLPsjx04Lw-sY6c89tuAIdg=s96-c	\N	\N	tshiya1976@gmail.com	\N	\N	2025-07-19 10:12:22.320854	t	t	2025-07-19 10:12:22.379496	f	f	\N
115	105876570462594031881	$2a$10$k.WoD.91q8nOfYAN5IZ/Wud/URopU81rqQ/APRKiET2MdK9OYhA0e	\N	StarMax	https://lh3.googleusercontent.com/a/ACg8ocLJ3YSpPojFL_L5bXYWB_qoyKBPoXRZA9Frs1PB5VUrv3X3RT_a=s96-c	\N	\N	medmaxlion@gmail.com	\N	\N	2025-07-01 15:58:20.289286	t	t	2025-07-01 15:58:20.326212	f	f	\N
133	101455511315946537297	$2a$10$9I3hveykSM5fydUliK6CA.v2k6yKqHK4.fXOONg/NcI0aGdZCYJ/W	Kazakov	Vladimir	https://lh3.googleusercontent.com/a/ACg8ocKI_FB5lvaOHga5O_vhM3FZofAkOcJuPVKUrTiBoxzgK9oISg=s96-c	\N	\N	vladimir1989kazakov@gmail.com	\N	\N	2025-07-06 21:35:50.410698	t	t	2025-07-06 21:35:50.451239	f	f	\N
76	ScooterSingles	$2a$10$bEGhDNR9vSqCsbgotohozejd1oQspUz8GwETuZjomszievkLhGF/K			b0cf4748-f9c0-4e77-a18b-fcd74705c934.jpg	\N	\N			MALE	2025-06-26 13:58:40.112455	t	f	2025-07-01 18:12:58.650713	f	f	2025-06-26 23:39:17.231587
105	aspmap	$2a$10$s1yDBfzOI3qz57LbnCpXPuYNd3CmuoOFiehZPlRTTb09DLil2MMcm			7ddd0bfe-0f2e-4e81-b35b-cc1475dce7c6.jpg	\N	\N			MALE	2025-06-29 23:04:10.765163	t	f	2025-10-19 20:58:53.267674	f	f	2025-10-14 00:58:42.581102
113	117064527940570497216	$2a$10$9YnUTCLtCf0yQuM6eAgK4OMbVKruxjxOFeEX4IvAu8rdGL6njBcE2	Кудинов	Алексей	https://lh3.googleusercontent.com/a/ACg8ocL0fowpdj2K4wBLvd71H_UboMLJo9Xf2pOsWBsxjvNJ2s1Y2i0=s96-c	\N	\N	alexeykud76@gmail.com	\N	\N	2025-07-01 12:16:10.02454	t	t	2025-07-01 18:31:34.447823	f	f	2025-07-01 12:16:10.134683
109	it_humor	$2a$10$kd0Cgdizz6QtQf8Kw8vtseqxkcKqAIYtGUl4fSdo6k888xj8iCqvO			a4f75466-b06a-4fae-97d1-a94f28189a2e.jpg	\N	\N			MALE	2025-06-30 22:50:35.303847	t	f	2025-06-30 22:50:50.738854	f	f	\N
2	pavlov89312	$2a$10$EVVeWkpD9bZAXPm3w/3rJusArgMRouc0mSAKsDySkAgLNH0XVTwfu	Иванов	Иван	jlxwadlu.jpg	\N	\N	\N	\N	MALE	2022-05-21 20:53:54.520474	t	f	2025-07-04 12:27:57.569229	t	f	2025-06-30 12:36:18.567242
125	105839205921491296161	$2a$10$uutVGphHCmDfroY7D9YER.UaNSSowZJGA9Q4bDKKCM.a2ib4BRSsO	\N	car-man	https://lh3.googleusercontent.com/a/ACg8ocJHkDAxiZWG1kx85VkwuTXG7p_AmmJLjE75zouwzf1Jjk3svYMA=s96-c	\N	\N	ashovikov57@gmail.com	\N	\N	2025-07-04 13:28:49.01464	t	t	2025-07-04 13:28:49.066766	f	f	\N
123	motivator	$2a$10$.WRFAq8ho2OeT6W5SeGstu5BCFI/LkP0.W/7Bd8NNuT2/khDV6G9a			6fe1487f-751c-4154-aec5-9758c1b6e7c2.jpg	\N	\N			MALE	2025-07-03 22:20:42.542496	t	f	2025-07-03 22:21:17.658379	f	f	2025-07-03 22:21:01.074852
178	trolleybus_blg	$2a$10$3TAz1OBBn3/ylAQQ0lq4YOLE0y3WP9qg2n2dircMZJUaJ0UTM3Z4K			9cbaf830-b57a-400f-b927-26f4e7195f4a.jpg	\N	\N			MALE	2025-07-21 03:42:17.32609	t	f	2025-08-05 07:08:25.763302	f	f	2025-08-02 20:56:23.348943
26	kittanamm	$2a$10$hAKHq5ympLw.qvRsGa2NzuaTPLMG3Fhz1i8ucFxl.NngUm2Tuw/kS	\N	\N	pfzjcloq.jpg	\N	\N	\N	\N	FEMALE	2022-05-31 17:53:01.883933	t	f	2025-08-04 20:02:02.588619	f	f	2025-07-16 16:31:48.250435
163	116498097036743630137	$2a$10$94wzbsUZxp1yi5ALevkMAe40EigdyRC2VEsYu4wbxKBXDmKLiQwOG	Алексеев	Яков	https://lh3.googleusercontent.com/a/ACg8ocIszMOGDPI9LedZ4ePVVeiYw7F5_vUcZNjYxYS8of1f__N4foCo=s96-c	\N	\N	yakoff1977a@gmail.com	\N	\N	2025-07-16 06:26:12.344743	t	t	2025-07-16 06:29:19.850853	f	f	2025-07-16 06:28:21.948584
90	Alice	$2a$10$gxcQMNggIwunBVDMNzB5wOCVYE.FgAUE.xADGgikZQPL3BRtEMx5G			4290c044-a0f5-409a-99b5-2d8f4128d3e4.jpg	\N	\N			MALE	2025-06-27 14:24:45.238526	t	f	2025-06-27 14:25:02.969753	f	f	\N
73	humor	$2a$10$N2m4b2.GjuDCAgyN7I6wZ.OPDEKnXuZKt/3jSdgStZXu.jTLs.nZW			e7a756d3-403e-4462-91f2-5ca972adbf73.jpg	\N	\N			MALE	2025-06-26 12:05:54.188931	t	f	2025-07-05 01:34:17.049133	f	f	2025-07-04 23:41:07.220434
167	car-man-theater	$2a$10$GLQpIsKz9cxIw/zKiL7r0.gptrez0jH59yA3nLg1jsw0GVDWUxHDG	ТЕАТР КАР-МЭН ★ THEATER CAR☭MAN (фан-легион)		89148acd-43b7-41f5-8d8f-da45c41fc00f.jpg	Экзотик-поп театр Сергея Лемоха "Кар-Мэн" (солист Роман Лемох)	\N			MALE	2025-07-18 01:14:48.823611	t	f	2025-09-05 13:34:45.868265	f	f	2025-08-29 19:51:32.254677
29	sam_wowa@mail.ru	$2a$10$tUweVOiMFCb0mbfRJWuYx.FCp2c8yVmEOm.GMoITmUSZx7uaV/OVC		Владимир	\N	\N	\N	sam_wowa@mail.ru	89095930397	\N	2022-06-16 21:36:20.550887	t	f	2025-07-16 16:30:34.776687	f	f	\N
21	novogireevo_msk	$2a$10$EnP7PydZfuVxNPO6vWWm/OU0vXwQWtSQAK5LaAdyWRRo6KuTMJpca	Новогиреево	Москва	gorrkwuo.jpg	\N	\N	\N	\N	MALE	2022-05-28 13:54:17.798864	t	f	2025-07-16 16:25:38.798565	f	f	\N
69	cinema00	$2a$10$KBQCbKYj.ZRTCSADxQogXupINa0H3OUwrUd2DhdS.R.8Lod4EfvKG			6a00adbe-2c94-4cca-92c2-6bc2c184892f.jpg	\N	\N			MALE	2025-06-25 19:26:46.259487	t	f	2025-06-26 20:53:50.280352	f	f	2025-06-25 19:27:03.990315
40	handsapp	$2a$10$LB8tX3DdlX9iXaoIHQfgcuO0wkXd6ugZ7Y2Lzs1YWwwUW9sQZC/bi			9d7c0aa1-fd0c-4963-afab-3b578f6fea1a.jpg	HandsApp - социальная сеть, ориентированная на обмен фотографиями, видео, создание вишлистов, TODO-задач, прослушивание музыки и многое другое	https://vk.com/handsapp.online			MALE	2023-03-12 01:00:21.762967	t	f	2025-06-30 13:02:02.719265	f	f	2025-06-26 22:38:52.997258
120	104162692515758568232	$2a$10$Z./fdothA3rzQsA591PU7OSHd7Q5tpAXdB3GxD.2JTEcnnK8OHSoW	попов	павел борисович	https://lh3.googleusercontent.com/a/ACg8ocJDbHIw5yAm1yPIstQmiWP4f4A-d7FMzy_FZ0Ljx-5F1UZa2rw=s96-c	\N	\N	pasha.hi.fi@gmail.com	\N	\N	2025-07-02 13:29:35.884228	t	t	2025-07-02 13:29:35.951952	f	f	\N
30	nmokretsova	$2a$10$N9QYrJNr7OHQhmMAffGX0uPMepcyNtAG/QNFrhTY3M8keXYBEUfEW	МК	Наталья	gldsybao.jpg	Очень общительная	\N	natalyasv.twins@yandex.ru	89104481983	FEMALE	2022-06-29 15:27:13.852559	t	f	2025-07-16 16:31:01.87278	f	f	\N
86	prodigy	$2a$10$ga3sK4.mTR8BEPSFqOi7E.s9A5JBEmdRYJijLreCaoD3pPw9UyKCa			e7e29a86-30ba-442a-85c9-2428c3011db3.jpg	\N	\N			MALE	2025-06-27 11:49:58.45077	t	f	2025-06-27 11:50:13.642784	f	f	\N
88	stranger_things	$2a$10$ujrwb24ru231tzmEMNM7F.AlalD3Mup0KeH/J0CbrY4HzX5ipvFhy			45581c53-b8eb-45a3-b903-3aa457bacb88.jpg	\N	\N			MALE	2025-06-27 14:12:34.254665	t	f	2025-06-27 14:13:03.319932	f	f	\N
114	116080650529711577266	$2a$10$qvPEFQz.qVEnsEjPwf/tduaphFzEVK6BLxacbLOBpsECjWMe9pnVO	Якубов	Илья	https://lh3.googleusercontent.com/a/ACg8ocKL2SpkKQenytNgLbxm5uirFd-fGZAjIXHgCxjm0JIpBe3R=s96-c	\N	\N	iluxich1985@gmail.com	\N	\N	2025-07-01 12:17:45.105875	t	t	2025-07-01 12:17:45.144627	f	f	\N
175	105346016989562077318	$2a$10$SIW2vnVYI1v65ePPIgxfi..3nkp658gtI7VqI03h8JNvpjyKXdjnO	аккаунт	Просто	https://lh3.googleusercontent.com/a/ACg8ocJapoI5zOp8ttzULcsQNkJ92-Rtve-JHsJGQz26-IY5YD7q_Tk=s96-c	\N	\N	oefrs80@gmail.com	\N	\N	2025-07-19 17:30:42.688412	t	t	2025-07-19 17:30:42.734488	f	f	\N
37	car-man	$2a$10$cVetKmJw0Y7W6MVhhwvoEu0hVdWS1d3UHEqt2r91YfmnP0hOZUq4C	КАР-МЭН ★ CAR☭MAN (фан-легион)		6b02a16a-de26-46cd-b237-77924f92157a.jpg	Фанатский легион группы КАР-МЭН ;)	https://vk.com/lemohtitomir			MALE	2023-02-20 01:27:46.654316	t	f	2025-08-21 15:03:21.257528	f	f	2025-08-18 16:24:39.03707
36	shurrik_music	$2a$10$1gXuvFPPdZfsUPLJooG8g.p3KUXiHU3KIVtdR.PHA519dGTJxKLsS			xrbnsise.jpg	\N	\N			MALE	2023-01-04 21:31:57.459672	t	f	2025-07-16 16:29:36.500316	f	f	\N
92	Brooklyn_Bounce	$2a$10$Bly/EkKpfpc1GAXqR1TX5uobZDGMg7vDTce0y33D0nrswTOLTTzfq			cc844d1e-aba1-433b-b2d2-16cd17b917a2.jpg	\N	\N			MALE	2025-06-27 15:37:55.218306	t	f	2025-06-27 15:38:08.928551	f	f	\N
41	itlife	$2a$10$qjSzh7wj4PmG5XKpO.HpEupYXX.kQ5I08vKOTF7.YF4/pYFjyt9nO	Всё о программировании и Java		xcjlxgzv.jpg	\N	https://vk.com/itliferun			MALE	2023-03-12 01:01:58.946875	t	f	2025-06-25 02:09:39.400648	f	f	2025-06-25 00:07:29.703611
67	Pavlusha	$2a$10$KitgqxiSl6IqaEzZ6j4Y1eNpb5z2KcRAnoNgaC.5hVNGf.Nzg3iqK		Наталка	\N	\N	\N	pavlova.cifra@mail.ru	9307496816	MALE	2025-06-25 15:07:31.183001	t	f	2025-06-26 18:32:32.127846	f	f	2025-06-26 18:23:25.709523
4	shurrik77	$2a$10$PZZKiQS94tiT467Mf0fKFesGOZ0myjBUNT9cHWR8B92EKojqjEmRi	Павлов	Александр	b3d81efc-589c-428d-b6f7-1d6724238a7d.jpg	\N	https://vk.com/aspmap	\N	\N	MALE	2022-05-21 21:14:54.011308	t	f	2025-08-21 15:01:59.786636	f	f	2025-08-19 17:56:39.29701
65	666	$2a$10$vrrATcgGwlqoihrvcGEw/ev4iariNJDBCxVEhtT.GG0NgQBDeQftu			4605f840-d0fc-491e-a285-444118035362.jpg	\N	\N			MALE	2025-06-25 13:04:14.893494	t	f	2025-06-25 13:04:37.653095	f	f	\N
106	Ринат	$2a$10$cEkbQS85Qweiu4y.Rl3LZu8X8B0vvrLMTxUiy4Rf9QsRtWxEaBbHq	Якупов	Ринат	\N	\N	\N	rinat_yakupov@mail.ru	89673622581	\N	2025-06-30 00:10:57.660442	t	f	2025-06-30 00:11:25.4796	f	f	\N
98	117451245895044495399	$2a$10$na7uy3TEqQEzJyek1Q4goOksAGjZcAAW84ZnaUFgzydMDEJj4LpOa	\N	Александр	https://lh3.googleusercontent.com/a/ACg8ocLJitZuWO8pEjc--oZdK9YX0GbsuJFWFWxOBZ1KISEeFjLI2MrI=s96-c	\N	\N	aspmap77@gmail.com	\N	\N	2025-06-29 00:43:42.527728	t	t	2025-10-04 20:14:56.203266	f	f	2025-09-26 23:47:44.777725
179	bus_blg	$2a$10$597j8aqAqkxfBMDRzkXvVu4d1W8zguiL97BdDC25I9HgZmJ.vdNK2			e3a1f751-b3c7-4e57-8004-bde0f14b6c24.jpg	\N	\N			MALE	2025-07-21 04:17:33.037304	t	f	2025-07-21 04:17:47.893792	f	f	\N
116	100775404854752783125	$2a$10$KWEdmZ1g2IhgdznJqsg66OmnCXIyLYXDpGyAT18AtEipxA1SpDvtS	Павлов	Павел	https://lh3.googleusercontent.com/a/ACg8ocL-asKoFPRRaZAju18nqTG88HjVcjF_cAcBMh-iIKlZxZkxSxag=s96-c	\N	\N	ppv76@mail.ru	\N	\N	2025-07-01 17:02:07.415462	t	t	2025-07-01 17:02:07.453444	f	f	\N
118	112913997365849233274	$2a$10$FhfhK5w5CK83EQy1RPXJsOY0XbSKoDN1KTaZMhUfUhUwij1UMTisC	Chan	Kukuri	https://lh3.googleusercontent.com/a/ACg8ocIsZwxEFfTlgAfCqshsH0KdtSSSu3U4zUBCdyq6gqvD_6lBeQ0=s96-c	\N	\N	eragirimono@gmail.com	\N	\N	2025-07-02 02:11:23.4062	t	t	2025-07-02 02:11:23.463133	f	f	\N
75	ScooterAlbums	$2a$10$PzPM.DeWAkko9SjfWmB72.dRXz9Xjp26mubKelzR3hLmK0KARXxp2			5ae56a6e-db77-4a7d-8540-467ae5b2a8f5.jpg	\N	\N			MALE	2025-06-26 13:58:27.672275	t	f	2025-07-02 12:48:52.093266	f	f	2025-07-01 18:17:45.466387
71	Blackpink	$2a$10$i8meGPhBLfHZtXMZiKL1NOcVMl/h3587/v5U42eCjsGuN5.K5XBx2			e57ed14f-4abf-4d45-b221-52573714fa30.jpg	\N	\N			MALE	2025-06-25 22:11:18.01575	t	f	2025-06-26 20:53:23.54682	f	f	2025-06-25 22:29:21.131028
202	markII	$2a$10$H5Ec7x3wTx9Cij8BHaiVDuVRRQAuXDKjfVCOV2E3kjvGpAY3sYfii			84841525-f9b4-41eb-a711-92c9c4f60827.jpg	\N	\N			MALE	2025-08-03 17:00:27.53511	t	f	2025-08-03 17:00:45.412853	f	f	\N
1	admin	$2a$10$H156Jgo8kfqfFNhc9Q9YaeKPaFlcdfG.bGY.c7culdIrl1Ox19xC2	Админов	Админ	c191a883-5c17-4a77-bc3d-6eb4ae2ad745.jpg	\N	\N	\N	\N	MALE	2022-05-21 20:53:54.515757	t	f	2025-11-13 20:02:44.383847	f	f	2025-10-27 05:38:54.432705
\.


--
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 346, true);


--
-- PostgreSQL database dump complete
--

