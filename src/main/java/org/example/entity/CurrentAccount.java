package org.example.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CURRENT")
public class CurrentAccount extends BankAccount{

    @Override
    public String getAccountType(){
        return "CURRENT";
    }
}
