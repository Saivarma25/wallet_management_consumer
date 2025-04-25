package in.pinnacle.apps.wallet.api.util.dto;


import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class WalletSyncDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -4519432224605676145L;

    private Long walletId;

    private BigDecimal balance;

}
