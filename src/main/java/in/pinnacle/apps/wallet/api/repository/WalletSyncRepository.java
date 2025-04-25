package in.pinnacle.apps.wallet.api.repository;

import in.pinnacle.apps.wallet.api.model.WalletSync;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletSyncRepository extends JpaRepository<WalletSync, Long> {

    WalletSync findByWalletId(Long walletId);

}
