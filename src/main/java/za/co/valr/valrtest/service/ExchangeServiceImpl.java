package za.co.valr.valrtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.valr.valrtest.mapper.LimitOrderMapper;
import za.co.valr.valrtest.model.LimitOrder;
import za.co.valr.valrtest.model.LimitOrderEntity;
import za.co.valr.valrtest.repository.LimitOrderRepository;

@Service("exchangeService")
public class ExchangeServiceImpl implements ExchangeService {

    private LimitOrderRepository limitOrderRepository;
    private LimitOrderMapper limitOrderMapper;

    @Override
    public long addLimitOrder(LimitOrder limitOrder) {
        LimitOrderEntity limitOrderEntity = limitOrderMapper.LimitOrderToLimitOrderEntity(limitOrder);
        return limitOrderRepository.save(limitOrderEntity).getId();
    }

    @Autowired
    public void setLimitOrderRepository(LimitOrderRepository limitOrderRepository) {
        this.limitOrderRepository = limitOrderRepository;
    }

    @Autowired
    public void setLimitOrderMapper(LimitOrderMapper limitOrderMapper) {
        this.limitOrderMapper = limitOrderMapper;
    }
}
