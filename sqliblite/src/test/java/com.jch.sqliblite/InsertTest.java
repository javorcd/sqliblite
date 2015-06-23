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

import com.jch.sqliblite.statements.Insert;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Created by javier on 21/06/15.
 */
public class InsertTest {

    @Test
    public void testInsertSimpleQuery() {
        String expected = "INSERT INTO clientes VALUES (1, 'javier')";
        String sql = new Insert().insert("clientes").values(1, "javier").builStatement();
        Assert.assertEquals(expected, sql);
    }
}
