package pl.ing.pionteching.common;

import pl.ing.pionteching.transaction.domain.Account;
import pl.ing.pionteching.transaction.dto.Transaction;

import java.util.List;

public interface Aggregator<RawData,AggregatedData> {
    AggregatedData aggregate(RawData transactions);
}
