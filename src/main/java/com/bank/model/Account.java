package com.bank.model;

import com.bank.enums.AccountStatus;
import com.bank.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class Account {

    private UUID accountId;
    @NotNull
    @Positive
    private BigDecimal balance;
    @NotNull
    private AccountType accountType;
    private Date creationDate;
    @NotNull
    private Long userId;
    private AccountStatus accountStatus;


}
