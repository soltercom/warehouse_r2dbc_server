package ru.spb.altercom.warehouse_r2dbc_server.model.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemRepository extends ReactiveSortingRepository<Item, Long> {

    Flux<Item> findByNameStartingWith(@Param("name") String name, Pageable page);

    Mono<Long> countByNameStartingWith(@Param("name") String name);

}
