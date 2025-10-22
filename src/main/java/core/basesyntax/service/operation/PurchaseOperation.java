package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class PurchaseOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int current = Storage.FRUITS.getOrDefault(fruit, 0);
        int newQuantity = current - transaction.getQuantity();

        if (newQuantity < 0) {
            throw new RuntimeException("Not enough "
                    + fruit
                    + " in storage. "
                    + "Tried to purchase "
                    + transaction.getQuantity()
                    + ", but only "
                    + current
                    + " available.");
        }

        Storage.FRUITS.put(fruit, newQuantity);
    }
}
