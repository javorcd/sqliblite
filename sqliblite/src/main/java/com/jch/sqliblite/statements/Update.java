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

import com.jch.sqliblite.IUpdate;
import com.jch.sqliblite.functions.AbstractOperations;
import com.jch.sqliblite.functions.Value;
import com.jch.sqliblite.util.Util;

/**
 * This class concatenates the sentences to construct new statement to update values in
 * the database
 *
 * @author javier
 */
public class Update extends AbstractOperations implements IUpdate {

    private static final StringBuilder query = new StringBuilder();

    /**
     * Constructors
     */
    public Update() {
        super(query);
    }

    @Override
    public IUpdate update(Object table) {
        query.append("UPDATE ").append(table);
        return this;
    }

    @Override
    public IUpdate set(Value... values) {
        query.append(" SET ");
        Util.appendUpdateArgument(query, values);
        return this;
    }

    @Override
    public IUpdate where(Object... condition) {
        query.append(" WHERE");
        Util.appendCondition(query, condition);
        return this;
    }

    @Override
    public IUpdate and(Object... condition) {
        andOperation(condition);
        return this;
    }

    @Override
    public IUpdate between(Object val1, Object val2) {
        betweenOperation(val1, val2);
        return this;
    }

    @Override
    public IUpdate exists(Object subquery) {
        existsOperation(subquery);
        return this;
    }

    @Override
    public IUpdate in(Object... values) {
        inOperation(values);
        return this;
    }

    @Override
    public IUpdate like(Object value) {
        likeOperation(value);
        return this;
    }

    @Override
    public IUpdate glob(Object value) {
        globOperation(value);
        return this;
    }

    @Override
    public IUpdate not() {
        notOperation();
        return this;
    }

    @Override
    public IUpdate or(Object condition) {
        orOperation(condition);
        return this;
    }

    @Override
    public IUpdate isNot(Object value) {
        isNotOperation(value);
        return this;
    }

    @Override
    public IUpdate isNull() {
        isNullOperation();
        return this;
    }

    @Override
    public IUpdate isNotNull() {
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
