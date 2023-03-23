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
package swt.group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import swt.group.jdbc.Rolle;

/**
 *
 * @author hvo
 */
public class InfoseiteDataHelper {

    public static ArrayList<InfoseiteData> listFromDb(ResultSet rs) throws SQLException {
        ArrayList<InfoseiteData> infoseiteDataList = new ArrayList<>();
        while (rs.next()) {
            int inhalt_id = rs.getInt("inhalt_id");
            String inhalt = rs.getString("inhalt");
            InfoseiteData infoseiteData = new InfoseiteData("" + inhalt_id, inhalt);
            infoseiteDataList.add(infoseiteData);
        }
        return infoseiteDataList;
    }
}
