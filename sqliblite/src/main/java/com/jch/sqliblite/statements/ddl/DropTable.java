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
 * Statement to drop one table if exists
 *
 * Created by javier
 */
public class DropTable {

    /**
     * Create new {@link String} with the value passed
     *
     * @param table table name
     * @return {@link String}
     */
    public static String drop(String table) {
        return String.format("DROP TABLE IF EXISTS %s", table);
    }
}
