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

package com.jch.sqliblite;

import com.jch.sqliblite.functions.ILogicalOp;

/**
 * Operations to construct {@link com.jch.sqliblite.statements.Select} statements
 *
 * @author javier
 */
public interface ISelect extends IStatement, ILogicalOp<ISelect> {

    /**
     * @return
     */
    ISelect select();

    /**
     * @param columns
     * @return
     */
    ISelect select(Object... columns);

    /**
     * @param columns
     * @return
     */
    ISelect selectDistinct(Object... columns);

    /**
     * @param tables
     * @return
     */
    ISelect from(Object... tables);

    /**
     * @param value
     * @return
     */
    ISelect limit(Object value);

    /**
     * @param value
     * @return
     */
    ISelect offset(Object value);

    /**
     * @param condition
     * @return
     */
    ISelect where(Object... condition);

    /**
     * @param columns
     * @return
     */
    ISelect orderBy(Object... columns);

    /**
     * @return
     */
    ISelect asc();

    /**
     * @return
     */
    ISelect desc();

    /**
     * @param columns
     * @return
     */
    ISelect groupBy(Object... columns);

    /**
     * @param condition
     * @return
     */
    ISelect having(Object... condition);

    /**
     * @param table
     * @return
     */
    ISelect crossJoin(Object table);

    /**
     * @param table
     * @return
     */
    ISelect innerJoin(Object table);

    /**
     * @param table
     * @return
     */
    ISelect outerJoin(Object table);

    /**
     * @param table
     * @return
     */
    ISelect naturalJoin(Object table);

    /**
     * @return
     */
    ISelect left();

    /**
     * @param query
     * @return
     */
    ISelect union(Object query);

    /**
     * @param query
     * @return
     */
    ISelect unionAll(Object query);

    /**
     * @param columns
     * @return
     */
    ISelect using(Object... columns);

    /**
     * @param condition
     * @return
     */
    ISelect on(Object... condition);

}
