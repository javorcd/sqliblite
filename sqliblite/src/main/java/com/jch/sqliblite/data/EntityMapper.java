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

/**
 * Convert values from {@link Cursor} to a type specified
 *
 * @param <T> generic type
 * @author javier
 */
public interface EntityMapper<T> {

    /**
     * Mapped a cursor to obtain an instance of an object type T
     *
     * @param cursor {@link Cursor} values returned from database
     * @return tipo
     */
    T mapEntity(Cursor cursor);

}
