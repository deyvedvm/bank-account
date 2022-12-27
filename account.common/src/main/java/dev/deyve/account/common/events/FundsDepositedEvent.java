package dev.deyve.account.common.events;

import dev.deyve.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FundsDepositedEvent extends BaseEvent {
    private double amount;
}
