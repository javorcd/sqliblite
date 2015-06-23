/*
 * Copyright 2015 Javier Casanova Hernandez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jch.sqliblite.statements;

import com.jch.sqliblite.ISelect;
import com.jch.sqliblite.functions.AbstractOperations;
import com.jch.sqliblite.util.Util;

/**
 * This class concatenates the sentences to construct new statement to retrieve values in
 * the database
 *
 * @author javier
 */
public class Select extends AbstractOperations implements ISelect {

    private static final StringBuilder query = new StringBuilder();

    /**
     * Constructor
     */
    public Select() {
        super(query);
    }

    @Override
    public ISelect select() {
        query.append("SELECT *");
        return this;
    }

    @Override
    public ISelect select(Object... columns) {
        query.append("SELECT ");
        Util.appendUpdateArgument(query, columns);
        return this;
    }

    @Override
    public ISelect selectDistinct(Object... columns) {
        query.append("SELECT DISTINCT ");
        Util.appendUpdateArgument(query, columns);
        return this;
    }

    @Override
    public ISelect from(Object... tables) {
        query.append(" FROM ");
        Util.appendUpdateArgument(query, tables);
        return this;
    }

    @Override
    public ISelect limit(Object value) {
        query.append(" LIMIT ").append(value);
        return this;
    }

    @Override
    public ISelect offset(Object value) {
        query.append(" OFFSET ").append(value);
        return this;
    }

    @Override
    public ISelect where(Object... condition) {
        query.append(" WHERE");
        Util.appendCondition(query, condition);
        return this;
    }

    @Override
    public ISelect orderBy(Object... columns) {
        query.append(" ORDER BY ");
        Util.appendUpdateArgument(query, columns);
        return this;
    }

    @Override
    public ISelect asc() {
        query.append(" ASC");
        return this;
    }

    @Override
    public ISelect desc() {
        query.append(" DESC");
        return this;
    }

    @Override
    public ISelect groupBy(Object... columns) {
        query.append(" GROUP BY ");
        Util.appendUpdateArgument(query, columns);
        return this;
    }

    @Override
    public ISelect having(Object... condition) {
        query.append(" HAVING");
        Util.appendCondition(query, condition);
        return this;
    }

    @Override
    public ISelect crossJoin(Object table) {
        query.append(" CROSS JOIN ").append(table);
        return this;
    }

    @Override
    public ISelect innerJoin(Object table) {
        query.append(" INNER JOIN ").append(table);
        return this;
    }

    @Override
    public ISelect outerJoin(Object table) {
        query.append(" OUTER JOIN ").append(table);
        return this;
    }

    @Override
    public ISelect naturalJoin(Object table) {
        query.append(" NATURAL JOIN ").append(table);
        return this;
    }

    @Override
    public ISelect left() {
        query.append(" LEFT");
        return this;
    }

    @Override
    public ISelect union(Object q) {
        query.append(" UNION ").append(q);
        return this;
    }

    @Override
    public ISelect unionAll(Object q) {
        query.append(" UNION ALL ").append(q);
        return this;
    }

    @Override
    public ISelect using(Object... columns) {
        query.append(" USING (");
        Util.appendUpdateArgument(query, columns);
        query.append(")");
        return this;
    }

    @Override
    public ISelect on(Object... condition) {
        query.append(" ON");
        Util.appendCondition(query, condition);
        return this;
    }

    @Override
    public ISelect and(Object... condition) {
        andOperation(condition);
        return this;
    }

    @Override
    public ISelect between(Object val1, Object val2) {
        betweenOperation(val1, val2);
        return this;
    }

    @Override
    public ISelect exists(Object subquery) {
        existsOperation(subquery);
        return this;
    }

    @Override
    public ISelect in(Object... values) {
        inOperation(values);
        return this;
    }

    @Override
    public ISelect like(Object value) {
        likeOperation(value);
        return this;
    }

    @Override
    public ISelect glob(Object value) {
        globOperation(value);
        return this;
    }

    @Override
    public ISelect not() {
        notOperation();
        return this;
    }

    @Override
    public ISelect or(Object condition) {
        orOperation(condition);
        return this;
    }

    @Override
    public ISelect isNot(Object value) {
        isNotOperation(value);
        return this;
    }

    @Override
    public ISelect isNull() {
        isNullOperation();
        return this;
    }

    @Override
    public ISelect isNotNull() {
        isNotNullOperation();
        return this;
    }

    @Override
    public String builStatement() {
        query.trimToSize();
        String result = query.toString();
        query.delete(0, result.length());
        return result;
    }
}
