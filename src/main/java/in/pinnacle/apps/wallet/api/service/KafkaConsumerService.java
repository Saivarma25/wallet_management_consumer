package in.pinnacle.apps.wallet.api.service;

import in.pinnacle.apps.wallet.api.repository.WalletRepository;
import in.pinnacle.apps.wallet.api.util.dto.WalletDTO;
import in.pinnacle.apps.wallet.api.util.dto.WalletSyncDTO;
import in.pinnacle.apps.wallet.api.util.dto.WalletTransactionDTO;
import in.pinnacle.apps.wallet.api.model.Wallet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final WalletService walletService;

    private final WalletRepository walletRepository;

    private final WalletTransactionService walletTransactionService;

    private static final String WALLET_TRANSACTION = "wallet_transaction";

    private static final String WALLET = "wallet";

    private static final String WALLET_SYNC = "wallet_sync";

    @KafkaListener(
            topics = WALLET_TRANSACTION,
            containerFactory = "walletTransactionKafkaListenerContainerFactory")
    @Transactional
    public void processWalletTransactionMessage(WalletTransactionDTO txnDto, Acknowledgment acknowledgment) {
        try {
            Wallet wallet = walletRepository.findByWalletId(txnDto.getWalletId());
            walletTransactionService.addTransaction(txnDto, false);
            if (txnDto.getWalletAmount().signum() < 0)
                walletTransactionService.addTransaction(txnDto, true);
            else
                walletService.updateWalletMaster(wallet, txnDto.getTransactionAmount(), txnDto.getCreatedAt());
            // Acknowledge message
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Exception while consuming Wallet Transaction", e);
        }
    }

    @KafkaListener(
            topics = WALLET,
            containerFactory = "walletKafkaListenerContainerFactory")
    public void processWalletMessage(ConsumerRecord<Long, Object> consumerRecord, Acknowledgment acknowledgment) {
        try {
            walletService.createWallet((WalletDTO) consumerRecord.value());
            // Acknowledge message
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Exception while consuming Wallet", e);
        }
    }

    @KafkaListener(
            topics = WALLET_SYNC,
            containerFactory = "walletKafkaListenerContainerFactory")
    public void processWalletSyncMessage(ConsumerRecord<Long, Object> consumerRecord, Acknowledgment acknowledgment) {
        try {
            walletService.doWalletSync((WalletSyncDTO) consumerRecord.value());
            // Acknowledge message
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Exception while consuming Wallet Sync", e);
        }
    }

}
