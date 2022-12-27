package dev.deyve.account.cmd.api.commands;

import dev.deyve.account.common.dtos.AccountType;
import dev.deyve.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {

    private String accountHolder;
    private AccountType accountType;
    private double openingBalance;
}
