package za.co.valr.valrtest;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import za.co.valr.valrtest.model.OrderBook;
import za.co.valr.valrtest.model.OrderBookEntity;
import za.co.valr.valrtest.repository.OrderBookRepository;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		this.objectMapper = new ObjectMapper();
		this.orderBookRepository = (OrderBookRepository) context.getBean("orderBookRepository");
	}

	@Test
	void contextLoads() {
		assertTrue(context.containsBean("swaggerApi"));
		assertTrue(context.containsBean("orderBookRepository"));
		assertTrue(context.containsBean("valrService"));
		assertTrue(context.containsBean("orderBookMapper"));
		assertTrue(context.containsBean("tradeRepository"));
		assertTrue(context.containsBean("limitOrderRepository"));
		assertTrue(context.containsBean("tradeMapper"));
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


		this.mockMvc.perform(get("/BTCZAR/orderbook")).
				andExpect(status().isOk()).
				andExpect(content().string(objectMapper.writeValueAsString(expResult)));
	}

	File getFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource("data/"+fileName).getFile());
	}

}
