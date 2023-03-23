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
package swt.group.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt.group.uni.StudiumData;
import swt.group.uni.StudiumDataHelper;

/**
 *
 * @author hvo
 */
public class StudiumRepository {

    final private static Logger LOG = LoggerFactory.getLogger(PersonRepository.class);

    public static ArrayList<StudiumData> retrieveStudiumData(int personId) {
        ArrayList<StudiumData> studiumDataList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM studiendaten WHERE person_id = ?");) {
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();

            studiumDataList = StudiumDataHelper.listFromDb(rs);
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return studiumDataList;
    }

    public static int insertStudiumData(StudiumData studiumData) {
        int rowCount = 0;
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT INTO studiendaten (studiendaten_id, fach, inhalt, semester, person_id) VALUES (NULL, ?, ?, ?, ?);");) {

            StudiumDataHelper.fillPreparedStatement(ps, studiumData);
            rowCount = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return rowCount;
    }
    
            public static int updateStudiumData(StudiumData studiumData) {
        int rowCount = 0;
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("UPDATE studiendaten SET fach = ?, inhalt = ?, semester = ?, person_id = ? WHERE studiendaten_id = ? ");) {
            
            StudiumDataHelper.fillPreparedStatement(ps, studiumData);
            ps.setInt(5, Integer.parseInt(studiumData.id));
            rowCount = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return rowCount;
    }

        public static int deleteStudiumData(int id) {
            int rowCount = 0;
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("DELETE FROM studiendaten WHERE studiendaten_id = ?");) {
            ps.setInt(1, id);
            
            rowCount = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return rowCount;
    
    }
}
