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
import swt.group.InfoseiteData;
import swt.group.InfoseiteDataHelper;

/**
 *
 * @author hvo
 */
public class InfoseiteRepository {

    final private static Logger LOG = LoggerFactory.getLogger(JDBCConnection.class);

    public static ArrayList<InfoseiteData> retrieveAll() {
        ArrayList<InfoseiteData> infoseiteDataList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * FROM infoseite");) {
            ResultSet rs = ps.executeQuery();

            infoseiteDataList=InfoseiteDataHelper.listFromDb(rs);
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return infoseiteDataList;
    }

    public static int insertInfoseiteData(InfoseiteData infoseiteData) {
        int rowCount = 0;
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT INTO infoseite (inhalt_id, inhalt) VALUES (NULL, ?);");) {

            ps.setString(1, infoseiteData.inhalt);
            rowCount = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return rowCount;
    }

    public static int updateInfoseiteData(InfoseiteData infoseiteData) {
        int rowCount = 0;
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("UPDATE infoseite SET inhalt = ? WHERE inhalt_id = ? ");) {

            ps.setString(1, infoseiteData.inhalt);
            ps.setInt(2, Integer.parseInt(infoseiteData.id));
            rowCount = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return rowCount;
    }

    public static int deleteInfoseiteData(int id) {
        int rowCount = 0;
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("DELETE FROM infoseite WHERE inhalt_id = ?");) {
            ps.setInt(1, id);

            rowCount = ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return rowCount;

    }
}
