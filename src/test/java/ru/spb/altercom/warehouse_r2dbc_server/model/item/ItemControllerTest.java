package ru.spb.altercom.warehouse_r2dbc_server.model.item;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.spb.altercom.warehouse_r2dbc_server.common.TableData;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ItemControllerTest {

    private static WebTestClient testClient;

    @BeforeAll
    static void init() {
        ItemService itemService = Mockito.mock(ItemService.class);
        when(itemService.getNew()).thenReturn(getItemMono(null));
        when(itemService.findById(anyLong())).thenReturn(getItemMono(1L));
        when(itemService.save(any(Item.class))).thenReturn(getItemMono(1L));
        when(itemService.getTableData(anyInt(), anyInt(), anyInt(), anyString(), any(Sort.Direction.class)))
            .thenReturn(Mono.just(new TableData<Item>(1, 10, 10, getItemList())));

        testClient = WebTestClient.bindToController(new ItemController(itemService))
                .configureClient()
                .baseUrl("/api/items")
                .build();
    }

    @Test
    @DisplayName("should return Item")
    void findByIdTest() {
        testClient.get().uri("/{id}", 1L)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.name").isEqualTo("Item 1");
    }

    @Test
    @DisplayName("should return new Item")
    void getNewTest() {
        testClient.get().uri("/new")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$.name").isEqualTo("");
    }

    @Test
    @DisplayName("should save new and return Item")
    void saveNewTest() {
        testClient.post().uri("/new")
            .body(getItemMono(null), Item.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$").isNotEmpty()
            .jsonPath("$.id").isEqualTo(1L)
            .jsonPath("$.name").isEqualTo("Item 1");
    }

    @Test
    @DisplayName("should save existed and return Item")
    void saveTest() {
        testClient.post().uri("/1")
                .body(getItemMono(1L), Item.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isNotEmpty()
                .jsonPath("$.id").isEqualTo(1L)
                .jsonPath("$.name").isEqualTo("Item 1");
    }

    @Test
    @DisplayName("should return a TableData")
    void getTableDataTest() {
        testClient.get().uri("/table?draw=1&start=1&length=10&search[value]=&order[0][dir]=ASC")
            .exchange()
            .expectStatus().isOk().expectBody()
            .jsonPath("$").isNotEmpty()
            .jsonPath("$.draw").isEqualTo(1)
            .jsonPath("$.recordsTotal").isEqualTo(10)
            .jsonPath("$.data[0].id").isEqualTo(1L)
            .jsonPath("$.data[9].id").isEqualTo(10L)
            .jsonPath("$.data[0].name").isEqualTo("Item 1")
            .jsonPath("$.data[9].name").isEqualTo("Item 10");
    }

    private static List<Item> getItemList() {
        return List.of(
           testItem(1L), testItem(2L), testItem(3L),
           testItem(4L), testItem(5L), testItem(6L),
           testItem(7L), testItem(8L), testItem(9L),
           testItem(10L)
        );
    }

    private static Mono<Item> getItemMono(Long id) {
        return Mono.just(testItem(id));
    }

    private static Item testItem(Long id) {
        if (id == null) {
            return Item.empty();
        } else {
            return Item.builder()
                    .id(id)
                    .name("Item " + id)
                    .build();
        }
    }

}
