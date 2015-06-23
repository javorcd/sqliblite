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

import com.jch.sqliblite.IDelete;
import com.jch.sqliblite.functions.AbstractOperations;
import com.jch.sqliblite.util.Util;

/**
 * This class concatenates the sentences to construct new statement to delete values in
 * the database
 *
 * @author javier
 */
public class Delete extends AbstractOperations implements IDelete {

    private static final StringBuilder query = new StringBuilder();

    /**
     * Constructor
     */
    public Delete() {
        super(query);
    }

    @Override
    public IDelete delete(Object table) {
        query.append("DELETE FROM ").append(table);
        return this;
    }

    @Override
    public IDelete where(Object... condition) {
        query.append(" WHERE");
        Util.appendCondition(query, condition);
        return this;
    }

    @Override
    public IDelete and(Object... condition) {
        andOperation(condition);
        return this;
    }

    @Override
    public IDelete between(Object val1, Object val2) {
        betweenOperation(val1, val2);
        return this;
    }

    @Override
    public IDelete exists(Object subquery) {
        existsOperation(subquery);
        return this;
    }

    @Override
    public IDelete in(Object... values) {
        inOperation(values);
        return this;
    }

    @Override
    public IDelete like(Object value) {
        likeOperation(value);
        return this;
    }

    @Override
    public IDelete glob(Object value) {
        globOperation(value);
        return this;
    }

    @Override
    public IDelete not() {
        notOperation();
        return this;
    }

    @Override
    public IDelete or(Object condition) {
        orOperation(condition);
        return this;
    }

    @Override
    public IDelete isNot(Object value) {
        isNotOperation(value);
        return this;
    }

    @Override
    public IDelete isNull() {
        isNullOperation();
        return this;
    }

    @Override
    public IDelete isNotNull() {
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
