package za.co.valr.valrtest.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import za.co.valr.valrtest.exceptions.BadRequest;
import za.co.valr.valrtest.exceptions.NotFound;
import za.co.valr.valrtest.mapper.OrderBookMapper;
import za.co.valr.valrtest.mapper.TradeMapper;
import za.co.valr.valrtest.model.*;
import za.co.valr.valrtest.repository.OrderBookRepository;
import za.co.valr.valrtest.repository.TradeRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataServiceImplTest {
    private MarketDataServiceImpl orderBookService;
    private OrderBookRepository orderBookRepository;
    private TradeRepository tradeRepository;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.orderBookRepository = Mockito.mock(OrderBookRepository.class);
        this.tradeRepository = Mockito.mock(TradeRepository.class);
        this.orderBookService = new MarketDataServiceImpl();
        this.orderBookService.setOrderBookRepository(orderBookRepository);
        this.orderBookService.setTradeRepository(tradeRepository);
        this.orderBookService.setOrderBookMapper(Mappers.getMapper(OrderBookMapper.class));
        this.orderBookService.setTradeMapper(Mappers.getMapper(TradeMapper.class));
    }
    @DisplayName("Given that an order book for BTCZAR is in the repository" +
            "And I have BTCZAR as an input" +
            "When I call getOrderBook" +
            "Then the Order book for BTCZAR should be returned")
    @Test
    void getOrderBook() throws IOException, NotFound, BadRequest {
        OrderBookEntity orderBookEntity = objectMapper.readValue(getFile("Orderbook.json"), OrderBookEntity.class);
        Mockito.when(orderBookRepository.findByCurrencyPair("BTCZAR"))
                .thenReturn(orderBookEntity);

        OrderBook result = orderBookService.getOrderBook("BTCZAR");
        OrderBook expResult = new OrderBook();
        expResult.setAsks(new ArrayList<>());
        setAsks(expResult.getAsks());
        expResult.setBids(new ArrayList<>());
        setBids(expResult.getBids());
        expResult.setLastChange("2020-08-07T06:54:18.261Z");
        assertEquals(expResult, result);
    }
    @DisplayName("Given that an order book for BTCZAR is in the repository" +
            "And I have ETHZAR as an input" +
            "When I call getOrderBook" +
            "Then a Not Found Exception should be thrown")
    @Test
    void getOrderBook_notFound() throws IOException, NotFound, BadRequest {
        OrderBookEntity orderBookEntity = objectMapper.readValue(getFile("Orderbook.json"), OrderBookEntity.class);
        Mockito.when(orderBookRepository.findByCurrencyPair("BTCZAR"))
                .thenReturn(orderBookEntity);

        assertThrows(NotFound.class, () -> {
            orderBookService.getOrderBook("ETHZAR");
        });
    }
    @DisplayName("Given that an order book for BTCZAR is in the repository" +
            "And I have empty input" +
            "When I call getOrderBook" +
            "Then a Bad Request Exception should be thrown")
    @Test
    void getOrderBook_empty() throws IOException, NotFound, BadRequest {
        OrderBookEntity orderBookEntity = objectMapper.readValue(getFile("Orderbook.json"), OrderBookEntity.class);
        Mockito.when(orderBookRepository.findByCurrencyPair("BTCZAR"))
                .thenReturn(orderBookEntity);

        assertThrows(BadRequest.class, () -> {
            orderBookService.getOrderBook("");
        });
    }
    @DisplayName("Given that an order book for BTCZAR is in the repository" +
                 "And I have null input" +
                 "When I call getOrderBook" +
                 "Then a Bad Request Exception should be thrown")
    @Test
    void getOrderBook_null() throws IOException, NotFound, BadRequest {
        OrderBookEntity orderBookEntity = objectMapper.readValue(getFile("Orderbook.json"), OrderBookEntity.class);
        Mockito.when(orderBookRepository.findByCurrencyPair("BTCZAR"))
                .thenReturn(orderBookEntity);

        assertThrows(BadRequest.class, () -> {
            orderBookService.getOrderBook(null);
        });
    }
    @DisplayName("Given that I have a trade history for BTCZAR is in the repository" +
            "And I have BTCZAR as an input" +
            "When I call getAllTrades" +
            "Then the Trade History for BTCZAR should be returned")
    @Test
    void getAllTrades() throws IOException, NotFound, BadRequest {
        TypeReference<List<TradeEntity>> typeRef
                = new TypeReference<List<TradeEntity>>() {};
        List<TradeEntity> trades = objectMapper.readValue(getFile("TradeHistory.json"), typeRef);
        Mockito.when(tradeRepository.findByCurrencyPair("BTCZAR"))
                .thenReturn(trades);

        List<Trade> result = orderBookService.getAllTrades("BTCZAR");
        List<Trade> expResult = new ArrayList<>();
        setTrades(expResult);
        assertEquals(expResult, result);
    }
    @DisplayName("Given that I have a trade history for BTCZAR is in the repository" +
            "And I have ETHZAR as an input" +
            "When I call getAllTrades" +
            "Then a Not Found Exception should be thrown")
    @Test
    void getAllTrades_notFound() throws IOException, NotFound, BadRequest {
        TypeReference<List<TradeEntity>> typeRef
                = new TypeReference<List<TradeEntity>>() {};
        List<TradeEntity> trades = objectMapper.readValue(getFile("TradeHistory.json"), typeRef);
        Mockito.when(tradeRepository.findByCurrencyPair("BTCZAR"))
                .thenReturn(trades);

        assertThrows(NotFound.class, () -> {
            orderBookService.getAllTrades("ETHZAR");
        });
    }
    @DisplayName("Given that I have no trade history for BTCZAR is in the repository" +
            "And I have BTCZAR as an input" +
            "When I call getAllTrades" +
            "Then a Not Found Exception should be thrown")
    @Test
    void getAllTrades_notFound_empty_arraylist() throws IOException, NotFound, BadRequest {
        Mockito.when(tradeRepository.findByCurrencyPair("BTCZAR"))
                .thenReturn(new ArrayList<>());

        assertThrows(NotFound.class, () -> {
            orderBookService.getAllTrades("ETHZAR");
        });
    }
    @DisplayName("Given that I have a trade history  for BTCZAR is in the repository" +
            "And I have empty input" +
            "When I call getAllTrades" +
            "Then a Bad Request Exception should be thrown")
    @Test
    void getAllTrades_empty() throws IOException, NotFound, BadRequest {
        TypeReference<List<TradeEntity>> typeRef
                = new TypeReference<List<TradeEntity>>() {};
        List<TradeEntity> trades = objectMapper.readValue(getFile("TradeHistory.json"), typeRef);
        Mockito.when(tradeRepository.findByCurrencyPair("BTCZAR"))
                .thenReturn(trades);

        assertThrows(BadRequest.class, () -> {
            orderBookService.getAllTrades("");
        });
    }
    @DisplayName("Given that I have a trade history  for BTCZAR is in the repository" +
            "And I have null input" +
            "When I call getAllTrades" +
            "Then a Bad Request Exception should be thrown")
    @Test
    void getAllTrades_null() throws IOException, NotFound, BadRequest {
        TypeReference<List<TradeEntity>> typeRef
                = new TypeReference<List<TradeEntity>>() {};
        List<TradeEntity> trades = objectMapper.readValue(getFile("TradeHistory.json"), typeRef);
        Mockito.when(tradeRepository.findByCurrencyPair("BTCZAR"))
                .thenReturn(trades);

        assertThrows(BadRequest.class, () -> {
            orderBookService.getAllTrades(null);
        });
    }

    File getFile(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("data/"+fileName).getFile());
    }

    void setAsks(List<Ask> asks) {
        asks.add(new Ask());
        asks.get(0).setCurrencyPair("BTCZAR");
        asks.get(0).setOrderCount(13);
        asks.get(0).setQuantity("2.57680846");
        asks.get(0).setSide("sell");
        asks.get(0).setPrice("215413");
        asks.add(new Ask());
        asks.get(1).setCurrencyPair("BTCZAR");
        asks.get(1).setOrderCount(2);
        asks.get(1).setQuantity("0.7574323");
        asks.get(1).setSide("sell");
        asks.get(1).setPrice("215600");
        asks.add(new Ask());
        asks.get(2).setCurrencyPair("BTCZAR");
        asks.get(2).setOrderCount(1);
        asks.get(2).setQuantity("0.135339");
        asks.get(2).setSide("sell");
        asks.get(2).setPrice("215601");
    }
    void setBids(List<Bid> bids) {
        bids.add(new Bid());
        bids.get(0).setCurrencyPair("BTCZAR");
        bids.get(0).setOrderCount(7);
        bids.get(0).setQuantity("10.92911387");
        bids.get(0).setSide("buy");
        bids.get(0).setPrice("215412");
        bids.add(new Bid());
        bids.get(1).setCurrencyPair("BTCZAR");
        bids.get(1).setOrderCount(1);
        bids.get(1).setQuantity("0.025");
        bids.get(1).setSide("buy");
        bids.get(1).setPrice("214701");
        bids.add(new Bid());
        bids.get(2).setCurrencyPair("BTCZAR");
        bids.get(2).setOrderCount(1);
        bids.get(2).setQuantity("0.0349367");
        bids.get(2).setSide("buy");
        bids.get(2).setPrice("214700");
    }
    void setTrades(List<Trade> trades) {
        trades.add(new Trade());
        trades.get(0).setCurrencyPair("BTCZAR");
        trades.get(0).setId("d82cad6d-94c4-4863-96a4-d4eb4afdd7fe");
        trades.get(0).setQuantity("0.0251");
        trades.get(0).setTradedAt("2020-08-07T06:58:01.779577Z");
        trades.get(0).setPrice("215413");
        trades.get(0).setQuoteVolume("5406.8663");
        trades.get(0).setSequenceId(489055);
        trades.get(0).setTakerSide("buy");
        trades.add(new Trade());
        trades.get(1).setCurrencyPair("BTCZAR");
        trades.get(1).setId("8c799b4c-fb55-4a0b-b434-cb5d9c4e6f81");
        trades.get(1).setQuantity("0.00254239");
        trades.get(1).setTradedAt("2020-08-07T06:58:00.197377Z");
        trades.get(1).setPrice("215413");
        trades.get(1).setQuoteVolume("547.66385707");
        trades.get(1).setSequenceId(489054);
        trades.get(1).setTakerSide("buy");
        trades.add(new Trade());
        trades.get(2).setCurrencyPair("BTCZAR");
        trades.get(2).setId("7bd6b7d0-8857-4214-9122-a9257eb32754");
        trades.get(2).setQuantity("0.04312925");
        trades.get(2).setTradedAt("2020-08-07T06:57:43.537708Z");
        trades.get(2).setPrice("215413");
        trades.get(2).setQuoteVolume("9290.60113025");
        trades.get(2).setSequenceId(489053);
        trades.get(2).setTakerSide("buy");
    }
}