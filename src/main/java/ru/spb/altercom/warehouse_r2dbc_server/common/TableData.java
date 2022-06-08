package ru.spb.altercom.warehouse_r2dbc_server.common;

import java.util.List;

public record TableData<T>(int draw, long recordsTotal, long recordsFiltered, List<T> data) {
}
