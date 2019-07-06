package io.codevert.crater.builder;

import io.codevert.crater.EntityMetadata;
import io.codevert.crater.db.Query;

import javax.persistence.Column;

public class StandardSQLBuilder implements SQLBuilder {

    @Override
    public String createSelectStatement(Query query) {
        StringBuilder stringBuilder = new StringBuilder();
        EntityMetadata metadata = query.getEntityMetadata();
        String tableName = metadata.getTableName();
        String[] columnNames = metadata.getFieldColumnMap()
                .values()
                .stream()
                .map(Column::name)
                .toArray(String[]::new);

        stringBuilder.append("select ");
        stringBuilder.append(String.join(", ", columnNames));
        stringBuilder.append(" from ");
        stringBuilder.append(tableName);

        String whereClause = query.getWhereClause();
        if (whereClause != null) {
            stringBuilder.append(" ");
            stringBuilder.append(query.getWhereClause());
        }

        return stringBuilder.toString();
    }
}
