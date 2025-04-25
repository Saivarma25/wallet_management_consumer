package in.pinnacle.apps.wallet.api.util;

import lombok.Getter;

@Getter
public enum TransactionType {

    CREDIT,
    DEBIT;

    public TransactionType flip() {
        return this == CREDIT ? DEBIT : CREDIT;
    }

}
