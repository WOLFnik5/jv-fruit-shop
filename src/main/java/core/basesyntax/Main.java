package core.basesyntax;

import core.basesyntax.dao.ReportReader;
import core.basesyntax.dao.ReportReaderImpl;
import core.basesyntax.dao.ReportWriter;
import core.basesyntax.dao.ReportWriterImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.OperationStrategyImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        final String inputReport = "src/main/resources/reportToRead.csv";
        final String finalReport = "src/main/resources/finalReport.csv";

        ReportReader reader = new ReportReaderImpl();
        List<String> lines = reader.read(inputReport);

        DataConverter converter = new DataConverterImpl();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationStrategy strategy = new OperationStrategyImpl(operationHandlers);

        List<FruitTransaction> transactions = converter.toTransactions(lines);

        ShopService shopService = new ShopServiceImpl(strategy);
        shopService.process(transactions);

        ReportGenerator generator = new ReportGeneratorImpl();
        String resultingReport = generator.build();

        ReportWriter writer = new ReportWriterImpl();
        writer.write(resultingReport, finalReport);
    }
}
