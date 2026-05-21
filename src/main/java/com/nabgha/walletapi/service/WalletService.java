package com.nabgha.walletapi.service;

import com.nabgha.walletapi.entity.Wallet;
import com.nabgha.walletapi.exception.InsufficientBalanceException;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    public void withdraw(Wallet wallet, double amount) {
        if (amount > wallet.getBalance()) {
            throw new InsufficientBalanceException("Solde insuffisant pour ce retrait.");
        }
        wallet.setBalance(wallet.getBalance()-amount);
    }

    public void deposit(Wallet wallet, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Le montant du dépôt doit être positif.");
        }
        wallet.setBalance(wallet.getBalance() + amount);
    }
}
