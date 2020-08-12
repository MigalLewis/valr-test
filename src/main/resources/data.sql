insert into order_book_entity(id, currency_pair, last_change) values (1, 'BTCZAR', '2020-08-07T06:54:18.261Z')
insert into ask_entity(id, currency_pair, order_count, price, quantity, side) values (1, 'BTCZAR', 13, '215413', '2.57680846', 'sell')
insert into ask_entity(id, currency_pair, order_count, price, quantity, side) values (2, 'BTCZAR', 2, '215600', '0.7574323', 'sell')
insert into ask_entity(id, currency_pair, order_count, price, quantity, side) values (3, 'BTCZAR', 2, '215601', '0.135339', 'sell')

insert into bid_entity(id, currency_pair, order_count, price, quantity, side)  values (1, 'BTCZAR', 7, '215412', '10.92911387', 'buy')
insert into bid_entity(id, currency_pair, order_count, price, quantity, side)  values (2, 'BTCZAR', 1, '214701', '0.025', 'buy')
insert into bid_entity(id, currency_pair, order_count, price, quantity, side)  values (3, 'BTCZAR', 1, '214700', '0.0349367', 'buy')

update ask_entity set order_book_id=1 where id=1
update ask_entity set order_book_id=1 where id=2
update ask_entity set order_book_id=1 where id=3

update bid_entity set order_book_id=1 where id=1
update bid_entity set order_book_id=1 where id=2
update bid_entity set order_book_id=1 where id=3

insert into trade_entity(id, currency_pair, price, quantity, quote_volume, sequence_id, taker_side, traded_at)  values ('d82cad6d94c4486396a4d4eb4afdd7fe', 'BTCZAR', '215413', '0.0251','5406.8663',489055, 'buy','2020-08-07T06:58:01.779577Z')
insert into trade_entity(id, currency_pair, price, quantity, quote_volume, sequence_id, taker_side, traded_at)  values ('8c799b4cfb554a0bb434cb5d9c4e6f81', 'BTCZAR', '215413', '0.00254239','547.66385707',489054, 'buy','2020-08-07T06:58:00.197377Z')
insert into trade_entity(id, currency_pair, price, quantity, quote_volume, sequence_id, taker_side, traded_at)  values ('7bd6b7d0885742149122a9257eb32754', 'BTCZAR', '215413', '0.04312925','9290.60113025',489053, 'buy','2020-08-07T06:57:43.537708Z')
