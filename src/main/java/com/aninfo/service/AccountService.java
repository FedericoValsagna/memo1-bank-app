package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Deposit;
import com.aninfo.model.Transaction;
import com.aninfo.model.Withdraw;
import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);
        Transaction transaction = new Withdraw(account, sum);
        transaction.affectBalance(account);
        transactionRepository.save(transaction);
        accountRepository.save(account);
        return account;
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);
        Transaction transaction = new Deposit(account, sum);
        transaction.affectBalance(account);
        transactionRepository.save(transaction);
        accountRepository.save(account);
        return account;
    }

    public Collection<Transaction> getTransactions(Long cbu) {
        Collection<Transaction> accountTransactions= new ArrayList<Transaction>();
        Iterator<Transaction> transactions  = transactionRepository.findAll().iterator();
        while(transactions.hasNext()) {
            Transaction transaction = transactions.next();
            if (transaction.getCbu() == cbu){
                accountTransactions.add(transaction);
            }
        }
        return accountTransactions;
    }

    public Optional<Transaction> getTransaction(Long transactionNumber){
        return transactionRepository.findById(transactionNumber);
    }



    public void deleteTransactionById(Long transactionNumber) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionNumber);
        if (transaction.isPresent()) {
            Account account = accountRepository.findAccountByCbu(transaction.get().getCbu());
            transaction.get().revertOperation(account);
            transactionRepository.deleteById(transactionNumber);
        }
    }
}
