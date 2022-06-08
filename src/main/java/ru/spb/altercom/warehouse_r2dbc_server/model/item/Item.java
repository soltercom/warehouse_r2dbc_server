package ru.spb.altercom.warehouse_r2dbc_server.model.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("ITEMS")
public class Item {

    @Id
    private Long id;

    private String name;

    public static Item empty() {
        return new Item(null, "");
    }

}
