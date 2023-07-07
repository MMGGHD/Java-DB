package model;

// DB에서 Transation한 데이터를 담아놓기 위한 클래스

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
public class Account {
    private int accountNumber;
    private String accountPassword;
    private int accountBalance;
    private Timestamp accountCreatedAt;

    @Builder
    public Account(int accountNumber,String accountPassword, int accountBalance, Timestamp accountCreatedAt){
        this.accountNumber = accountNumber;
        this.accountPassword = accountPassword;
        this.accountBalance = accountBalance;
        this.accountCreatedAt = accountCreatedAt;

    }
}
