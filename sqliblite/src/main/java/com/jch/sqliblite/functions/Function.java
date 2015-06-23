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
 * Aggregation functions
 *
 * @author javier
 */
public class Function {

    /**
     * @param colum
     * @return
     */
    public static String avg(Object colum) {
        return (String.format("AVG(%s)", colum));
    }

    /**
     * @return
     */
    public static String count() {
        return ("COUNT(*)");
    }

    /**
     * @param colum
     * @return
     */
    public static String count(Object colum) {
        return (String.format("COUNT(%s)", colum));
    }

    /**
     * @param value
     * @return
     */
    public static String groupConcat(Object value) {
        return groupConcat(value, ",");
    }

    /**
     * @param value
     * @param separator
     * @return
     */
    public static String groupConcat(Object value, Object separator) {
        return (String.format("GROUP_CONCAT(%s, '%s')", value, separator));
    }

    /**
     * @param value
     * @return
     */
    public static String max(Object value) {
        return (String.format("MAX(%s)", value));
    }

    /**
     * @param value
     * @return
     */
    public static String min(Object value) {
        return (String.format("MIN(%s)", value));
    }

    /**
     * @param value
     * @return
     */
    public static String sum(Object value) {
        return (String.format("SUM(%s)", value));
    }

    /**
     * @param value
     * @return
     */
    public static String total(Object value) {
        return (String.format("TOTAL(%s)", value));
    }
}
