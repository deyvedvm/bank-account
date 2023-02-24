package dev.deyve.account.cmd.infrastruture;

import dev.deyve.account.cmd.domain.AccountAggregate;
import dev.deyve.account.cmd.domain.EventStoreRepository;
import dev.deyve.cqrs.core.events.BaseEvent;
import dev.deyve.cqrs.core.events.EventModel;
import dev.deyve.cqrs.core.exceptions.AggregateNotFoundException;
import dev.deyve.cqrs.core.exceptions.ConcurrentException;
import dev.deyve.cqrs.core.infrastructure.EventStore;
import dev.deyve.cqrs.core.producers.EventProducer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountEventStore implements EventStore {

    private final EventProducer eventProducer;

    private final EventStoreRepository eventStoreRepository;

    public AccountEventStore(EventStoreRepository eventStoreRepository, EventProducer eventProducer) {
        this.eventStoreRepository = eventStoreRepository;
        this.eventProducer = eventProducer;
    }

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {

        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrentException();
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                eventProducer.produce(event.getClass().getSimpleName(), event);

            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {

        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account Id provided");
        }
        return eventStream.stream().map(EventModel::getEventData).toList();
    }
}
