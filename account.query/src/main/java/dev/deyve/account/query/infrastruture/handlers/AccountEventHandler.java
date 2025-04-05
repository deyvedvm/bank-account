package dev.deyve.account.query.infrastruture.handlers;

import dev.deyve.account.common.events.AccountClosedEvent;
import dev.deyve.account.common.events.AccountOpenedEvent;
import dev.deyve.account.common.events.FundsDepositedEvent;
import dev.deyve.account.common.events.FundsWithdrawnEvent;
import dev.deyve.account.query.domain.AccountRepository;
import dev.deyve.account.query.domain.BankAccount;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {

    private final AccountRepository accountRepository;

    public AccountEventHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();

        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        var bankAccount = accountRepository.findById(event.getId());

        if (bankAccount.isEmpty()) {
            return;
        }

        var currenBalance = bankAccount.get().getBalance();
        var latestBalance = currenBalance + event.getAmount();
        bankAccount.get().setBalance(latestBalance);

        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        var bankAccount = accountRepository.findById(event.getId());

        if (bankAccount.isEmpty()) {
            return;
        }

        var currenBalance = bankAccount.get().getBalance();
        var latestBalance = currenBalance - event.getAmount();
        bankAccount.get().setBalance(latestBalance);

        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
