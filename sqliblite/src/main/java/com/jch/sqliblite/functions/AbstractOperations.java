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

package com.jch.sqliblite.functions;

import com.jch.sqliblite.util.Util;

/**
 * Common operations to classes {@link com.jch.sqliblite.statements.Update},
 * {@link com.jch.sqliblite.statements.Select}, and {@link com.jch.sqliblite.statements.Delete}
 *
 * @author javier
 */
public abstract class AbstractOperations {

    private final StringBuilder query;

    /**
     * Constructor
     *
     * @param q {@link StringBuilder} to concatenate the operations
     */
    protected AbstractOperations(StringBuilder q) {
        query = q;
    }

    /**
     * Concatenates the AND operation to the query.
     * <p/>
     * This method can not be overriden
     *
     * @param condition
     */
    protected final void andOperation(Object... condition) {
        query.append(" AND");
        Util.appendCondition(query, condition);
    }

    /**
     * Concatenates the BETWEEN operation to the query.
     * <p/>
     * This method can not be overriden
     *
     * @param val1
     * @param val2
     */
    protected final void betweenOperation(Object val1, Object val2) {
        query.append(" BETWEEN ").append(val1).append(" AND ").append(val2);
    }

    /**
     * Concatenates the EXISTS operation to the query.
     * <p/>
     * This method can not be overriden
     *
     * @param subquery
     */
    protected final void existsOperation(Object subquery) {
        query.append(" EXISTS (").append(subquery).append(")");
    }

    /**
     * Concatenates the IN operation to the query.
     * <p/>
     * This method can not be overriden
     *
     * @param values
     */
    protected final void inOperation(Object... values) {
        query.append(" IN (");
        Util.appendUpdateArgument(query, values);
        query.append(")");
    }

    /**
     * Concatenates the LIKE operation to the query.
     * <p/>
     * This method can not be overriden
     *
     * @param value
     */
    protected final void likeOperation(Object value) {
        query.append(" LIKE ");
        Util.escapeString(query, value);
    }

    /**
     * Concatenates the GLOB operation to the query.
     * <p/>
     * This method can not be overriden
     *
     * @param value
     */
    protected final void globOperation(Object value) {
        query.append(" GLOB ");
        Util.escapeString(query, value);
    }

    /**
     * Concatenates the NOT operation to the query.
     * <p/>
     * This method can not be overriden
     *
     */
    protected final void notOperation() {
        query.append(" NOT ");
    }

    /**
     * Concatenates the OR operation to the query.
     * <p/>
     * This method can not be overriden
     *
     * @param condition
     */
    protected final void orOperation(Object condition) {
        query.append(" OR ").append(condition);
    }

    /**
     * Concatenates the IS NOT operation to the query.
     * <p/>
     * This method can not be overriden
     *
     * @param value
     */
    protected final void isNotOperation(Object value) {
        query.append(" IS NOT ").append(value);
    }

    /**
     * Concatenates the IS NULL operation to the query.
     * <p/>
     * This method can not be overriden
     */
    protected final void isNullOperation() {
        query.append(" IS NULL");
    }

    /**
     * Concatenates the IS NOT NULL operation to the query.
     * <p/>
     * This method can not be overriden
     */
    protected final void isNotNullOperation() {
        query.append(" IS NOT NULL");
    }
}
