package in.pinnacle.apps.wallet.api.service;

import in.pinnacle.apps.wallet.api.util.dto.WalletTransactionDTO;
import in.pinnacle.apps.wallet.api.model.WalletTransaction;
import in.pinnacle.apps.wallet.api.repository.WalletTransactionRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletTransactionService {

    private final WalletTransactionRepository walletTransactionRepository;

    /**
     * Method to save wallet transactions as per the given wallet details
     * @param txnDto DTO that contains wallet and transaction details
     * @param reversal flag to mention the type of transaction (Actual or reversal)
     */
    public void addTransaction(@NotNull WalletTransactionDTO txnDto, boolean reversal) {
        walletTransactionRepository.save(new WalletTransaction(null, txnDto.getWalletId(),
                reversal ? txnDto.getTransactionType().flip().name() : txnDto.getTransactionType().name(),
                txnDto.getTransactionAmount(), (reversal ? "Reversal of " : "") + txnDto.getDescription(),
                txnDto.getCreatedAt()));
    }

}
