package org.example.utils;

import org.example.entity.BankAccount;
import org.example.entity.CreditAccount;
import org.example.entity.CurrentAccount;
import org.example.entity.SavingAccount;

public class AccountFactory {

    public static BankAccount createAccount(String type){

        return switch(type.toUpperCase()) {
            case "SAVINGS" -> new SavingAccount();
            case "CURRENT" -> new CurrentAccount();
            case "CREDIT" -> new CreditAccount();
            default -> throw new IllegalStateException("Invalid account type: " + type.toUpperCase());
        };

    }
}
