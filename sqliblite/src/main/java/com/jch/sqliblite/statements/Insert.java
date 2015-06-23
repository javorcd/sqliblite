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

import com.jch.sqliblite.IInsert;
import com.jch.sqliblite.util.Util;

/**
 * This class concatenates the sentences to construct new statement to insert values in
 * the database
 *
 * @author javier
 */
public class Insert implements IInsert {

    private static final StringBuilder query = new StringBuilder();

    /**
     * Constructor
     */
    public Insert() {

    }

    @Override
    public IInsert insert(Object table) {
        query.append("INSERT INTO ").append(table);
        return this;
    }

    @Override
    public IInsert values(Object... values) {
        query.append(" VALUES (");
        Util.appendArgument(query, values);
        query.append(")");
        return this;
    }

    @Override
    public IInsert populate(Object q) {
        query.append(" ").append(q);
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
