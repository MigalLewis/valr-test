package za.co.valr.valrtest.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Data
public class LimitOrderEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String side;
    @Column
    private double quantity;
    @Column
    private BigDecimal price;
    @Column
    private String currencyPair;
    @Column
    private boolean postOnly;
    @Column
    private long customerOrderId;
}
