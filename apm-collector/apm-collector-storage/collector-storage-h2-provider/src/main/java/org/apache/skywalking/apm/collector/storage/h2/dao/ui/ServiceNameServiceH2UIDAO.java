/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.collector.storage.h2.dao.ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.skywalking.apm.collector.client.h2.H2Client;
import org.apache.skywalking.apm.collector.client.h2.H2ClientException;
import org.apache.skywalking.apm.collector.storage.base.sql.SqlBuilder;
import org.apache.skywalking.apm.collector.storage.dao.ui.IServiceNameServiceUIDAO;
import org.apache.skywalking.apm.collector.storage.h2.base.dao.H2DAO;
import org.apache.skywalking.apm.collector.storage.table.register.ServiceNameTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author peng-yongsheng
 */
public class ServiceNameServiceH2UIDAO extends H2DAO implements IServiceNameServiceUIDAO {

    private final Logger logger = LoggerFactory.getLogger(ServiceNameServiceH2UIDAO.class);

    public ServiceNameServiceH2UIDAO(H2Client client) {
        super(client);
    }

    @Override public int getCount() {
        String dynamicSql = "select count({0}) as cnt from {1}";
        String sql = SqlBuilder.buildSql(dynamicSql, ServiceNameTable.COLUMN_SERVICE_ID, ServiceNameTable.TABLE);
        Object[] params = new Object[] {};

        try (ResultSet rs = getClient().executeQuery(sql, params)) {
            if (rs.next()) {
                return rs.getInt("cnt");
            }
        } catch (SQLException | H2ClientException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }
}
