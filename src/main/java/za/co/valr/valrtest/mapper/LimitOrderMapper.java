package za.co.valr.valrtest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import za.co.valr.valrtest.model.LimitOrder;
import za.co.valr.valrtest.model.LimitOrderEntity;
@Mapper
public interface LimitOrderMapper {
    @Mappings({
            @Mapping(target="price",expression = "java(String.format(\"%.0f\", entity.getPrice()))")
    })
    LimitOrder limitOrderEntityToLimitOrder(LimitOrderEntity entity);

    @Mappings({
    })
    LimitOrderEntity LimitOrderToLimitOrderEntity(LimitOrder dto);
}
