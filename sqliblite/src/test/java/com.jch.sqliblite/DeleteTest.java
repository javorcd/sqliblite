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

import com.jch.sqliblite.functions.Comparison;
import com.jch.sqliblite.statements.Delete;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by javier on 21/06/15.
 */
public class DeleteTest {

    @Test
    public void testDeleteSimple() {
        String expected = "DELETE FROM clientes";
        String sql = new Delete().delete("clientes").builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testDeleteSimpleWhere() {
        String expected = "DELETE FROM clientes WHERE id = 10";
        String sql = new Delete().delete("clientes").where("id", Comparison.equal(10))
                .builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testDeleteComplex_1() {
        String expected = "DELETE FROM clientes WHERE nombre = 'javier' AND saldo < 15";
        String sql = new Delete().delete("clientes").where("nombre", Comparison.equal("javier"))
                .and("saldo", Comparison.less(15)).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testDeleteComplex_2() {
        String expected = "DELETE FROM clientes WHERE direccion IS NULL";
        String sql = new Delete().delete("clientes").where("direccion").isNull().builStatement();
        Assert.assertEquals(expected, sql);
    }
}
