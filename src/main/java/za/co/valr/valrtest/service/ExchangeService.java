package za.co.valr.valrtest.service;

import za.co.valr.valrtest.model.LimitOrder;

public interface ExchangeService {
    long addLimitOrder(LimitOrder limitOrder);
}
