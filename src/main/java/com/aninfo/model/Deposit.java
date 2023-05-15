package com.aninfo.model;

import com.aninfo.exceptions.DepositNegativeSumException;

import javax.persistence.Entity;

@Entity
public class Deposit extends Transaction{


    public Deposit(Account account, Double sum) {
        if (sum <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }
        if (sum >= 2000) {
            sum = applyPromotion(sum);
        }
        this.cbu = account.getCbu();
        this.amount = sum;
    }

    public Deposit() {

    }

    private Double applyPromotion(Double sum) {
        double extra = sum / 10;
        if (extra > 500) {
            extra = 500;
        }
        return sum + extra;
    }

    @Override
    public void affectBalance(Account account) {
        Double currentBalance = account.getBalance();
        account.setBalance(currentBalance + this.getAmount());
    }

    @Override
    public void revertOperation(Account account) {
        Double currentBalance = account.getBalance();
        account.setBalance(currentBalance - this.getAmount());
    }
}
