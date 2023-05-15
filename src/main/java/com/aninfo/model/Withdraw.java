package com.aninfo.model;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;

import javax.persistence.Entity;

@Entity
public class Withdraw extends Transaction {
    public Withdraw(Account account, Double sum) {
        if (account.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.cbu = account.getCbu();
        this.amount = sum;
    }

    public Withdraw() {

    }

    @Override
    public void affectBalance(Account account) {
        Double currentBalance = account.getBalance();
        account.setBalance(currentBalance - this.getAmount());
    }

    @Override
    public void revertOperation(Account account) {
        Double currentBalance = account.getBalance();
        account.setBalance(currentBalance + this.getAmount());
    }
}
