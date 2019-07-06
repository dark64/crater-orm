package io.codevert.crater.builder;

import io.codevert.crater.db.Query;

public interface SQLBuilder {

    String createSelectStatement(Query query);
}
