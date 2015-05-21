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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbInsert {

    public static Logger LOG = LoggerFactory.getLogger(DbInsert.class);

    public static String[] NON_FATAL_SQLSTATES = {
            "42Y07", // Derby: Schema 'schema' does not exist (SQLState: 42Y07)
            "42Y55", // Derby: 'DROP TABLE' cannot be performed on 'schema.table' because it does not exist. (SQLState: 42Y55)
            "42704", // DB2: SQL Error: SQLCODE=-204, SQLSTATE=42704 'name' is an undefined name.
            "42000", // Oracle: ORA-00942: table or view does not exist
    };

    public static void main(String[] args) throws Exception {
        String db = (args == null || args.length == 0) ? "postgresql" : args[0];
        Set<String> nonFatalSqlStates = new HashSet<>(Arrays.asList(NON_FATAL_SQLSTATES));

        DataSource ds = DataSources.findDataSource(db);

        if (ds == null) {
            LOG.warn("No datasource configuration found for \"" + db + "\" database type");
            return;
        }

        try (Connection con = ds.getConnection()) {
            try (Statement st = con.createStatement()) {
                String[] scripts = readScripts(db, "initdb.sql");
                try {
                    for (String script : scripts) {
                        try {
                            st.execute(script);
                        } catch (SQLException e) {
                            // for databases which don't support "IF EXISTS" clause
                            if (!nonFatalSqlStates.contains(e.getSQLState())) {
                                throw e;
                            }
                        }
                    }
                    LOG.info("Database " + db + " initialized successfully");
                } catch (SQLException e) {
                    LOG.error(e.getMessage() + " (SQLState: " + e.getSQLState() + ")");
                }
            }
        }
    }

    private static String[] readScripts(String db, String script) throws IOException {
        File file = new File("src/main/sql/" + db + "/" + script);
        byte[] bytes = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(bytes);
        }
        StringWriter sw = new StringWriter();
        List<String> scripts = new LinkedList<>();
        char[] chars = new String(bytes, "UTF-8").toCharArray();
        int pos = 0;
        STATE st = STATE.DEFAULT;
        while (pos < chars.length) {
            char c = chars[pos++];
            switch (st) {
                case DEFAULT: {
                    if (c == '-' && (pos < chars.length && chars[pos] == '-')) {
                        st = STATE.COMMENT;
                    } else if (c == '\'') {
                        sw.append(c);
                        st = STATE.STRING_LITERAL;
                    } else if (c == ';') {
                        scripts.add(sw.toString());
                        sw = new StringWriter();
                    } else {
                        sw.append(c);
                    }
                    break;
                }
                case COMMENT: {
                    if (c == '\n') {
                        st = STATE.DEFAULT;
                    }
                    break;
                }
                case STRING_LITERAL: {
                    sw.append(c);
                    if (c == '\'') {
                        st = STATE.DEFAULT;
                    }
                    break;
                }
            }
        }
        return scripts.toArray(new String[scripts.size()]);
    }

    private enum STATE {
        DEFAULT, COMMENT, STRING_LITERAL
    }

}
