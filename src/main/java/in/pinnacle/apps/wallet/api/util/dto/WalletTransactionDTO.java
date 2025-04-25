package in.pinnacle.apps.wallet.api.util.dto;

import in.pinnacle.apps.wallet.api.util.TransactionType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WalletTransactionDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2012725756555899488L;

    /**
     * ID of the wallet for which this transaction belongs to
     */
    private Long walletId;

    /**
     * ID of the user for which this wallet and transaction belongs to
     */
    private Long userId;

    /**
     * Amount of the transaction requested
     */
    private BigDecimal transactionAmount;

    /**
     * Total amount of wallet after adding transaction amount
     */
    private BigDecimal walletAmount;

    /**
     * Type of the transaction requested
     */
    private TransactionType transactionType;

    /**
     * Details about the transaction requested
     */
    private String description;

    private LocalDateTime createdAt;

}
