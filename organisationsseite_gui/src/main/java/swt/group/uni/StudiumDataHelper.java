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
package swt.group.uni;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author hvo
 */
public class StudiumDataHelper {

    public static ArrayList<StudiumData> listFromDb(ResultSet rs) throws SQLException {
        ArrayList<StudiumData> studiumDataList = new ArrayList<>();
        while (rs.next()) {
            int studiendaten_id = rs.getInt("studiendaten_id");
            String fach = rs.getString("fach");
            String inhalt = rs.getString("inhalt");
            int semester = rs.getInt("semester");
            int person_id = rs.getInt("person_id");
            StudiumData studentData = new StudiumData("" + studiendaten_id, "" + person_id, semester, fach, inhalt);
            studiumDataList.add(studentData);
        }
        return studiumDataList;
    }

    public static void fillPreparedStatement(PreparedStatement ps, StudiumData studiumData) throws SQLException {
        ps.setString(1, studiumData.fach);
        ps.setString(2, studiumData.inhalt);
        ps.setInt(3, studiumData.semester);
        ps.setString(4, studiumData.personID);
    }

}
