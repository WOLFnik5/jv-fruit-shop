package core.basesyntax.service.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> handlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation op) {
        OperationHandler h = handlers.get(op);
        if (h == null) {
            throw new IllegalArgumentException("No handler for op: " + op);
        }
        return h;
    }
}
