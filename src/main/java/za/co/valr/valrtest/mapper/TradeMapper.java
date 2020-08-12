package za.co.valr.valrtest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import za.co.valr.valrtest.model.Trade;
import za.co.valr.valrtest.model.TradeEntity;

import java.util.List;

@Mapper
public interface TradeMapper {
    List<Trade> map(List<TradeEntity> trades);

    @Mappings({
            @Mapping(target="tradedAt", source="entity.tradedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"),
            @Mapping(target="id",expression = "java(entity.getId().toString())"),
            @Mapping(target="price",expression = "java(String.format(\"%.0f\", entity.getPrice()))")
    })
    Trade tradeEntityToTrade(TradeEntity entity);
}
