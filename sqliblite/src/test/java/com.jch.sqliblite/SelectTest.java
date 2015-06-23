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
import com.jch.sqliblite.functions.Function;
import com.jch.sqliblite.statements.Select;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by javier on 21/06/15.
 */
public class SelectTest {

    @Test
    public void testSelectSimple() {
        String expected = "SELECT * FROM clientes";
        String sql = new Select().select().from("clientes").builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectSimpleLike() {
        String expected = "SELECT * FROM clientes WHERE nombre LIKE 'ja%'";
        String sql = new Select().select().from("clientes").where("nombre").like("ja%").builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectSimpleLimit() {
        String expected = "SELECT * FROM clientes LIMIT 10";
        String sql = new Select().select().from("clientes").limit(10).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectSimpleLimitOffset() {
        String expected = "SELECT * FROM clientes LIMIT 10 OFFSET 2";
        String sql = new Select().select().from("clientes").limit(10).offset(2).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectSimpleWhere() {
        String expected = "SELECT * FROM clientes WHERE saldo >= 1000";
        String sql = new Select().select().from("clientes").where("saldo",
                Comparison.greaterOrEqual(1000)).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectComplex_1() {
        String expected = "SELECT * FROM clientes WHERE id IN (SELECT id FROM pedidos WHERE cliente_id = 10)";
        // child query
        String in = new Select().select("id").from("pedidos").where("cliente_id", Comparison.equal(10)).builStatement();
        // master query
        String sql = new Select().select().from("clientes").where("id").in(in).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectComplex_2() {
        String expected = "SELECT DISTINCT nombre FROM clientes WHERE EXISTS (SELECT id FROM pedidos WHERE cliente_id = 10)";
        // child query
        String exists = new Select().select("id").from("pedidos").where("cliente_id", Comparison.equal(10)).builStatement();
        // master query
        String sql = new Select().selectDistinct("nombre").from("clientes").where().exists(exists).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectCrossJoin() {
        String expected = "SELECT * FROM clientes CROSS JOIN pedidos";
        String sql = new Select().select().from("clientes").crossJoin("pedidos").builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectInnerJoinOn() {
        String expected = "SELECT * FROM clientes INNER JOIN pedidos ON cliente_id = 1234";
        String sql = new Select().select().from("clientes").innerJoin("pedidos")
                .on("cliente_id", Comparison.equal(1234)).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectInnerJoinUsing() {
        String expected = "SELECT * FROM clientes INNER JOIN pedidos USING (cliente_id)";
        String sql = new Select().select().from("clientes").innerJoin("pedidos")
                .using("cliente_id").builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectOuterJoinUsing() {
        String expected = "SELECT * FROM clientes OUTER JOIN pedidos USING (cliente_id)";
        String sql = new Select().select().from("clientes").outerJoin("pedidos")
                .using("cliente_id").builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectOuterJoinOn() {
        String expected = "SELECT * FROM clientes OUTER JOIN pedidos ON cliente_id = 1234";
        String sql = new Select().select().from("clientes").outerJoin("pedidos")
                .on("cliente_id", Comparison.equal(1234)).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectLeftOuterJoinOn() {
        String expected = "SELECT * FROM clientes LEFT OUTER JOIN pedidos ON cliente_id = 1234";
        String sql = new Select().select().from("clientes").left().outerJoin("pedidos")
                .on("cliente_id", Comparison.equal(1234)).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectLeftOuterJoinUsing() {
        String expected = "SELECT * FROM clientes LEFT OUTER JOIN pedidos USING (cliente_id)";
        String sql = new Select().select().from("clientes").left().outerJoin("pedidos")
                .using("cliente_id").builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectGroupBy() {
        String expected = "SELECT * FROM clientes WHERE saldo <= 1000 GROUP BY nombre";
        String sql = new Select().select().from("clientes").where("saldo", Comparison.lessOrEqual(1000))
                .groupBy("nombre").builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectGroupByHaving() {
        String expected = "SELECT * FROM clientes WHERE saldo <= 1000 GROUP BY nombre HAVING COUNT(nombre) < 2";
        String sql = new Select().select().from("clientes").where("saldo", Comparison.lessOrEqual(1000))
                .groupBy("nombre").having(Function.count("nombre"), Comparison.less(2)).builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectGroupByOrderByAsc() {
        String expected = "SELECT * FROM clientes WHERE saldo <= 1000 GROUP BY nombre ORDER BY saldo ASC";
        String sql = new Select().select().from("clientes").where("saldo", Comparison.lessOrEqual(1000))
                .groupBy("nombre").orderBy("saldo").asc().builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectGroupByOrderByDesc() {
        String expected = "SELECT * FROM clientes WHERE saldo <= 1000 GROUP BY nombre ORDER BY saldo DESC";
        String sql = new Select().select().from("clientes").where("saldo", Comparison.lessOrEqual(1000))
                .groupBy("nombre").orderBy("saldo").desc().builStatement();
        Assert.assertEquals(expected, sql);
    }

    @Test
    public void testSelectSumGroupByOrderByDesc() {
        String expected = "SELECT nombre, SUM(saldo) FROM clientes WHERE saldo <= 1000 GROUP BY nombre ORDER BY saldo DESC";
        String sql = new Select().select("nombre", Function.sum("saldo")).from("clientes").where("saldo", Comparison.lessOrEqual(1000))
                .groupBy("nombre").orderBy("saldo").desc().builStatement();
        Assert.assertEquals(expected, sql);
    }
}
