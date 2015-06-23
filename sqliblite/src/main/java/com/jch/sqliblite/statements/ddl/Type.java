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
 * Data types.
 * <p/>
 * Read https://www.sqlite.org/datatype3.html for more info
 */
public final class Type {

    /**
     * The value is a signed integer, stored in 1, 2, 3, 4, 6, or 8 bytes depending
     * on the magnitude of the value.
     */
    public static final String INTEGER = "INTEGER";

    /**
     *
     */
    public static final String VARCHAR = "VARCHAR";

    /**
     *
     */
    public static final String DATE = "DATE";

    /**
     * The value is a floating point value, stored as an 8-byte IEEE floating point number.
     */
    public static final String REAL = "REAL";

    /**
     * The value is a text string, stored using the database encoding (UTF-8, UTF-16BE or UTF-16LE).
     */
    public static final String TEXT = "TEXT";

    /**
     * The value is a blob of data, stored exactly as it was input.
     */
    public static final String BLOB = "BLOB";

}
