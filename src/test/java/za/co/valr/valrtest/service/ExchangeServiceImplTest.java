package za.co.valr.valrtest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import za.co.valr.valrtest.mapper.LimitOrderMapper;
import za.co.valr.valrtest.mapper.OrderBookMapper;
import za.co.valr.valrtest.model.LimitOrder;
import za.co.valr.valrtest.model.LimitOrderEntity;
import za.co.valr.valrtest.model.OrderBook;
import za.co.valr.valrtest.model.OrderBookEntity;
import za.co.valr.valrtest.repository.LimitOrderRepository;
import za.co.valr.valrtest.repository.OrderBookRepository;
import za.co.valr.valrtest.repository.TradeRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeServiceImplTest {
    private LimitOrderRepository limitOrderRepository;
    private ObjectMapper objectMapper;
    private ExchangeServiceImpl exchangeService;

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.limitOrderRepository = Mockito.mock(LimitOrderRepository.class);
        this.exchangeService = new ExchangeServiceImpl();
        this.exchangeService.setLimitOrderRepository(limitOrderRepository);
        this.exchangeService.setLimitOrderMapper(Mappers.getMapper(LimitOrderMapper.class));
    }

    @Test
    void addLimitOrder() {

        LimitOrder limitOrder = new LimitOrder();
        limitOrder.setSide("SELL");
        limitOrder.setQuantity("0.100000");
        limitOrder.setPrice("10000");
        limitOrder.setCurrencyPair("BTCZAR");
        limitOrder.setPostOnly(true);
        limitOrder.setCustomerOrderId("1234");

        LimitOrderEntity limitOrderEntity = new LimitOrderEntity();
        limitOrderEntity.setId(12345l);
        Mockito.when(limitOrderRepository.save(ArgumentMatchers.any(LimitOrderEntity.class)))
                .thenReturn(limitOrderEntity);

        long result = exchangeService.addLimitOrder(limitOrder);
        long expResult = 12345l;
        assertEquals(expResult, result);
    }
}