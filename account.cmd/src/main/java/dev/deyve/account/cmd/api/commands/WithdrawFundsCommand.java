package dev.deyve.account.cmd.api.commands;

import dev.deyve.cqrs.core.commands.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;
}
