package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    private static final String HEADER = "type,fruit,quantity";

    @Override
    public List<FruitTransaction> toTransactions(List<String> lines) {
        List<FruitTransaction> result = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty() || HEADER.equalsIgnoreCase(line)) {
                continue;
            }
            String[] parts = line.split(",", -1);
            if (parts.length != 3) {
                throw new RuntimeException("Bad csv line at " + i + ": " + line);
            }
            FruitTransaction transaction = new FruitTransaction();
            transaction.setOperation(FruitTransaction.Operation.fromCode(parts[0].trim()));
            transaction.setFruit(parts[1].trim());
            transaction.setQuantity(Integer.parseInt(parts[2].trim()));
            result.add(transaction);
        }
        return result;
    }
}
