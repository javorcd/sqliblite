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
import com.jch.sqliblite.functions.Value;
import com.jch.sqliblite.statements.Update;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by javier on 21/06/15.
 */
public class UpdateTest {

    @Test
    public void testUpdateSimple() {
        String expected = "UPDATE clientes SET nombre = 'javier'";
        String sql = new Update().update("clientes").set(new Value("nombre", "javier")).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testUpdateComplex_1() {
        String expected = "UPDATE clientes SET nombre = 'javier' WHERE id = 1";
        String sql = new Update().update("clientes").set(new Value("nombre", "javier"))
                .where("id", Comparison.equal(1)).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testUpdateComplex_2() {
        String expected = "UPDATE clientes SET nombre = 'javier' WHERE id = 1 AND saldo > 100";
        String sql = new Update().update("clientes").set(new Value("nombre", "javier"))
                .where("id", Comparison.equal(1)).and("saldo", Comparison.greater(100)).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testUpdateComplex_3() {
        String expected = "UPDATE clientes SET saldo = 1000 WHERE nombre = 'javier' AND presupuesto BETWEEN 500 AND 1500";
        String sql = new Update().update("clientes").set(new Value("saldo", 1000))
                .where("nombre", Comparison.equal("javier")).and("presupuesto").between(500, 1500)
                .builStatement();
        Assert.assertEquals(expected, sql);
    }
}
