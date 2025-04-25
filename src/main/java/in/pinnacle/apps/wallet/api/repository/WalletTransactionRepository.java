package in.pinnacle.apps.wallet.api.repository;

import in.pinnacle.apps.wallet.api.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

}
