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
package org.jboss.fuse.examples.transactions.routes;

import java.util.Random;

/**
 * Helper class to generate random airport codes out of 50 of the busiest airports in the world.
 */
public class Airports {

    private static final String[] AIRPORTS =
            new String[] { "ATL", "PEK", "LHR", "ORD", "HND", "LAX", "CDG", "DFW", "FRA", "HKG",
                    "DEN", "CGK", "DXB", "AMS", "MAD", "BKK", "JFK", "SIN", "CAN", "LAS",
                    "PVG", "SFO", "PHX", "IAH", "CLT", "MIA", "MUC", "KUL", "FCO", "IST",
                    "SYD", "MCO", "ICN", "DEL", "BCN", "LGW", "EWR", "YYZ", "SHA", "MSP",
                    "SEA", "DTW", "PHL", "BOM", "GRU", "MNL", "CTU", "BOS", "SZX", "MEL" };

    private static final Random RANDOM = new Random();

    /**
     * Generate a random airport code.
     */
    public static final String randomAirport() {
        return AIRPORTS[RANDOM.nextInt(AIRPORTS.length)];
    }

}
