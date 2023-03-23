/*
 * Copyright 2023 hvo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package swt.group.betrieb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hvo
 */
public class BetriebDataHelper {
        public static ArrayList<BetriebData> listFromDb(ResultSet rs) throws SQLException {
        ArrayList<BetriebData> betriebDataList = new ArrayList<>();
        while (rs.next()) {
            int ausbildungsdaten_id = rs.getInt("ausbildungsdaten_id");
            String beschreibung = rs.getString("beschreibung");
            String kommentar = rs.getString("kommentar");
            String titel = rs.getString("titel");
            boolean isFinished = rs.getBoolean("isFinished");
            int person_id = rs.getInt("person_id");
            BetriebData betriebData = new BetriebData("" + ausbildungsdaten_id, "" + person_id, titel, beschreibung, kommentar, isFinished);
            betriebDataList.add(betriebData);
        }
        return betriebDataList;
    }

    public static void fillPreparedStatement(PreparedStatement ps, BetriebData betriebData) throws SQLException {
        ps.setString(1, betriebData.beschreibung);
        ps.setString(2, betriebData.kommentar);
        ps.setString(3, betriebData.titel);
        ps.setBoolean(4, betriebData.istAbgeschlossen);
        ps.setString(5, betriebData.personID);
    }
}
