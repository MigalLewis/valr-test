package za.co.valr.valrtest.model;

import lombok.Data;

@Data
public class LimitOrder {
    private String side;
    private String quantity;
    private String price;
    private String currencyPair;
    private boolean postOnly;
    private long customerOrderId;
}
