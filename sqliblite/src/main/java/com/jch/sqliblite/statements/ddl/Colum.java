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

/**
 * Represent a column of a table
 *
 * Created by javier
 */
public class Colum {

    /**
     * Create {@link String} with the column name and data type
     *
     * @param columnName column name
     * @param dataType data type of the column
     * @return {@link String} with the column name and type
     */
    public static String value(String columnName, String dataType) {
        return String.format("%s %s", columnName, dataType);
    }

    /**
     * Create {@link String} with the column name, data type and constraint
     *
     * @param columnName column name
     * @param dataType data type of the column
     * @param constraint constraint
     * @return {@link String} with the column name, type and constraint
     */
    public static String value(String columnName, String dataType, String constraint) {
        return String.format("%s %s %s", columnName, dataType, constraint);
    }
}
