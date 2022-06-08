package ru.spb.altercom.warehouse_r2dbc_server.model.item;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.spb.altercom.warehouse_r2dbc_server.common.TableData;

@RestController
@RequestMapping(path="/api/items", produces="application/json")
@CrossOrigin(origins="*")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/new")
    public Mono<Item> getNew() {
        return itemService.getNew();
    }

    @GetMapping("/{id}")
    public Mono<Item> findById(@PathVariable("id") Long id) {
        return itemService.findById(id);
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Item> saveNew(@RequestBody Item item) {
        return itemService.save(item);
    }

    @PostMapping("/{id}")
    public Mono<Item> save(@RequestBody Item item) {
        return itemService.save(item);
    }

    @GetMapping("/table")
    public Mono<TableData<Item>> getTableData(@RequestParam int draw,
                                    @RequestParam int start,
                                    @RequestParam int length,
                                    @RequestParam(name = "search[value]", defaultValue = "") String search,
                                    @RequestParam(name = "order[0][dir]") Sort.Direction dir) {
        return itemService.getTableData(draw, start, length, search, dir);
    }
}
