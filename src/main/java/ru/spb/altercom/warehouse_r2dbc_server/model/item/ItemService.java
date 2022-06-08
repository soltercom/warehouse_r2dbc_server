package ru.spb.altercom.warehouse_r2dbc_server.model.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.spb.altercom.warehouse_r2dbc_server.common.TableData;

@Service
public class ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepo;

    public ItemService(ItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    public Mono<Item> findById(Long id) {
        return itemRepo.findById(id);
    }

    public Mono<Item> getNew() {
        return Mono.just(Item.empty());
    }

    public Mono<Item> save(Item item) {
        return itemRepo.save(item);
    }

    public Mono<TableData<Item>> getTableData(int draw, int page, int size, String search, Sort.Direction dir) {
        var count = itemRepo.countByNameStartingWith(search);
        return itemRepo.findByNameStartingWith(search, PageRequest.of(page / size, size, dir, "name"))
                .collectList()
                .zipWith(count)
                .map(data -> new TableData<Item>(draw, data.getT2(), data.getT2(), data.getT1()));
    }

}
