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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jch.sqliblite.data.exception.DatabaseCreationException;

/**
 * This class abstracts the creation and initialization of a database.
 *
 * @author javier
 */
public abstract class AbstractDatabase {

    private final Context context;
    private SQLiteOpenHelper helper;
    private SQLiteDatabase database;

    /**
     * Constructor
     *
     * @param ctx Application context
     */
    public AbstractDatabase(Context ctx) {
        context = ctx.getApplicationContext();
    }

    /**
     * Database name. Used to construct {@link com.jch.sqliblite.data.AbstractDatabase.DatabaseHelper}
     *
     * @return database name
     */
    public abstract String getName();

    /**
     * Version de la base de datos. Usado para construir el {@link com.jch.sqliblite.data.AbstractDatabase.DatabaseHelper}
     *
     * @return Entero que representa la version de la base de datos
     */
    public abstract int getVersion();

    /**
     * Used to construct {@link com.jch.sqliblite.data.AbstractDatabase.DatabaseHelper}.
     * <p/>
     * To understand the operation check {@link SQLiteOpenHelper#onCreate(SQLiteDatabase)}.
     *
     * @param database {@link SQLiteDatabase}
     */
    protected abstract void executeCreate(SQLiteDatabase database);

    /**
     * Used to construct {@link com.jch.sqliblite.data.AbstractDatabase.DatabaseHelper}.
     * <p/>
     * To understand the operation check {@link SQLiteOpenHelper#onUpgrade(SQLiteDatabase, int, int)}.
     * <p/>
     * To update the tables are cleared and loses all information
     *
     * @param database {@link SQLiteDatabase}
     * @param oldVer database old version
     * @param newVer database new version
     */
    protected abstract void executeUpgrade(SQLiteDatabase database, int oldVer, int newVer);

    /**
     * Return {@link SQLiteDatabase} to run operations.
     * <p/>
     * This method can not be overriden
     *
     * @return {@link SQLiteDatabase}.
     */
    protected synchronized final SQLiteDatabase openDatabase() {
        if (database == null) {
            open();
        }
        return database;
    }

    /**
     * Close {@link SQLiteDatabase} if is open.
     * <p/>
     * This method can not be overriden
     */
    protected synchronized final void closeDatabase() {
        if (database.isOpen()) {
            database.close();
            helper.close();
            database = null;
            helper = null;
        }
    }

    /**
     * Initialize {@link SQLiteDatabase}
     */
    private void open() {
        initializeHelper();
        try {
            database = helper.getWritableDatabase();
        } catch (DatabaseCreationException e) {
            logError(e.getMessage(), e);
        }
    }

    /**
     * Initialize {@link com.jch.sqliblite.data.AbstractDatabase.DatabaseHelper}
     */
    private void initializeHelper() {
        if (helper == null) {
            helper = new DatabaseHelper(context, getName(), null, getVersion());
        }
    }

    /**
     * Print error message in the log
     *
     * @param msg mensage
     * @param error error
     */
    private void logError(String msg, Throwable error) {
        Log.e(getClass().getSimpleName(), msg, error);
    }

    /**
     * Subclass of {@link SQLiteOpenHelper}
     */
    private final class DatabaseHelper extends SQLiteOpenHelper {

        /**
         * Constructor
         *
         * @param context Application context
         * @param name Database name
         * @param factory {@link android.database.sqlite.SQLiteDatabase.CursorFactory}
         * @param version Database version number
         */
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqld) {
            executeCreate(sqld);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqld, int oldVersion, int newVersion) {
            Log.w(AbstractDatabase.class.getSimpleName(), String.format(
                "Upgrading database from %s to %s, which will destroy all data", oldVersion, newVersion));
            executeUpgrade(database, oldVersion, newVersion);
            onCreate(sqld);
        }
    }
}
