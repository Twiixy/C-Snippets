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
public class PersonDataHelper {

    public static ArrayList<PersonData> listFromDb(ResultSet rs) throws SQLException {
        ArrayList<PersonData> personDataList = new ArrayList<>();
        while (rs.next()) {
            int person_id = rs.getInt("person_id");
            String vorname = rs.getString("vorname");
            String nachname = rs.getString("nachname");
            String betrieb = rs.getString("betrieb");
            String email = rs.getString("email");
            String passwort = rs.getString("passwort");
            int rolleId = rs.getInt("rolle_id");
            PersonData personData = new PersonData("" + person_id, vorname, nachname, betrieb, email, Rolle.of(rolleId).getName(), passwort);
            personDataList.add(personData);
        }
        return personDataList;
    }
}
