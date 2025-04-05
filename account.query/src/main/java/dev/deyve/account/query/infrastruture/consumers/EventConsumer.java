package dev.deyve.account.query.infrastruture.consumers;

import dev.deyve.account.common.events.AccountClosedEvent;
import dev.deyve.account.common.events.AccountOpenedEvent;
import dev.deyve.account.common.events.FundsDepositedEvent;
import dev.deyve.account.common.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {

    void consume(@Payload AccountOpenedEvent event, Acknowledgment acknowledgment);

    void consume(@Payload FundsDepositedEvent event, Acknowledgment acknowledgment);

    void consume(@Payload FundsWithdrawnEvent event, Acknowledgment acknowledgment);

    void consume(@Payload AccountClosedEvent event, Acknowledgment acknowledgment);

}
