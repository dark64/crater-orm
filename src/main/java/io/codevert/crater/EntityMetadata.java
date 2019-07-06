package io.codevert.crater;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityMetadata {

    private String tableName;
    private Map<Field, Column> fieldColumnMap;

    public EntityMetadata(Class<?> clazz) {
        this.tableName = getTableName(clazz);
        this.fieldColumnMap = getFieldColumnMap(clazz);
    }

    private Map<Field, Column> getFieldColumnMap(Class<?> clazz) {
        Map<Field, Column> map = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                map.put(field, column);
            }
        }
        return map;
    }

    private String getTableName(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null) {
            throw new IllegalStateException("Entity missing @Table annotation");
        }
        return table.name();
    }

    public String getTableName() {
        return tableName;
    }

    public Map<Field, Column> getFieldColumnMap() {
        return fieldColumnMap;
    }
}
