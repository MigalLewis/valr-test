package za.co.valr.valrtest.repository;
import org.springframework.data.repository.CrudRepository;
import za.co.valr.valrtest.model.LimitOrderEntity;

public interface LimitOrderRepository extends CrudRepository<LimitOrderEntity,Long> {
    public LimitOrderEntity findByCurrencyPair(String currencyPair);
}
