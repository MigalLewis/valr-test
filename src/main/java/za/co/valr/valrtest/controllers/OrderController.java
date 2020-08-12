package za.co.valr.valrtest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.valr.valrtest.model.LimitOrder;
import za.co.valr.valrtest.service.ExchangeService;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Autowired
    private ExchangeService exchangeService;

    @PostMapping("limit")
    public @ResponseBody long getOrderBook(@RequestBody LimitOrder limitOrder) {
        return this.exchangeService.addLimitOrder(limitOrder);
    }
}
