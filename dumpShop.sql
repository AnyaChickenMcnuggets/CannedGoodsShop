--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1
-- Dumped by pg_dump version 15.1

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

DROP DATABASE IF EXISTS shop;
--
-- Name: shop; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE shop WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';


ALTER DATABASE shop OWNER TO postgres;

\connect shop

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id integer NOT NULL,
    name character varying(255)
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.category_id_seq OWNER TO postgres;

--
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;


--
-- Name: image; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.image (
    id integer NOT NULL,
    file_name character varying(255),
    product_id integer NOT NULL
);


ALTER TABLE public.image OWNER TO postgres;

--
-- Name: image_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.image_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.image_id_seq OWNER TO postgres;

--
-- Name: image_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.image_id_seq OWNED BY public.image.id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id integer NOT NULL,
    date_time timestamp(6) without time zone,
    final_price real NOT NULL,
    number character varying(255),
    status smallint,
    person_id integer NOT NULL
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orders_id_seq OWNER TO postgres;

--
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    id integer NOT NULL,
    login character varying(100),
    password character varying(100),
    role character varying(50) NOT NULL
);


ALTER TABLE public.person OWNER TO postgres;

--
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.person_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.person_id_seq OWNER TO postgres;

--
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id integer NOT NULL,
    date_time timestamp(6) without time zone,
    description text NOT NULL,
    price real NOT NULL,
    seller character varying(255) NOT NULL,
    title text NOT NULL,
    warehouse character varying(255) NOT NULL,
    category_id integer NOT NULL,
    CONSTRAINT product_price_check CHECK ((price >= (1)::double precision))
);


ALTER TABLE public.product OWNER TO postgres;

--
-- Name: product_cart; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_cart (
    id integer NOT NULL,
    person_id integer,
    product_id integer,
    quantity integer
);


ALTER TABLE public.product_cart OWNER TO postgres;

--
-- Name: product_cart_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_cart_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_cart_id_seq OWNER TO postgres;

--
-- Name: product_cart_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_cart_id_seq OWNED BY public.product_cart.id;


--
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_id_seq OWNER TO postgres;

--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_id_seq OWNED BY public.product.id;


--
-- Name: product_order; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product_order (
    id integer NOT NULL,
    orders_id integer,
    price real NOT NULL,
    product_id integer,
    quantity integer NOT NULL,
    product_title character varying(255)
);


ALTER TABLE public.product_order OWNER TO postgres;

--
-- Name: product_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.product_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.product_order_id_seq OWNER TO postgres;

--
-- Name: product_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.product_order_id_seq OWNED BY public.product_order.id;


--
-- Name: category id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);


--
-- Name: image id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image ALTER COLUMN id SET DEFAULT nextval('public.image_id_seq'::regclass);


--
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- Name: person id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- Name: product id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product ALTER COLUMN id SET DEFAULT nextval('public.product_id_seq'::regclass);


--
-- Name: product_cart id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_cart ALTER COLUMN id SET DEFAULT nextval('public.product_cart_id_seq'::regclass);


--
-- Name: product_order id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_order ALTER COLUMN id SET DEFAULT nextval('public.product_order_id_seq'::regclass);


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.category (id, name) VALUES (4, 'Овощи консервированные');
INSERT INTO public.category (id, name) VALUES (5, 'Мясные консервы');
INSERT INTO public.category (id, name) VALUES (6, 'Рыбные консервы');
INSERT INTO public.category (id, name) VALUES (7, 'Блюда готовые консервированные');
INSERT INTO public.category (id, name) VALUES (8, 'Фруктовые консервы');
INSERT INTO public.category (id, name) VALUES (9, 'Грибы консервированные');
INSERT INTO public.category (id, name) VALUES (10, 'Паштеты и холодцы');
INSERT INTO public.category (id, name) VALUES (11, 'Сухие пайки');
INSERT INTO public.category (id, name) VALUES (12, 'Мед и варенья');


