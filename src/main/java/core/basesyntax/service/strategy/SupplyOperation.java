package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class SupplyOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction tx, Map<String, Integer> fruits) {
        fruits.merge(tx.getFruit(), tx.getQuantity(), Integer::sum);
    }
}
