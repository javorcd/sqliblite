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

/**
 * Represent the values pased in {@link com.jch.sqliblite.statements.Update} statement
 *
 * Created by javier
 */
public class Value {

    private final String column;
    private final Object value;

    /**
     * Constructor
     *
     * @param column column name
     * @param value new value. If type is {@link String} it escapes
     */
    public Value(String column, Object value) {
        this.column = column;
        this.value = value;
    }

    @Override
    public String toString() {
        if(value instanceof String) return String.format("%s = '%s'", column, value);
        return String.format("%s = %s", column, value);
    }
}