--
-- Data for Name: image; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.image (id, file_name, product_id) VALUES (81, 'a7c791a0-6e9b-4067-942e-72bef54f7146.6394128300.webp', 19);
INSERT INTO public.image (id, file_name, product_id) VALUES (82, 'b4857c3c-aba5-45e6-ae64-22722cbef6c9.6394128567.webp', 19);
INSERT INTO public.image (id, file_name, product_id) VALUES (83, 'cfcdb167-b6ed-40ca-b41d-4ed44962f5c8.6394128665.webp', 19);
INSERT INTO public.image (id, file_name, product_id) VALUES (84, 'fdc77b80-155c-44c9-b5d4-56b64a8b1bcb.6394128697.webp', 19);
INSERT INTO public.image (id, file_name, product_id) VALUES (85, '22d98b72-7d68-43ff-8d6e-b2dde77d8a95.6394128839.webp', 19);
INSERT INTO public.image (id, file_name, product_id) VALUES (86, '1de69fbc-84c3-4780-93df-0124e7b09a85.6394129449.webp', 20);
INSERT INTO public.image (id, file_name, product_id) VALUES (87, 'eefa2797-eca8-42a2-8ac4-0104573d7d64.6394129460.webp', 20);
INSERT INTO public.image (id, file_name, product_id) VALUES (88, '53bc3ea1-acf2-477d-ba9e-75abe699e5a7.6394129473.webp', 20);
INSERT INTO public.image (id, file_name, product_id) VALUES (89, '8a050440-8005-4d80-9a49-fb7adbca1566.6394129512.webp', 20);
INSERT INTO public.image (id, file_name, product_id) VALUES (90, 'f0fef676-9639-49a8-978a-cb403a9ee0d1.6394129522.webp', 20);
INSERT INTO public.image (id, file_name, product_id) VALUES (101, '9fe595ed-2930-472b-bc91-734e3f6ee7f1.1026881357.webp', 23);
INSERT INTO public.image (id, file_name, product_id) VALUES (102, 'ffb347ad-a716-48c3-8b0d-3275e438e5b6.6568903015.webp', 24);
INSERT INTO public.image (id, file_name, product_id) VALUES (103, 'aec76fdc-465a-4ac4-af92-af4c5c3231fb.1020615970.webp', 25);
INSERT INTO public.image (id, file_name, product_id) VALUES (104, 'a0a84c20-62d6-4f90-9f94-2565ba354164.6374764107.webp', 26);
INSERT INTO public.image (id, file_name, product_id) VALUES (105, '92229f3a-1b03-474a-9412-df6bdb6d56d4.6006646559.webp', 27);
INSERT INTO public.image (id, file_name, product_id) VALUES (107, '2c212ed1-dc06-4aab-a986-35407fa33e4f.1022736580.webp', 29);
INSERT INTO public.image (id, file_name, product_id) VALUES (108, 'bb946b37-4330-476e-8eea-f6c29d36407d.1027193930.webp', 30);
INSERT INTO public.image (id, file_name, product_id) VALUES (109, 'cf19ba99-a3f5-4147-a16d-81beaf3f4220.6359198635.webp', 31);
INSERT INTO public.image (id, file_name, product_id) VALUES (110, '80c60c19-67ca-4b81-9b91-89e6d1ba7774.6375343748.webp', 32);
INSERT INTO public.image (id, file_name, product_id) VALUES (111, '4e3c1468-92c3-42ae-935c-63dad289b9bf.6313264133.webp', 33);
INSERT INTO public.image (id, file_name, product_id) VALUES (112, '395f0a7e-c8e1-4d7e-aef9-fc2cae187467.6236528909.webp', 34);
INSERT INTO public.image (id, file_name, product_id) VALUES (113, '54098ec8-7df5-49e6-b0fb-f2f161755b6f.6494040877.webp', 35);
INSERT INTO public.image (id, file_name, product_id) VALUES (114, '3f0ef72b-b358-447a-a2ce-7b01af546477.1033261517.webp', 36);
INSERT INTO public.image (id, file_name, product_id) VALUES (115, '73d7e444-2630-4327-8c9e-80cc735ae3a3.6245301155.webp', 37);
INSERT INTO public.image (id, file_name, product_id) VALUES (116, '1370a4fb-f78b-4b65-affb-42bca5df90cb.6538067059.webp', 38);
INSERT INTO public.image (id, file_name, product_id) VALUES (117, 'bcad2a9f-ef4d-4c41-a9c9-4ae9fa066d5f.6611038355.webp', 39);
INSERT INTO public.image (id, file_name, product_id) VALUES (118, 'd2d320d1-26bb-43da-bf75-9111f9cb3c46.6494456182.webp', 40);
INSERT INTO public.image (id, file_name, product_id) VALUES (119, '51382177-2d29-4251-8240-17355276617d.6278363957.webp', 41);
INSERT INTO public.image (id, file_name, product_id) VALUES (120, 'a99d933b-8fa8-4af4-9a9a-54a2ca29dc4f.6494456250.webp', 42);
INSERT INTO public.image (id, file_name, product_id) VALUES (121, 'e34520d7-697c-4480-bd32-311415d684ff.6613839647.webp', 43);
INSERT INTO public.image (id, file_name, product_id) VALUES (122, 'e87c503c-80a5-4c1b-a098-2dd23ee204f7.6359260924.webp', 44);
INSERT INTO public.image (id, file_name, product_id) VALUES (123, '1fd10b0d-c32a-4f8f-837a-015b81940c25.1019557063.webp', 45);
INSERT INTO public.image (id, file_name, product_id) VALUES (124, 'f4e87f44-107c-4f4f-be03-ad7a5a51a41a.1019557053.webp', 46);
INSERT INTO public.image (id, file_name, product_id) VALUES (126, '9297eb6b-960c-486b-b0ba-7206958de404.6509072349.webp', 48);
INSERT INTO public.image (id, file_name, product_id) VALUES (127, '57891ccc-5e13-4821-a970-0f3949269fae.6529560040.webp', 49);
INSERT INTO public.image (id, file_name, product_id) VALUES (128, 'c2d4b3e6-e1f5-41f8-8cb4-157ef6fc35e5.6332022559.webp', 50);
INSERT INTO public.image (id, file_name, product_id) VALUES (129, 'd272dbe9-905c-40c1-ad3f-9c6727774da2.6235167093.webp', 51);
INSERT INTO public.image (id, file_name, product_id) VALUES (130, 'ab2599f9-d69c-41c7-93a6-5b1add26ee95.6533733867.webp', 52);
INSERT INTO public.image (id, file_name, product_id) VALUES (131, 'e835bc8b-7a9a-4785-adec-fa64f0a31c02.6610750134.webp', 53);
INSERT INTO public.image (id, file_name, product_id) VALUES (132, '75b0ea07-dfac-4204-a58f-7e331c270019.6205751517.webp', 54);
INSERT INTO public.image (id, file_name, product_id) VALUES (133, '4752d26a-dde3-4fb3-8939-10a5e5fd5418.6146634104.webp', 55);
INSERT INTO public.image (id, file_name, product_id) VALUES (134, '6e6ec161-9cbd-44a7-a684-0a51540e7f26.6359144071.webp', 56);
INSERT INTO public.image (id, file_name, product_id) VALUES (1, 'deletedProduct', 1);
INSERT INTO public.image (id, file_name, product_id) VALUES (136, '4652fbcc-8464-48a9-aee4-cbb41a9c468d.XB4CmPlsgAU.jpg', 58);
INSERT INTO public.image (id, file_name, product_id) VALUES (76, '9b3f2238-7467-412d-b408-a699e2afc1eb.6394084656.webp', 18);
INSERT INTO public.image (id, file_name, product_id) VALUES (77, '06d2489c-758c-4ef2-80af-cc4597c68d63.6394084679.webp', 18);
INSERT INTO public.image (id, file_name, product_id) VALUES (78, '780cc719-f1be-4257-a3ee-0500f13d2493.6394084968.webp', 18);
INSERT INTO public.image (id, file_name, product_id) VALUES (79, 'd916ee7c-23d0-4795-8862-17996d742ef7.6394084993.webp', 18);
INSERT INTO public.image (id, file_name, product_id) VALUES (80, 'f9dc4965-fffe-4580-8dab-5717eeb69a10.6394084291.webp', 18);


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.orders (id, date_time, final_price, number, status, person_id) VALUES (14, '2023-04-22 21:16:07.906241', 300, 'dd78c868-8b9c-4f1b-a6da-65c60fd349f030017', 0, 17);


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.person (id, login, password, role) VALUES (15, 'Illidan', '$2a$10$/0cFuhqDHk0h5XB1CWsNX.Fq7AQapJ5kzYWJ23V3EUtj1o0qU90MG', 'ROLE_ADMIN');
INSERT INTO public.person (id, login, password, role) VALUES (17, 'user1', '$2a$10$GlTXFqZsi4wF5eq6xPQmNOyaHTSzSexe2AHcaV5H1cGT7pCWuw5B6', 'ROLE_USER');


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (30, '2023-04-20 21:36:49.894355', 'Консервы рыбные. Килька балтийская неразделанная обжаренная с овощами в томатном соусе по-мексикански. В процессе производства продукции используются только рыбное сырье высочайшего качества и натуральные ингредиенты. Наша продукция богата полинасыщенными кислотами Омега 3 и соответствует самым высоким стандартам экологической безопасности.
Условия хранения
При температуре от 0 до 20 градусов и относительной влажности не более 75%

Состав
Килька балтийская, масло растительное, томатная паста, сахар, мука пшеничная, перец болгарский, кукуруза, морковь, горошек зеленый, фасоль, регулятор кислотности-уксусная кислота, соль, пряности', 109, 'За Родину', 'Килька балтийская За Родину неразделанная, обжаренная, с овощами в томатном соусе по-мексикански, 240 г', 'Розничный', 6);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (31, '2023-04-20 21:40:24.78973', 'Мясо с зеленым горошком и морковью. Второе блюдо изготовленное по стандартам ГОСТ высший сорт. данная продукция одобрена и используется в Вооружённых Силах России МВД, школьных учреждениях. Продукт не содержит искусственных консервантов ароматизаторов и ГМО Перед употреблением рекомендуется разогреть.

НЕ содержит красителей,

НЕ содержит ароматизаторов,

НЕ содержит консервантов

Состав: 

Мясо говядины,

Зеленый горошек

Морковь столовая свежая,

Сахар,

Соль пищевая,

Лавровый лист,

Масло подсолнечное,

Мука пшеничная,

Перец молотый черный
Условия хранения
3 года при температуре от 0°С до +20°С относительной влажности воздуха не более 75 %, После вскрытия потребительской упаковки хранить в холодильнике при температуре от +2°С до +6°С не более суток. Дата изготовления изготовления указана на дне банки в первом ряду. 

Состав
Состав: 

Мясо говядины,
Зеленый горошек
Морковь столовая свежая,
Сахар,
Соль пищевая,
Лавровый лист,
Масло подсолнечное,
Мука пшеничная,
Перец молотый черный', 239, '
Консервсушпрод', 'Консервы мясные стерилизованные. Вторые обеденные блюда. "Мясо с зеленым горошком и морковью"', 'Розничный', 7);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (32, '2023-04-20 21:41:28.399617', 'Каши с мясом - незаменимый продукт на все случаи жизни. Вкусная тушенка выручит вас в походе, на даче или поможет сэкономить время на приготовлении любимых блюд!

Консервированная продукция изготовлена по классическим рецептурам, отличается по своему составу ценными качествами и свойствами.

Массовая доля мяса и жира по закладке в соответствии с рецептурой не менее 48,96%. Массовая доля крупы по закладке в соответствии с рецептурой 17,8%.

Очень сытная каша с мясом от проверенного белорусского мясокомбината. Перед употреблением рекомендуется разогреть.

Срок годности не более 2-х лет со дня изготовления. Дата изготовления указана на донышке банки в первом ряду.
Условия хранения
Хранить при температуре от 0°C до 20°C и относительной влажности воздуха не более 75%. После вскрытия упаковки консервы хранить в холодильнике не более 24 часов при температуре от 2°C до 6°C.

Состав
Свинина, вода, крупа перловая, жир топленый свиной, лук, соль пищевая йодированная, перец черный молотый.', 300, '
Калинковичский мясокомбинат', 'Каша перловая со свининой белорусская КАЛИНКОВИЧИ 340гр.', 'Розничный', 7);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (29, NULL, 'Крупные шпроты в масле.
Условия хранения
Хранить при температуре от 0 до 20 и относительной влажности воздуха не более 75%, открытую банку хранить в холодильнике не более 24 часов.

Состав
Рыба копченая, масло растительное, соль.', 145, '5 Морей', '5 Морей Шпроты в масле, 160 г', 'Розничный', 6);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (18, '2023-04-20 21:11:32.419107', 'Тот самый чудесный вкус, которые многие помнят с детства. Сладкая кукуруза Bonduelle первой появилась на российском рынке, поэтому сегодня, говоря кукуруза, мы чаще всего имеем в виду Bonduelle. Только отборные зерна - сочные и золотистые - попадают в банку всего через 4 часа после сбора урожая.
Условия хранения
Хранить при температуре от 0°С до 25°С и относительной влажности воздуха не более 75%. После вскрытия банки хранить в холодильнике при температуре 4±2°С

Состав
Сладкая кукуруза в зернах, вода, сахар, соль.', 95, 'Bonduelle', 'Кукуруза Bonduelle сладкая в зернах, 170 г', 'Розничный', 4);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (19, '2023-04-20 21:13:07.361531', 'Любимый салат теперь можно готовить быстро и не только по праздникам. Ведь самое сложное мы сделали: все овощи в банке уже вымыты, почищены, эстетично нарезаны и приготовлены на пару. Вам остается просто добавить остальные ингредиенты. Это уникальное предложение на российском рынке: всего несколько минут - и готовый салат на вашем столе радует вас и ваших гостей.
Условия хранения
С относительной влажностью воздуха не более 75%, при температуре от 0`С до +25`С, после вскрытия упаковки хранить в холодильнике при температуре от +2''С до +6''С, после вскрытия хранить в холодильнике в течение 48 часов', 200, 'Bonduelle', 'Оливье Bonduelle " На пару", 310 г', 'Розничный', 4);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (20, '2023-04-20 21:14:33.105594', 'Пожалуй, ни один праздничный стол невозможно представить без знаменитого салата «Оливье», неотъемлемой частью которого является консервированный горошек. И это лишь одно, самое известное, из блюд, в состав которых входит этот продукт. Консервированный зеленый горошек GLOBUS – эталон высочайшего качества еще со времен СССР, за ним устраивали настоящую предпраздничную «охоту» по магазинам города. И в наши дни легендарный консервированный зеленый горошек остается настоящей «визитной карточкой» бренда GLOBUS. Его знаменитый нежный вкус и текстура остались неизменными, он все так же идеально подходит и для праздничных салатов, и для многих повседневных блюд. И это по-прежнему 100% натуральный продукт, без консервантов и искусственных добавок. Как и другие продукты, производящиеся под маркой GLOBUS, горошек изготавливается в соответствии с международными стандартами качества ISO 9001 и ISO 22000. Продукт награжден Российским Знаком Качества*. Зеленый горошек GLOBUS — легенда праздничных застолий!
Условия хранения
После вскрытия хранить в холодильнике в течение 48 часов, при температуре от 0`С до +25`С, с относительной влажностью воздуха не более 75%

Состав
Зеленый горошек быстрозамороженный, вода питьевая, сахар-песок, соль поваренная пищевая.', 109, 'Globus', 'Горошек Globus зеленый, 400 г', 'Розничный', 4);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (23, '2023-04-20 21:24:16.371055', 'Фасоль печеная по-домашнему.
Условия хранения
В прохладном темном месте, при температуре от +1`С до +25`С, с относительной влажностью воздуха не более 75%

Состав
Фасоль продовольственная (белая), лук свежий, томатная паста, сахар, масло подсолнечное, соль, регулятор кислотности: уксусная кислота.', 194, 'Дядя Ваня', 'Дядя Ваня фасоль печеная по-домашнему, 480 г', 'Розничный', 4);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (24, '2023-04-20 21:29:08.371926', 'Условия хранения
при температуре от 0°C до 20°C и относительной влажности не более 75%. Открытую банку хранить в холодильнике и употребить в течение 24 часов.

Состав
Свинина, жир свиной, лук репчатый, соль пищевая, перец чёрный молотый, лавровый лист.
', 398, 'Башкирский купец', 'Тушенка Свинина тушеная Высший сорт Башкирский купец', 'Розничный', 5);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (25, '2023-04-20 21:30:02.237998', 'Изготовлена на новейшем высокотехнологичном оборудовании, что позволяет достичь упругой и сочной консистенции мяса, оригинального вкуса, свойственного классической солено-вареной ветчине. Произведено строго по ГОСТ.
Условия хранения
Хранить при температуре от 0° C до +20° C и относительной влажности воздуха не более 75%. После вскрытия хранить в холодильнике при температуре от +2° С до +6° С

Состав
Свинина, вода питьевая, желатин, соль поваренная пищевая, влагоудерживающий агент - полифосфат натрия, сахар, нитритная соль, антиокислитель - аскорбат калия.', 204, 'Знаток', 'Знаток ветчина домашняя ГОСТ высший сорт, 230 г', 'Розничный', 5);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (26, '2023-04-20 21:31:06.797733', 'Индейкатушеная Люкс, приготовлена по нормамГОСТ. Удобно открывается-Ключ.  Это вкусно!
Условия хранения
Хранить  при температуре от 0°С до 20°С  и относительной влажности воздуха не более 75%. После вскрытия банки продукт хранить в холодильнике не более 24 ЧАСОВ при температуре от 2°С до 6°С. Перед применением рекомендуется охладить . Дата изготовления указана на дне или крышке банки в первом ряду.а не более 75%

Состав
мясо индейки, вода, соль  пищевая, лук сушеный, загуститель (Е407), перец черный молотый. Примите к сведению, что на предприятии используются: молоко, яичный порошок, злаки, содержащие глютен, горчица, соя, сельдерей и кунжут.', 147, 'Рузком', 'Индейка тушеная ГОСТ, 325 г', 'Розничный', 5);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (33, '2023-04-20 21:42:10.413094', 'Первое обеденное блюдо: суп грибной "МеленЪ" приготовлено по домашнему рецепту.

Это:

• ВКУСНО, так как приготовлено профессионалами;

• БЫСТРО, так как можно приготовить за 5 минут;

• ПОЛЕЗНО: без усилителей вкуса, без ароматизаторов, без красителей;

• ПРОСТО, потому что можно совершенно не уметь готовить, но приготовить этот вкусный рассольник как заправский кулинар. Перед употреблением нужно только развести содержимое горячей кипяченой водой в соотношении 1:1 и разогреть на плите или микроволновой печи 2-3 минуты. Такое сможет сделать даже ребенок.

Одной банки хватает для приготовления 3-х порций. Порадуйте себя и близких! Приятного аппетита
Условия хранения
Хранить при температуре от 0 до 25°C и относительной влажности не более 75%. После вскрытия хранить в холодильнике (при температуре +4 +6°С) не более 2-х суток

Состав
овощи (картофель, лук, морковь), вода, грибы шампиньоны, рис, масло растительное подсолнечное, крахмал картофельный, соль, укроп сушеный, петрушка сушеная, перец душистый молотый, перец черный молотый', 195, '
Меленъ', 'МЕЛЕНЪ Суп грибной 460 гр', 'Розничный', 7);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (34, '2023-04-20 21:42:56.710213', 'Классический хумус - это самый популярный вид хумуса, с которого часто начинают знакомство с данным продуктом. Отсутствие добавок и топингов делают вкус классического хумуса наиболее аутентичным. В классическом хумусе максимально раскрывается нутовый вкус, а также ощущается тахина, масло и чеснок.
Условия хранения
9 месяцев при температуре хранения от +2 до +25 С. Вскрытую упаковку хранить в холодильнике не более 72 часов.

Состав
Нут, масло подсолнечное рафинированное дезодорированное, вода, кунжут, соль, чеснок, перец красный, консервант сорбат калия, кислота лимонная. ', 222, 'Салаты и Деликатесы', 'Хумус "Салаты и Деликатесы" классический 200 г', 'Розничный', 7);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (35, '2023-04-20 21:44:20.631104', 'Персики консервированные Botanica представляют собой сочные половинки персиков, бережно сохраненных в сладком сиропе. Эти фруктовые консервы отличаются своей качественной обработкой и натуральным вкусом, благодаря чему они подойдут как для десертов, так и для приготовления различных блюд. Ботаника - это бренд, заботящийся о здоровом питании своих покупателей и предлагающий продукты высокого качества. Персики консервированные Botanica не содержат искусственных красителей и консервантов, что делает их еще более привлекательными для любителей здорового образа жизни. Эти фруктовые консервы станут отличным дополнением к завтраку, десерту или к любому другому блюду, придавая ему неповторимый вкус и аромат. Так что, если вы хотите насладиться свежим и сочным вкусом персиков в любое время года, персики консервированные Botanica - это лучший выбор для вас!
Условия хранения
Хранить при температуре от 0°С до +25°С 
при относительной влажности воздуха не более 75%. После вскрытия переложить продукт в 
неметаллическую посуду и хранить в холодильнике при температуре от +2°С до +5°С в 
течение не более 3 суток.
Срок годности: 36 мес. при соблюдении условий хранения.

Состав
персики(половинки), вода, сахар, антиокислитель-аскорбиновая кислота.', 362, 'Botanica', 'Персики консервированные, половинки в сиропе, 850 мл', 'Розничный', 8);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (36, '2023-04-20 21:44:52.4659', 'Тропический фруктовый коктейль с соком маракуйи.
Условия хранения
Хранить при температуре от 0С до +25С и относительной влажности воздуха не более 75%.

Состав
Кусочки ананаса, кусочки красной папайи, кусочки желтой папайи, вода, сахар, кусочки гуавы, свежеотжатый сок маракуйи, банановое пюре, регулятор кислотности: лимонная кислота.', 258, 'Iberica', 'Тропический коктейль Iberica с соком маракуйи, 425г', 'Розничный', 8);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (37, '2023-04-20 21:45:40.804046', 'Манго сорта Альфонсо выращивается в Индии. Благодаря кремовой текстуре мякоти сорт считается одним из самых лучших во всем мире. Альфонсо обладает сладким вкусом с лёгким ароматом шафрана. В пюре присутствует большое количество минералов, витаминов, ферментов. Манго сорта Альфонсо способствует улучшению пищеварительной функции, сердечной работы, повышает иммунитет.', 2162, '
ECOPRODUCT', 'Пюре манго Альфонсо без сахара, 3.1 кг, без добавок, Экопродукт', 'Розничный', 8);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (38, '2023-04-20 21:46:32.462408', 'Ананасы Королевские - это целые   тщательно отобранные фрукты, очищенные от кожуры, нарезанные на кусочки и залитые концентрированным сахарным сиропом. Они содержат большое количество витаминов: C, B1, B2, B6, B9, A, E, K, PP; минеральных веществ: калий, медь, кальций, магний, фосфор, натрий, цинк и селен, так же холин, бета каротин. Тем, кто хочет похудеть, рекомендуется есть именно кусочки консервированных ананасов, они могут заменить различные сладости, при своей низкой калорийности отлично утоляют голод, ускоряют процессы расщепления белков, препятствуют образованию тромбов, снижают отечность, способствуют улучшению кровообращения и защищают клетки организма, восстанавливают психо-эмоциональное равновесие, улучшают настроение, борются со стрессом, заряжают бодростью.Ананасы прекрасно сочетаются с курицей, входят в состав мясных блюд, используются в приготовлении десертов, выпечки, пиццы, пикантных бутербродов, фруктовых салатов, подаются с мороженым и в составе коктейлей
Условия хранения
Хранить в помещениях, защищенных от прямого попадания солнечных лучей, при температуре от 0 до 30 градусов и относительной влажности воздуха не более 80% После вскрытия продукта хранить в холодильнике при температуре от 0 до 4 градусов в течении 48 часов. Не рекомендуется хранить в открытой жестяной банке.

Состав
Ананасы, вода питьевая,сахар. массовая доля плодов не менее 53% Витамины В,С, кальций, железо.', 250, 'Медведь любимый', 'Ананасы королевские кусочки в сиропе Медведь любимый Вьетнам 580 мл', 'Розничный', 8);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (39, '2023-04-20 21:47:34.866388', 'Смесь по рецепту советского учёного и врача — Николая Михайловича Амосова. Она зарекомендовала себя как средство, которое способствует правильной работе сердечной мышцы, профилактике сердечно-сосудистых заболеваний и укреплению иммунитета, что подтверждается многолетним использованием этой пасты врачами-кардиологами и положительными отзывами людей, попробовавших ее.
Условия хранения
0-25

Состав
Курага, Изюм(темный),Чернослив, Инжир, Мёд, Грецкий орех, Лимон', 792, 'ИП Ларионов Р.Г.', 'Паста Амосова. Мед с сухофруктами и орехами.', 'Розничный', 8);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (40, '2023-04-20 21:48:48.732194', 'Для тех, кто ищет что-то уникальное. Наши маринованные шампиньоны в изысканном маринаде с укропом, красным болгарским перцем и специями теперь производятся на заводе Bonduelle в Краснодарском крае. Но мы не только сменили локацию, но и улучшили рецептуру: значительно уменьшили количество кислот и сахара, что сделало грибы более полезными и еще более вкусными!
Условия хранения
Консервы хранить при температуре от 0 °С до 25 °С и относительной влажности  воздуха не более 75 %

Состав
грибы шампиньоны, вода питьевая, сахар, соль, регулятор кислотности: лимонная кислота, антиокислитель: аскорбиновая кислота, перец красный сладкий сушеный, лук репчатый сушеный, семена горчицы, укроп сушеный, черный перец горошком', 328, 'Bonduelle', 'Шампиньоны Bonduelle маринованные, 500 мл', 'Розничный', 9);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (41, '2023-04-20 21:49:19.935729', 'Целые шампиньоны Lutik отлично впишутся в рацион любой семьи! Консервация -  это специальная обработка продуктов, при которой прекращается размножение  микроорганизмов и деятельность ферментов. 
 
 Уважаемые клиенты! Обращаем ваше внимание на то, что упаковка может иметь несколько видов дизайна. Поставка осуществляется в зависимости от наличия на складе.
Условия хранения
Хранить при температуре от 0°С до 25°С и относительной влажности воздуха не более 75%. После вскрытия банки хранить в холодильнике при температуре 4±2°С

Состав
Шампиньоны, заливка: вода, регулятор кислотности – лимонная кислота, соль.', 172, 'Lutik', 'Шампиньоны Lutik целые, 425мл', 'Розничный', 9);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (42, '2023-04-20 21:50:09.314711', 'Шампиньоны по-провански» – это вкус юга Франции, который стал намного ярче. Круглые, золотистые, один к одному грибы в ароматном маринаде теперь производят на заводе Bonduelle в Краснодарском крае по улучшенной рецептуре. Натуральный аромат прованских трав и вкус красного болгарского перца чувствуются более отчетливо, так как мы значительно уменьшили количество кислот и сахара. Все это сделало грибы намного полезнее! Открываете банку и вуаля – на столе готовая пикантная закуска. Без всяких хлопот!
Условия хранения
Консервы хранить при температуре от 0 °С до 25 °С и относительной влажности  воздуха не более 75 %

Состав
грибы шампиньоны, вода питьевая, сахар, соль, уксус,  регулятор кислотности: лимонная кислота, перец красный сладкий сушеный, лук репчатый сушеный, чеснок сушеный, базилик сушеный, майоран сушеный, чабер сушеный, трава душицы (орегано) сушеная, тимьян сушеный, мята сушеная', 358, 'Bonduelle', 'Шампиньоны Bonduelle по-провански, 500 мл', 'Розничный', 9);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (43, '2023-04-20 21:50:42.698092', 'Консервированные грузди отборные "Lutik" используют на своих кухнях огромное количество предприятий питания и хлебобулочных производств.

Уважаемые клиенты! Обращаем ваше внимание на то, что упаковка может иметь несколько видов дизайна. Поставка осуществляется в зависимости от наличия на складе.

Условия хранения
При температуре от 0`С до +25`С

Состав
Грузди, заливка: вода, соль, чеснок, регулятор кислотности: лимонная кислота. Продукт стерилизован.', 268, 'Lutik', 'Грузди Lutik отборные, 580мл', 'Розничный', 9);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (44, '2023-04-20 21:51:58.284794', 'Настоящее мясо дикого животного, добытое в естественных природных условиях, в сочетании с мастерски подобранными натуральными специями и травами в нежнейшем паштете.

Состав: мясо лося, свинина, печень свиная, пряности, чеснок, соль поваренная пищевая.

Условия хранения до вскрытия банки: при t от 0 °C до 20 °C и относительной влажности воздуха не более 75%. Открытую банку хранить при t от 0 °C до 6 °C не более 24 часов. 

Пищевая ценность (на 100 г. продукта): белок не менее – 18,0 г. Жир не более – 6,0 г. Энергетическая ценность не более 126 ккал/527 кДж.
Условия хранения
Хранить в темном прохладном месте

Состав
мясо лося;свинина;печень свиная;пряности;чеснок;соль', 397, 'Мясничий', 'Паштет Мясничий из лосятины с пряностями и чесноком мясные консервы 240 г. ж/б', 'Розничный', 10);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (45, '2023-04-20 21:52:42.116397', 'Паштет ТМ "Знаток" является уникальным на рынке продуктом, так как он не содержит консервантов, красителей, ароматизаторов и прочих добавок и производится исключительно из натуральных ингредиентов и высококачественного мясного сырья в соответствии со стандартами ГОСТ. Паштет ТМ "Знаток" изготавливается на основе отборной говяжьей печени, которая содержит огромное количество полезных для организма веществ. В то же время паштет ТМ "Знаток" является очень вкусной и сытной закуской на любом столе, будь это праздничный ужин или поход в лес. Для удобства открывания и закрывания банка оснащается ключом.
Условия хранения
Хранить при температуре от 0° C до +20° C и относительной влажности воздуха не более 75%. После вскрытия хранить в холодильнике при температуре от +2° С до +6° С

Состав
Печень говяжья, масло сливочное, мозги, лук, соль пищевая, сахар, перец душистый молотый и черный молотый, орех мускатный, корица, гвоздика.', 158, 'Знаток', 'Знаток паштет печеночный со сливочным маслом ГОСТ, 230 г', 'Розничный', 10);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (46, '2023-04-20 21:53:18.216893', 'Паштет ТМ "Знаток" является уникальным на рынке продуктом, так как он не содержит консервантов, красителей, ароматизаторов и прочих добавок и производится исключительно из натуральных ингредиентов и высококачественного мясного сырья в соответствии со стандартами ГОСТ. Паштет ТМ "Знаток" изготавливается на основе отборной говяжьей или свиной печени, которая содержит огромное количество полезных для организма веществ. В то же время паштет ТМ "Знаток" является очень вкусной и сытной закуской на любом столе, будь это праздничный ужин или поход в лес. Для удобства открывания и закрывания банка оснащается ключом.
Условия хранения
Хранить при температуре от 0° C до +20° C и относительной влажности воздуха не более 75%. После вскрытия хранить в холодильнике при температуре от +2° С до +6° С

Состав
Свинина, печень свиная, молоко коровье, яйца куриные, крахмал картофельный, лук свежий, соль пищевая, имбирь, корица, орех мускатный.', 158, 'Знаток', 'Знаток паштет Пражский ГОСТ, 230 г', 'Розничный', 10);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (48, '2023-04-20 21:54:58.434828', 'Тематический армейский набор из "солдатских каш" и тушеного мяса свиньи входящих в состав наборов сухпаек армейский. Вкусное готовое блюдо на охоте, рыбалке, в походе или автомобильном путешествии для всей семьи. Долгий срок хранения мясной консервации позволяет делать запасы впрок. Все наши продукты оснащены ключом для удобного открытия.Качество ГОСТ-гарантирует, что вам понравятся наши консервы, каши и тушенка. Курганский мясокомбинат — один из крупных производителей тушёнки и мясных консервов. Наша тушенка завоевала любовь потребителя по всей территории РФ.
Условия хранения
Хранить при температуре от 0С до +20С и относительной влажности воздуха не более 75%.

Состав
Свинина тушеная высший сорт ГОСТ 338 гр. "Паек Победа" Состав: свинина, лук, соль, лист лавровый, перец черный.Каша гречневая с говядиной ГОСТ 340 гр. "Паек Победа" Состав: говядина, крупа гречневая, вода, масло подсолнечное, лук, соль, перец черный Каша перловая с говядиной ГОСТ 340 гр. "Паек Победа" Состав: говядина, крупа перловая, вода, масло подсолнечное, лук, соль, перец черный После вскрытия потребительской упаковки консервы хранить в холодильнике не более 24 часов при температуре от 0С до +6С.Перед употреблением рекомендуется разогреть. Не разогревать банку в микроволновой печи.', 693, 'Курганский мясокомбинат', 'Курганский мясокомбинат Комплект консервов "Паек Победа" ( свинина тушеная, каши перловая и гречневая с говядиной), сухпаек', 'Розничный', 11);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (49, '2023-04-20 21:56:18.931239', 'Аварийный рацион питания Marine PRO
Аварийный сухой паек ИРП Marine PRO рассчитан на 3-е суток в аварийной ситуации. Состоит из 9-ти суточных блоков в общей фольгированной упаковке общим весом 468 грамм. Для поддержания деятельности организма требуется употреблять 3 блока в сутки.

Сухпаек аварийный предназначен для поддержания жизнедеятельности организма в аварийных условиях, связанных с дефицитом продуктов с огромным сроком хранения 5 лет. 

Рацион пищевой аварийный позиционируется как морской - при употреблении не вызывает жажду и тем самым позволяет экономить питьевую воду при ее ограничении, поэтому широко применяется для аварийного снабжения морских судов как неприкосновенный запас еды на случай чрезвычайного происшествия. 
Условия хранения
Не требует особых условий хранения

Состав
Мука пшеничная в/с-32%,
         Сахарная пудра-30%,
         Жир кондитерский-28%,
         Овсяные хлопья-10%', 780, 'MARINE PRO', 'Сухой паек МАРИН ПРО Аварийный рацион питания ИРП, 480 грамм', 'Розничный', 11);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (50, '2023-04-20 21:57:07.029637', 'Сухой паек ПОХОДНЫЙ Вариант 1 (ИРП-ПОХОДНЫЙ1) СПЕЦПИТ
В составе пайка имеются армейские галеты, которые изготовлены из пшеничной муки. Также в него включаются консервированные блюда, например, борщ с мясом, перловая каша с говядиной, тефтели из говядины. Их можно есть как в холодном виде, так и употреблять горячими после разогревания.
Условия хранения
В прохладном месте

Состав
Каша перловая с говядиной 250 гр.
Борщ с мясом 360 гр.
Тефтели из говядины 250 гр.
Сыр плавленый стерилизованный 80 гр.
Галеты армейские из муки пшеничной 150 гр.
Концентрат напитка тонизирующий 25 гр.
Повидло яблочное 45 гр.
Чай черный байховый 4 гр.
Сахар 20 гр.
Ложка столовая биоразлагаемая 3 шт.
Салфетки бумажные 3 шт.
 1 к-т', 620, 'Спецпит', 'Сухой паёк ПОХОДНЫЙ Вариант 1 (ИРП-ПОХОДНЫЙ1) СПЕЦПИТ', 'Розничный', 11);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (51, '2023-04-20 21:57:48.34824', 'Сухой паек ТРЕВОЖНЫЙ Вариант 2
Сухой паек в 1,5 раза меньше и легче стандартного суточного рациона питания ИРП-П (повседневный):
 - Используется в тревожных чемоданах ФСО и ФСБ;
 - Применяется в обеспечении питанием сотрудников силовых структур;
 - То, что нужно для подарка коллегам, он им пригодится 
- Не требует особых условий хранения.
Условия хранения
В сухом прохладном месте

Состав
Галеты армейские из муки пшеничной 50 гр.
Говядина тушеная 250 гр.
Каша гречневая с говядиной 250 гр.
Каша перловая с говядиной 250 гр.
Паштет 100 гр.
Повидло яблочное 90 гр.
Чай черный байховый 6 гр.
Сахар 60 гр.
 1 к-т', 676, 'Спецпит', 'Сухой паек ТРЕВОЖНЫЙ для тревожного чемодана ИРП Вариант-2, 1250 г', 'Розничный', 11);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (52, '2023-04-20 21:58:40.528775', 'Срок годности до 23 апреля 2024г.

Суточный паёк стал компактней по габаритам и на треть меньше по весу - 1,47 кг против стандартных 2,1 кг.

В новых спецрационах большое количество продуктов, содержащих углеводы. Это обеспечивает быстрое насыщение и восстановление энергии, что в условиях интенсивных учебно-боевых действий очень важно. Новый рацион выгодно отличается от ранее применявшихся широким ассортиментом мясных и мясорастительных консервов. Так, в паёк вошли "мясо птицы тушёное", "печень пикантная по-восточному", "мясо птицы с картофелем и овощами", "плов с говядиной", "макароны по-флотски", "каша гречневая с печенью".

Вариант 1:
Говядина тушеная 1 сорт - 250 гр. 
Мясо птицы с картошкой и овощами -250 гр. 
Икра из овощей (кабачки) - 100 гр. 
Фарш колбасный - 100 гр.
Вариант 4:
Гуляш говяжий с томатным соусом - 250 гр. 
Макароны по-флотски -250 гр. 
Рагу из овощей - 100 гр. 
Паштет "Нежный" - 100 гр.
Вариант 6:
Говядина тушеная - 250 гр. 
Каша гречневая с печенью -250 гр. 
Рагу из овощей - 100 гр. 
Паштет "Нежный" - 100 гр.
Вариант 7:
Мясо птицы тушеное - 250 гр. 
Каша рисовая с говядиной - 250 гр. 
Икра из овощей (кабачки) - 100 гр. 
Паштет печеночный со сливочным маслом - 100 гр.
Условия хранения
при температуре 0-20гр С и относительной влажности воздуха 75%

Состав
Состав
1. Галеты армейские из муки пшеничные первого сорта
2. Галеты армейские из муки пшеничной отборной
3. Консервы мясные
4. Консервы мясные паштетные и фаршевые
5. Консервы мясорастительные
6. Консервы овощные закусочные
7. Паста шоколадно-ореховая
8. Шоколад горький
9. Палочка фруктовая
10. Повидло фруктовое
11. напиток молочный сухой
12. Фруктовый/ягодный концентрат
13. Чай черный
14. Кофе растворимый
15. Сахар белый кристаллический
16. Соль
17. Перец черный молотый
18. Поливитамины драже
19. Таблетки для дезинфекции воды
20. Разогреватель портативный
21. Спички водоветороустойчивые, комплект
22. Салфетки дезинфицирующие
23. Салфетки бумажные
24. Ложки пластмассовые
25. Инструкция', 668, 'Сухпайки', 'Суточный сухпаек 1,5кг/до 04.2024', 'Розничный', 11);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (58, NULL, 'Печеночный паштет с говяжьейпеченью Perva Extra является одним из бестселлеров среди наших паштетов. И это не удивительно, ведь он буквально тает во рту, также паштет из говяжьей печени положительно влияет на функции кожи, глаз, способствует правильной работе желудка, благотворно влияет на рост и обмен веществ, повышает гемоглобин, улучшает иммунитет. Условия хранения После вскрытия потребительской упаковки консервы хранить в холодильнике не более 24 часов при температуре от 0°С до +6°С Состав Состав: вода,маргарин (рафинированные, дезодорированные масла в натуральном и модифицированном виде (пальмовое масло и его фракции, подсолнечное масло), вода питьевая, эмульгаторы (Е471, соевый лецитин), соль, ароматизатор "Сливки-молоко", краситель Е160а, регулятор кислотности лимонная кислота), печень говяжья, мука рисовая, жир свиной, белок животный, соль, загуститель Е 407, Е412, сахар, смесь экстрактов, эмульгатор Е471, Е472, комплексная пищевая добавка «Говядина» (соль, мальтодекстрин, ароматизатор, усилитель вкуса и аромата Е621), соль нитритная, регулятор кислотности Е451, краситель – красный рисовый, перец черный молотый. Возможно наличие в незначительных количествах яичного порошка, сухого молока, горчицы, сои, злаков, орехов и продуктов их переработки.
', 89, 'PERVA', 'Паштет Деликатесный c говяжьей печенью Perva ключ, 250 г', 'Розничный', 10);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (53, '2023-04-20 21:59:42.541347', 'Мед ГРЕЧИШНЫЙ
Натуральный Алтайский гречишный мёд имеет приятный темно-коричневый цвет, с красноватым оттенком. Имеет не яркий запах, умеренную сладость и нежный терпковато-расходящийся вкус, обжигающий горло. Ежедневное употребление гречишного мёда способствует повышению иммунитета, позволяет активировать обмен веществ, укрепляет нервную систему, очищает от шлаков и токсинов, а также оказывает огромный положительный эффект при лечении различных простудных заболеваний. Гречневый мед широко применяется в косметологии, а также в кулинарии. Каждый день Smolov формирует новые форматы потребления продукта, открывает новые вкусовые нотки. В нашем магазине вы можете ознакомиться широкой разновидностью меда: гречишный, липовый, луговой, горный и др., которые станут отличным подарком на день рождения, свадьбы, праздники, для взрослых и детей, предпочитающим полезные сладости.', 271, 'smolov', 'Мед натуральный Гречишный Алтайский 300г., без сахара, без добавок, подарочный набор', 'Розничный', 12);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (54, '2023-04-20 22:00:09.334744', 'Ароматный джем с насыщенным вкусом черники придется по вкусу не только взрослым, но и детям.
Условия хранения
В сухом прохладном месте

Состав
Ягода, сахар, фруктозно-глюкозный сироп, вода, пектин, лимонная кислота, сорбиновая кислота.', 141, 'Махеевъ', 'Махеевъ джем черничный, 300 г', 'Розничный', 12);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (55, '2023-04-20 22:00:44.772463', 'Массовая доля фруктовой части 60%. Фрукты и ягоды, собранную вручную, без добавления сахара.
Условия хранения
Хранить в сухом, прохладном месте. После вскрытия хранить в холодильнике.

Состав
Черника, вода, подсластители: сироп сорбитола и гликозиды стевиола, желирующий агент: пектин, концентрированный сок лимона, натуральные ароматизаторы.', 335, 'Zuegg', 'Конфитюр Zuegg Черника, с пониженной калорийностью, 220 г', 'Розничный', 12);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (56, '2023-04-20 22:01:33.34318', 'BEES & SEEDS — это свежайший мёд с добавлением полезных ингредиентов. Ничего лишнего в составе: только мёд и куркума с имбирем.

Покупатели говорят, что это очень вкусное сочетание! А еще это мощная польза для иммунитета: куркума уменьшает воспаления в организме, помогает бороться с вирусами и бактериями и лечить многие заболевания. Кушайте наш мёд с чаем или без чая, добавляйте в кашу, намазывайте на хлеб и печенье — он отлично заменяет сахар и конфеты. Попробуйте сами!

Изготавливается с душой: Мёд и компоненты не обрабатываются нагревом, за счёт чего сохраняются все преимущества этих суперфудов, данные природой. Весь используемый мёд проверяется в лабораториях, что гарантирует его натуральность и пользу.

Энергетическая ценность: 334 ккал / 1410 кДж.

 • Белки, 2 г

 • Жиры, 4 г

 • Углеводы, 75.96 г
Условия хранения
В темном прохладном месте без посторонних запахов при температуре до 20С

Состав
Мёд натуральный гречишный,
Куркума молотая,
Имбирь молотый.', 570, 'Bees & Seeds', 'Мёд, Куркума и Имбирь: Медовый урбеч из натурального мёда гречишного, сладость без сахара, полезное пп лакомство, веганский и вегетарианский продукт питания, структура: крем мёд без орехов, 400г', 'Розничный', 12);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (1, '2023-04-21 12:30:59', 'deletedProduct', 1, 'deletedProduct', 'deletedProduct', 'deletedProduct', 4);
INSERT INTO public.product (id, date_time, description, price, seller, title, warehouse, category_id) VALUES (27, NULL, 'При изготовлении ветчины используют только лопаточные и шейные части свинины, которые проходят глубокую термическую обработку, что обеспечивает возможность длительного хранения мясной продукции. 
Ветчина может храниться вне холодильника и использоваться для приготовления бутербродов, салатов, необычных блюд и закусок.
Русский изыскь - Мясные деликатесы достойные Вас!
Условия хранения
Температура хранения от 0 до +20. Открытую упаковку хранить в холодильнике и использовать в течении 24 часов.

Состав
свинина со шкуркой или без шкурки, вода, соль нитритная (соль поваренная, фиксатор окраски e250), соль поваренная, сахар, стабилизатор цвета e301, желатин', 259, 'Русский изыскъ', 'Мясные консервы Русский изыскъ Ветчина, 325 г', 'Розничный', 5);


--
-- Data for Name: product_cart; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: product_order; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product_order (id, orders_id, price, product_id, quantity, product_title) VALUES (28, 14, 100, 1, 3, '5 Морей Килька в томате, 240 г');


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.category_id_seq', 12, true);


--
-- Name: image_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.image_id_seq', 138, true);


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_id_seq', 14, true);


--
-- Name: person_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_id_seq', 17, true);


--
-- Name: product_cart_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_cart_id_seq', 63, true);


--
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_id_seq', 60, true);


--
-- Name: product_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.product_order_id_seq', 28, true);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: image image_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: product_cart product_cart_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_cart
    ADD CONSTRAINT product_cart_pkey PRIMARY KEY (id);


--
-- Name: product_order product_order_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_order
    ADD CONSTRAINT product_order_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: product uk_qka6vxqdy1dprtqnx9trdd47c; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT uk_qka6vxqdy1dprtqnx9trdd47c UNIQUE (title);


--
-- Name: orders fk1b0m4muwx1t377w9if3w6wwqn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk1b0m4muwx1t377w9if3w6wwqn FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- Name: product fk1mtsbur82frn64de7balymq9s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- Name: image fkgpextbyee3uk9u6o2381m7ft1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT fkgpextbyee3uk9u6o2381m7ft1 FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: product_order fkh73acsd9s5wp6l0e55td6jr1m; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_order
    ADD CONSTRAINT fkh73acsd9s5wp6l0e55td6jr1m FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: product_cart fkhpnrxdy3jhujameyod08ilvvw; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_cart
    ADD CONSTRAINT fkhpnrxdy3jhujameyod08ilvvw FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: product_order fknxb19amwrevbigam8p6n0h12s; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_order
    ADD CONSTRAINT fknxb19amwrevbigam8p6n0h12s FOREIGN KEY (orders_id) REFERENCES public.orders(id);


--
-- Name: product_cart fksgnkc1ko2i1o9yr2p63ysq3rn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product_cart
    ADD CONSTRAINT fksgnkc1ko2i1o9yr2p63ysq3rn FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- PostgreSQL database dump complete
--

