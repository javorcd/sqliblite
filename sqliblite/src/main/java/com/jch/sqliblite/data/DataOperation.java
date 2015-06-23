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

package com.jch.sqliblite.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * This class offers methods to perform operations against a database
 *
 * @author javier
 */
public final class DataOperation {

    private final AbstractDatabase database;

    /**
     * Constructor
     *
     * @param db subclass of {@link AbstractDatabase}
     */
    public DataOperation(AbstractDatabase db) {
        database = db;
    }

    /**
     * Run a query using {@link com.jch.sqliblite.statements.Insert} statement
     *
     * @param statement Query
     * @return row ID
     */
    public long insert(String statement) {
        try {
            return database.openDatabase().compileStatement(statement).executeInsert();
        } finally {
            database.closeDatabase();
        }
    }

    /**
     * Run a query using {@link com.jch.sqliblite.statements.Update} statement
     *
     * @param statement query
     * @return number of rows updated
     */
    public int update(String statement) {
        try {
            return database.openDatabase().compileStatement(statement).executeUpdateDelete();
        } finally {
            database.closeDatabase();
        }
    }

    /**
     * Run a query using {@link com.jch.sqliblite.statements.Delete} statement
     *
     * @param statement query
     * @return number of rows deleted
     */
    public int delete(String statement) {
        try {
            return database.openDatabase().compileStatement(statement).executeUpdateDelete();
        } finally {
            database.closeDatabase();
        }
    }

    /**
     * Run a query using  {@link com.jch.sqliblite.statements.Select} statement
     * <p/>
     * Uses {@link EntityMapper} to obtain valid {@link List} with the elements from a cursor.
     *
     * @param statement query
     * @param mapper {@link EntityMapper} to reconstruct entities
     * @param <T> Generic type
     * @return {@link List} with the values returned from database
     */
    public <T> List<T> get(String statement, EntityMapper<T> mapper) {
        Cursor cursor = null;
        List<T> entities = new ArrayList<>();
        try {
            cursor = database.openDatabase().rawQuery(statement, null);
            if(cursor.moveToFirst()) {
                do {
                    entities.add(mapper.mapEntity(cursor));
                } while (cursor.moveToNext());
            }
            return entities;
        } finally {
            if(cursor != null) cursor.close();
            database.closeDatabase();
        }
    }
}
