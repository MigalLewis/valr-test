package za.co.valr.valrtest.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.co.valr.valrtest.mapper.LimitOrderMapper;
import za.co.valr.valrtest.mapper.OrderBookMapper;
import za.co.valr.valrtest.mapper.TradeMapper;

@Configuration
public class BeanConfig {

    @Bean
    public OrderBookMapper orderBookMapper() {
        return Mappers.getMapper(OrderBookMapper.class);
    }

    @Bean
    public TradeMapper tradeMapper() {
        return Mappers.getMapper(TradeMapper.class);
    }

    @Bean
    public LimitOrderMapper limitOrderMapper() {
        return Mappers.getMapper(LimitOrderMapper.class);
    }

}
