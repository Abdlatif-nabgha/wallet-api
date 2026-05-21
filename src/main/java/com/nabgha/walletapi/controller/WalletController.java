package com.nabgha.walletapi.controller;

import com.nabgha.walletapi.entity.Wallet;
import com.nabgha.walletapi.exception.InsufficientBalanceException;
import com.nabgha.walletapi.repository.WalletRepository;
import com.nabgha.walletapi.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final WalletRepository walletRepository;

    @PostMapping
    public Wallet createWallet(@RequestParam double initialBalance){
        Wallet wallet = Wallet.builder()
                .balance(initialBalance)
                .build();
        walletRepository.save(wallet);
        return wallet;
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawAmount(@PathVariable Long id, @RequestParam double amount) {

        try {
            Wallet wallet = walletRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Wallet not found"));

            walletService.withdraw(wallet, amount);
            walletRepository.save(wallet);

            return ResponseEntity.ok("Retrait de " + amount + " effectué avec succès. Nouveau solde : " + wallet.getBalance());
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> depositAmount(@PathVariable Long id, @RequestParam double amount) {
        try {
            Wallet wallet = walletRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Wallet not found"));

            walletService.deposit(wallet, amount);
            walletRepository.save(wallet);

            return ResponseEntity.ok("Dépôt de " + amount + " effectué avec succès. Nouveau solde : " + wallet.getBalance());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
