package dev.deyve.account.query.infrastruture.handlers;

import dev.deyve.account.common.events.AccountClosedEvent;
import dev.deyve.account.common.events.AccountOpenedEvent;
import dev.deyve.account.common.events.FundsDepositedEvent;
import dev.deyve.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);

    void on(FundsDepositedEvent event);

    void on(FundsWithdrawnEvent event);

    void on(AccountClosedEvent event);
}
