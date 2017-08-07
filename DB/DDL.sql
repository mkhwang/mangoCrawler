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