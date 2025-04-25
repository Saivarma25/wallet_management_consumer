package in.pinnacle.apps.wallet.api.service;

import in.pinnacle.apps.wallet.api.model.Wallet;
import in.pinnacle.apps.wallet.api.model.WalletSync;
import in.pinnacle.apps.wallet.api.repository.WalletRepository;
import in.pinnacle.apps.wallet.api.repository.WalletSyncRepository;
import in.pinnacle.apps.wallet.api.util.dto.WalletDTO;
import in.pinnacle.apps.wallet.api.util.dto.WalletSyncDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    private final WalletSyncRepository walletSyncRepository;

    private final RedisTemplate<String, BigDecimal> redisTemplate;

    private static final String WALLET = "wallet:";

    /**
     * Method to update wallet balance in DB, which will be called by a virtual thread
     * @param wallet existing WalletMaster Object to update
     * @param amountToAdd Amount that needs to be added to the given WalletMaster object
     */
    public void updateWalletMaster(Wallet wallet, BigDecimal amountToAdd, LocalDateTime modifiedAt) {
        wallet.setBalance(wallet.getBalance().add(amountToAdd));
        wallet.setModifiedAt(modifiedAt);
        walletRepository.save(wallet);
    }

    /**
     * Method to create a wallet with given details which are from a producer
     * @param walletDTO DTO that contains all the details for wallet creation
     */
    public void createWallet(@NotNull WalletDTO walletDTO) {
        Wallet wallet = walletRepository.save(new Wallet(null, walletDTO.getUserId(),
                walletDTO.getCurrency(), walletDTO.getWalletType(), new BigDecimal(0L),
                true, walletDTO.getCreatedAt(), walletDTO.getCreatedAt()));

        // create wallet in redis
        redisTemplate.opsForValue().set(WALLET + wallet.getWalletId(), wallet.getBalance());
    }

    /**
     * Method to sync wallets with balances
     * @param walletSyncDTO DTO to save into wallet sync table
     */
    public void doWalletSync(WalletSyncDTO walletSyncDTO) {

        WalletSync walletSync = walletSyncRepository.findByWalletId(walletSyncDTO.getWalletId());
        if (walletSync == null)
            walletSync = new WalletSync(walletSyncDTO.getWalletId(), walletSyncDTO.getBalance());
        else
            walletSync.setBalance(walletSyncDTO.getBalance());

        walletSyncRepository.save(walletSync);
    }

}