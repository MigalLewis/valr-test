package za.co.valr.valrtest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.valr.valrtest.exceptions.BadRequest;
import za.co.valr.valrtest.exceptions.NotFound;
import za.co.valr.valrtest.model.OrderBook;
import za.co.valr.valrtest.model.Trade;
import za.co.valr.valrtest.service.MarketDataService;

import java.util.List;

@RestController
public class MarketDataController {
    @Autowired
    private MarketDataService marketDataService;

    @GetMapping("{currencyPair}/orderbook")
    public @ResponseBody OrderBook getOrderBook(@PathVariable String currencyPair) throws NotFound, BadRequest {
        return this.marketDataService.getOrderBook(currencyPair);
    }
    @GetMapping("{currencyPair}/tradeHistory")
    public @ResponseBody List<Trade> getAllTrades(@PathVariable String currencyPair) {
        return this.marketDataService.getAllTrades(currencyPair);
    }
}
