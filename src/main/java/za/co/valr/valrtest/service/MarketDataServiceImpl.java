package za.co.valr.valrtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.valr.valrtest.mapper.OrderBookMapper;
import za.co.valr.valrtest.mapper.TradeMapper;
import za.co.valr.valrtest.model.OrderBookEntity;
import za.co.valr.valrtest.model.OrderBook;
import za.co.valr.valrtest.model.Trade;
import za.co.valr.valrtest.model.TradeEntity;
import za.co.valr.valrtest.repository.OrderBookRepository;
import za.co.valr.valrtest.repository.TradeRepository;

import java.util.List;


@Service("valrService")
public class MarketDataServiceImpl implements MarketDataService {
    private OrderBookRepository orderBookRepository;
    private OrderBookMapper orderBookMapper;
    private TradeRepository tradeRepository;
    private TradeMapper tradeMapper;

    @Override
    public OrderBook getOrderBook(String currencyPair) {
        OrderBookEntity orderBookEntity = orderBookRepository.findByCurrencyPair(currencyPair);
        return orderBookMapper.orderBookEntityToOrderBook(orderBookEntity);
    }

    @Override
    public List<Trade> getAllTrades(String currencyPair) {
        List<TradeEntity> tradeEntities = tradeRepository.findByCurrencyPair(currencyPair);
        return tradeMapper.map(tradeEntities);
    }

    @Autowired
    public void setOrderBookRepository(OrderBookRepository orderBookRepository) {
        this.orderBookRepository = orderBookRepository;
    }

    @Autowired
    public void setOrderBookMapper(OrderBookMapper orderBookMapper) {
        this.orderBookMapper = orderBookMapper;
    }


    @Autowired
    public void setTradeRepository(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Autowired
    public void setTradeMapper(TradeMapper tradeMapper) {
        this.tradeMapper = tradeMapper;
    }
}
