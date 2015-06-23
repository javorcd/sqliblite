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

package com.jch.sqliblite.statements.ddl;

import com.jch.sqliblite.IStatement;
import com.jch.sqliblite.util.Util;

/**
 * Statement to create new table string
 *
 * Created by javier
 */
public class CreateTable implements IStatement {

    private final StringBuilder query;

    /**
     * Constructor
     */
    private CreateTable() {
        this.query = new StringBuilder();
    }

    /**
     * Initialize this with the given name
     *
     * @param name table name
     * @return this object with the table name appended to it
     */
    public static CreateTable name(String name) {
        CreateTable createTable = new CreateTable();
        createTable.query.append("CREATE TABLE ");
        createTable.query.append(name);
        return createTable;
    }

    /**
     * Concatenates the columns names
     *
     * @param colums
     * @return
     */
    public CreateTable colums(String... colums) {
        query.append("(");
        Util.appendUpdateArgument(query, colums);
        query.append(");");
        return this;
    }

    @Override
    public String builStatement() {
        query.trimToSize();
        return query.toString();
    }
}
