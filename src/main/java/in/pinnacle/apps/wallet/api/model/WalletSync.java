package in.pinnacle.apps.wallet.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "wallet_sync")
public class WalletSync implements Serializable {

    @Serial
    private static final long serialVersionUID = -2786923827366108115L;

    @Id
    private Long walletId;

    private BigDecimal balance;

}
