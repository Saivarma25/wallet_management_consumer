package in.pinnacle.apps.wallet.api.repository;

import in.pinnacle.apps.wallet.api.model.Wallet;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Wallet findByWalletId(@NotNull Long walletId);

}
