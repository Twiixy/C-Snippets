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
import swt.group.betrieb.BetriebData;
import swt.group.betrieb.BetriebDataHelper;

/**
 *
 * @author hvo
 */
public class BetriebRepository {

    final private static Logger LOG = LoggerFactory.getLogger(BetriebRepository.class);

    public static ArrayList<BetriebData> retrieveBetriebData(int id) {
        ArrayList<BetriebData> betriebDataList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM ausbildungsdaten WHERE person_id = ?");) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            betriebDataList = BetriebDataHelper.listFromDb(rs);
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return betriebDataList;
    }

    public static int insertBetriebData(BetriebData betriebData) {
        int rowCount = 0;
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT INTO ausbildungsdaten (ausbildungsdaten_id, beschreibung, kommentar, titel, isFinished, person_id) VALUES (NULL, ?, ?, ?, ?, ?);");) {
            
            BetriebDataHelper.fillPreparedStatement(ps, betriebData);
            rowCount = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return rowCount;
    }
    
        public static int updateBetriebData(BetriebData betriebData) {
        int rowCount = 0;
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("UPDATE ausbildungsdaten SET beschreibung = ?, kommentar = ?, titel = ?, isFinished = ?, person_id = ? WHERE ausbildungsdaten_id = ? ");) {
            
            BetriebDataHelper.fillPreparedStatement(ps, betriebData);
            ps.setInt(6, Integer.parseInt(betriebData.id));
            rowCount = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return rowCount;
    }
    
    public static int deleteBetriebData(int id) {
            int rowCount = 0;
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("DELETE FROM ausbildungsdaten WHERE ausbildungsdaten_id = ?");) {
            ps.setInt(1, id);
            
            rowCount = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return rowCount;
    
    }
}
