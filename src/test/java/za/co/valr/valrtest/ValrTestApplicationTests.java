package za.co.valr.valrtest;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import za.co.valr.valrtest.model.*;
import za.co.valr.valrtest.repository.OrderBookRepository;
import za.co.valr.valrtest.repository.TradeRepository;

import java.io.File;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ValrTestApplicationTests {

	@Autowired
	private WebApplicationContext context;
	@Autowired
	private MockMvc mockMvc;
	private OrderBookRepository orderBookRepository;
	private TradeRepository tradeRepository;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		this.objectMapper = new ObjectMapper();
		this.objectMapper.registerModule(new JavaTimeModule());
		this.orderBookRepository = (OrderBookRepository) context.getBean("orderBookRepository");
		this.tradeRepository = (TradeRepository) context.getBean("tradeRepository");
	}

	@Test
	void contextLoads() {
		assertTrue(context.containsBean("swaggerApi"));
		assertTrue(context.containsBean("orderBookRepository"));
		assertTrue(context.containsBean("marketDataService"));
		assertTrue(context.containsBean("orderBookMapper"));
		assertTrue(context.containsBean("tradeRepository"));
		assertTrue(context.containsBean("limitOrderRepository"));
		assertTrue(context.containsBean("tradeMapper"));
		assertTrue(context.containsBean("exchangeService"));
		assertTrue(context.containsBean("limitOrderMapper"));
	}

	@Test
	public void actuatorIsUp() throws Exception {
		this.mockMvc.perform(get("/actuator/health")).
				andExpect(status().isOk())
				.andExpect(content().contentType("application/vnd.spring-boot.actuator.v3+json"))
				.andExpect(content().string("{\"status\":\"UP\"}"));
	}

	@Test
	public void getOrderBook() throws Exception {
		OrderBookEntity orderBookEntity = objectMapper.readValue(getFile("Orderbook.json"), OrderBookEntity.class);

		orderBookRepository.save(orderBookEntity);

		OrderBook expResult = objectMapper.readValue(getFile("OrderBookResponse.json"), OrderBook.class);


		this.mockMvc.perform(get("/marketdata/BTCZAR/orderbook")).
				andExpect(status().isOk()).
				andExpect(content().string(objectMapper.writeValueAsString(expResult)));
	}
	@Test
	public void getOrderBook_notFound() throws Exception {
		OrderBookEntity orderBookEntity = objectMapper.readValue(getFile("Orderbook.json"), OrderBookEntity.class);

		orderBookRepository.save(orderBookEntity);


		this.mockMvc.perform(get("/marketdata/ETHZAR/orderbook")).
				andExpect(status().isNotFound());
	}

	@Test
	public void getAllTrades() throws Exception {
		TypeReference<List<TradeEntity>> typeRef
				= new TypeReference<List<TradeEntity>>() {};
		List<TradeEntity> tradeEntities = objectMapper.readValue(getFile("TradeHistory.json"), typeRef);

		tradeRepository.saveAll(tradeEntities);


		this.mockMvc.perform(get("/marketdata/BTCZAR/tradeHistory")).
				andExpect(status().isOk()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	@Test
	public void getAllTrades_notFound() throws Exception {
		TypeReference<List<TradeEntity>> typeRef
				= new TypeReference<List<TradeEntity>>() {};
		List<TradeEntity> tradeEntities = objectMapper.readValue(getFile("TradeHistory.json"), typeRef);

		tradeRepository.saveAll(tradeEntities);


		this.mockMvc.perform(get("/marketdata/ETHZAR/orderbook")).
				andExpect(status().isNotFound());
	}

	File getFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource("data/"+fileName).getFile());
	}

	@Test
	public void postOrderLimit() throws Exception {
		LimitOrder limitOrder = new LimitOrder();
		limitOrder.setSide("SELL");
		limitOrder.setQuantity("0.100000");
		limitOrder.setPrice("10000");
		limitOrder.setCurrencyPair("BTCZAR");
		limitOrder.setPostOnly(true);
		limitOrder.setCustomerOrderId("1234");



		this.mockMvc.perform(post("/orders/limit").
				contentType(MediaType.APPLICATION_JSON).
				content(objectMapper.writeValueAsString(limitOrder))).
				andExpect(status().isOk()).
				andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}


}
