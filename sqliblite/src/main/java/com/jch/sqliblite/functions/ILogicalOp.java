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


import com.jch.sqliblite.IStatement;

/**
 * Generic operations
 *
 * @param <T>
 * @author javier
 */
public interface ILogicalOp<T extends IStatement> {

    /**
     * @param condition
     * @return
     */
    T and(Object... condition);

    /**
     * @param val1
     * @param val2
     * @return
     */
    T between(Object val1, Object val2);

    /**
     * @param subquery
     * @return
     */
    T exists(Object subquery);

    /**
     * @param values
     * @return
     */
    T in(Object... values);

    /**
     * @param value
     * @return
     */
    T like(Object value);

    /**
     * @param value
     * @return
     */
    T glob(Object value);

    /**
     * @return
     */
    T not();

    /**
     * @param condition
     * @return
     */
    T or(Object condition);

    /**
     * @param value
     * @return
     */
    T isNot(Object value);

    /**
     * @return
     */
    T isNull();

    /**
     * @return
     */
    T isNotNull();

}
