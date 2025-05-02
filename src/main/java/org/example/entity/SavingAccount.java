package org.example.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SAVINGS")
public class SavingAccount extends BankAccount{

    @Override
    public String getAccountType(){
        return "SAVINGS";
    }

}
