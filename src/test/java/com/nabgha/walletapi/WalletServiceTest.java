package com.nabgha.walletapi;


import com.nabgha.walletapi.entity.Wallet;
import com.nabgha.walletapi.exception.InsufficientBalanceException;
import com.nabgha.walletapi.service.WalletService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WalletServiceTest {
    private final WalletService walletService = new WalletService();

    @Test
    void should_withdraw_amount_when_balance_is_sufficient() {
        // Arrange
        Wallet wallet = Wallet.builder().id(1L).balance(100.0).build();
        
        // Act
        walletService.withdraw(wallet, 40.0);
        
        // Assert
        assertEquals(60.0, wallet.getBalance());
    }

    @Test
    void should_throw_exception_when_balance_is_insufficient() {
        // Arrange
        Wallet wallet = Wallet.builder().id(2L).balance(50.0).build();
        
        // Act & Assert
        assertThrows(InsufficientBalanceException.class, () -> {
            walletService.withdraw(wallet, 100);
        });
    }

    @Test
    void should_deposit_amount() {
        // Arrange
        Wallet wallet = Wallet.builder().id(3L).balance(50.0).build();
        
        // Act
        walletService.deposit(wallet, 50.0);
        
        // Assert
        assertEquals(100.0, wallet.getBalance());
    }
}
