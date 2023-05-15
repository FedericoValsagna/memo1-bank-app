package com.aninfo.model;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;

import javax.persistence.*;

@Entity
// @Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long transactionNumber;

    protected Long cbu;
    protected Double amount;
    public Double getAmount() {
        return amount;
    }

    public Long getCbu() {
        return cbu;
    }

    public Long getTransactionNumber() {
        return transactionNumber;
    }

    public abstract void affectBalance(Account account);

    public abstract void revertOperation(Account account);

}
