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

package com.jch.sqliblite.util;

/**
 * Utils to concatenate arguments and conditions in the queries
 *
 * @author javier
 */
public class Util {

    /**
     *
     * @param sb
     * @param values
     */
    public static void appendArgument(StringBuilder sb, Object... values) {
        for (int cont = 0; cont < values.length; cont++) {
            if (cont > 0) {
                sb.append(", ");
            }
            if(values[cont] instanceof String) {
                escapeString(sb, values[cont]);
            } else {
                sb.append(values[cont]);
            }
        }
    }

    /**
     *
     * @param sb
     * @param values
     */
    public static void appendUpdateArgument(StringBuilder sb, Object... values) {
        for (int cont = 0; cont < values.length; cont++) {
            if (cont > 0) {
                sb.append(", ");
            }
            sb.append(values[cont]);
        }
    }

    /**
     *
     * @param sb
     * @param conditions
     */
    public static void appendCondition(StringBuilder sb, Object... conditions) {
        for (Object obj : conditions) {
            sb.append(" ").append(obj);
        }
    }

    /**
     *
     * @param sb
     * @param value
     */
    public static void escapeString(StringBuilder sb, Object value) {
        String val = (String) value;
        sb.append("\'");
        if (val.contains("\'")) {
            for (int i = 0; i < val.length(); i++) {
                char ch = val.charAt(i);
                if (ch == '\'') {
                    sb.append('\'');
                }
                sb.append(ch);
            }
        } else {
            sb.append(val);
        }
        sb.append("\'");
        sb.trimToSize();
    }
}
