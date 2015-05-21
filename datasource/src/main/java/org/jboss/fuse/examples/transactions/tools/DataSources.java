/**
 *  Copyright 2005-2015 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.jboss.fuse.examples.transactions.tools;

import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;

public class DataSources {

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(DataSources.class.getResourceAsStream("/jdbc.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static DataSource findDataSource(String db) throws Exception {
        switch (db) {
            case "postgresql":
                return postgresql();
        }
        return null;
    }

    private static DataSource postgresql() throws Exception {
        Class<?> dsClass = Class.forName("org.postgresql.ds.PGSimpleDataSource");
        DataSource ds = (DataSource) dsClass.newInstance();
        set(ds, "url", "jdbc.postgresql.url");
        set(ds, "user", "jdbc.postgresql.user");
        set(ds, "password", "jdbc.postgresql.password");

        return ds;
    }

    private static void set(DataSource ds, String property, String key) throws Exception {
        String method = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
        ds.getClass().getMethod(method, String.class).invoke(ds, properties.getProperty(key));
    }

}
