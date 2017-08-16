create table ms_parsing_queue
(
	no int auto_increment comment '작업 번호'
		primary key,
	sc_code varchar(100) not null comment '모비온 투베코드',
	priority char(1) null comment '우선순위 0:순차, 1:우선, 2:최우선',
	state char(1) null comment '0 : 대기, 1 : 스레드 큐 입력, 2 : 에러, 3 : 작업 중',
	updatedate timestamp default CURRENT_TIMESTAMP not null comment '작업 시작시간',
	regdate timestamp default '0000-00-00 00:00:00' not null comment '입력시간'
)
;

create table ms_parsing_pattern
(
	sc_code varchar(100) not null comment '모비온 투베코드' primary key,
	shop_type varchar(10) not null comment '상점 유형',
	productlist_pattern varchar(100) comment '상품리스트 패턴',
	title_pattern varchar(100) comment '상품명 패턴',
	url_pattern varchar(100) comment '상품주소 패턴',
	imgurl_pattern varchar(100) comment '이미지주소 패턴',
	price_pattern varchar(100) comment '판매가 패턴',
	originprice_pattern varchar(100) comment '원가 패턴',
	saleCheck_pattern varchar(100) comment '세일체크 패턴',
	productCode_pattern varchar(100) comment '상품코드 패턴',
	urlformat varchar(100) comment '상품주소 포멧',
	page_pattern varchar(100) comment '페이지 패턴',
	soldout_msg varchar(100) comment '품절 패턴',
	filter_msg varchar(100) comment '필터 문자열',
	updatedate timestamp default CURRENT_TIMESTAMP not null comment '작업 시작시간',
	regdate timestamp default '0000-00-00 00:00:00' not null comment '입력시간'
)
;

create table ms_parsing_info
(
	seq bigint auto_increment
		primary key,
	pcode varchar(150) null,
	category_code varchar(150) null,
	url varchar(500) null,
	mobile_url varchar(500) null,
	sc_code varchar(200) null,
	tobe_sc_code varchar(200) null,
	site_name varchar(50) null,
	page varchar(255) null,
	category_match_code int null,
	ssumage varchar(100) null
)
;

create index mpi_cmc
	on ms_parsing_info (category_match_code)
;

create index mpi_tsc
	on ms_parsing_info (tobe_sc_code)
;

create table ms_goods
(
	gscd bigint auto_increment
		primary key,
	pcode varchar(100) not null,
	sc_code varchar(100) not null,
	category varchar(250) default '0' null,
	sitename varchar(50) not null,
	title varchar(250) null,
	image1 varchar(250) not null,
	image2 varchar(250) null,
	image3 varchar(250) null,
	image4 varchar(250) null,
	image5 varchar(250) null,
	image6 varchar(250) null,
	orgprice int(20) null,
	price int(20) null,
	dc_rate int(3) default '0' not null,
	qty_saled int(10) default '0' not null,
	url varchar(300) null,
	regdate timestamp default '0000-00-00 00:00:00' not null,
	updatedate datetime null,
	display int(1) default '0' not null,
	md_recom char default 'N' null comment 'Y : 추천상품',
	inquiry_cnt int default '0' null comment '조회수',
	adminselect varchar(1) null,
	width int null,
	height int null
)
;

create index mg_psearch
	on ms_goods (pcode, sc_code)
;

create index mg_regDate
	on ms_goods (regdate)
;

create index mg_si
	on ms_goods (sitename, image1)
;

create index sc_code_idx
	on ms_goods (sc_code)
;

create index title
	on ms_goods (title)
;

insert into ms_parsing_queue (sc_code, priority, state) VALUE('167b007a416fdcbf602d78a613c98c44', '0', '0');
insert into ms_parsing_queue (sc_code, priority, state) VALUE('d09e6b1113af4fe611d73c965cdd6a0f', '1', '0');
insert into ms_parsing_queue (sc_code, priority, state) VALUE('e4d5322e54248598f2e42a1fcd6af86d', '1', '0');
insert into ms_parsing_queue (sc_code, priority, state) VALUE('22477f12d144bee29b18cb0571ec5589', '1', '0');
