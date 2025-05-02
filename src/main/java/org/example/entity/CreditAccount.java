package org.example.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CREDIT")
public class CreditAccount extends BankAccount{

    @Override
    public String getAccountType(){
        return "CREDIT";
    }

}
