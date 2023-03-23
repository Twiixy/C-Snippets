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
import swt.group.PersonData;
import swt.group.PersonDataHelper;

/**
 *
 * @author hvo
 */
public class PersonRepository {

    final private static Logger LOG = LoggerFactory.getLogger(PersonRepository.class);

    public static ArrayList<PersonData> retrievePersonData(int personId) {
        ArrayList<PersonData> personDataList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * from person WHERE person_id = ?");) {
            ps.setInt(1, personId);
            ResultSet rs = ps.executeQuery();

            personDataList = PersonDataHelper.listFromDb(rs);

        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return personDataList;
    }

    public static ArrayList<PersonData> retrievePersonDataWithUsername(String username) {
        ArrayList<PersonData> personDataList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT * from person WHERE email = ?");) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            personDataList = PersonDataHelper.listFromDb(rs);
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return personDataList;
    }

    public static String retrievePasswordForUser(String username) {
        String retrievedPassword = null;
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT passwort FROM person WHERE email = ?");) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                retrievedPassword = rs.getString("passwort");
            }
        } catch (SQLException e) {
            LOG.error("A databse error ocurred: ", e);
        }
        return retrievedPassword;
    }

    public static ArrayList<PersonData> retrieveAllAusbilderOfAzubi(int azubiId) {
        ArrayList<PersonData> personDataList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT p.person_id, p.vorname, p.nachname, p.betrieb, p.email, p.rolle_id, p.passwort FROM betreut b JOIN person p ON b.ausbilder_id = p.person_id WHERE b.azubi_id = ?");) {
            ps.setInt(1, azubiId);
            ResultSet rs = ps.executeQuery();

            personDataList = PersonDataHelper.listFromDb(rs);
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return personDataList;
    }

    public static ArrayList<PersonData> retrieveAllAzubisOfAusbilder(int ausbilderId) {
        ArrayList<PersonData> personDataList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT p.person_id, p.vorname, p.nachname, p.betrieb, p.email, p.rolle_id, p.passwort FROM betreut b JOIN person p ON b.azubi_id = p.person_id WHERE b.ausbilder_id = ?");) {
            ps.setInt(1, ausbilderId);
            ResultSet rs = ps.executeQuery();

            personDataList = PersonDataHelper.listFromDb(rs);
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return personDataList;
    }

    public static ArrayList<PersonData> retrieveAllAzubisWithoutAusbilder() {
        ArrayList<PersonData> personDataList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT  p.person_id, p.vorname, p.nachname, p.betrieb, p.email, p.rolle_id, p.passwort from person p LEFT JOIN betreut b ON p.person_id=b.azubi_id WHERE p.rolle_id=2 AND b.ausbilder_id IS NULL");) {
            ResultSet rs = ps.executeQuery();

            personDataList = PersonDataHelper.listFromDb(rs);
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return personDataList;
    }

    public static void deletePerson(int id) {
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("DELETE FROM person WHERE person_id = ?");) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
    }

    public static void addPerson(PersonData personData) {
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT INTO person(person_id, vorname, nachname, betrieb, email, rolle_id, passwort) VALUES (NULL, ?, ?, ?, ?, ?,?);");) {
            ps.setString(1, personData.vorname);
            ps.setString(2, personData.nachname);
            ps.setString(3, personData.betrieb);
            ps.setString(4, personData.email);
            ps.setInt(5, Integer.parseInt(personData.rolle));
            ps.setString(6, personData.passwort);
            ps.execute();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
    }

    public static void updatePerson(PersonData personData) {
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("UPDATE person SET vorname = ?, nachname = ?, betrieb = ?, email = ?, passwort = ? WHERE person_id = ? ");) {
            ps.setString(1, personData.vorname);
            ps.setString(2, personData.nachname);
            ps.setString(3, personData.betrieb);
            ps.setString(4, personData.email);
            ps.setString(5, personData.passwort);
            ps.setInt(6, Integer.parseInt(personData.id));
            ps.executeUpdate();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
    }

    public static ArrayList<PersonData> retrieveAllAusbilder() {
        ArrayList<PersonData> ausbilderList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT  p.person_id, p.vorname, p.nachname, p.betrieb, p.email, p.rolle_id, p.passwort from person p WHERE p.rolle_id = 1");) {
            ResultSet rs = ps.executeQuery();
            ausbilderList = PersonDataHelper.listFromDb(rs);
            ps.execute();

        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return ausbilderList;
    }

    public static ArrayList<PersonData> retrieveAllStudents() {
        ArrayList<PersonData> studentList = new ArrayList<>();
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("SELECT  p.person_id, p.vorname, p.nachname, p.betrieb, p.email, p.rolle_id, p.passwort from person p WHERE p.rolle_id = 2");) {
            ResultSet rs = ps.executeQuery();
            studentList = PersonDataHelper.listFromDb(rs);
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
        return studentList;
    }

    public static void deleteAllRelationenOfBetreuer(int id) {
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("DELETE FROM betreut WHERE ausbilder_id = ?");) {
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
    }

    public static void addAusbilderAndAzubi(int ausbilderId, int azubiId) {
        try (Connection connection = JDBCConnection.getConnection(); PreparedStatement ps = connection.prepareStatement("INSERT INTO betreut(ausbilder_id, azubi_id) VALUES (?, ?);");) {
            ps.setInt(1, ausbilderId);
            ps.setInt(2, azubiId);
            ps.execute();
        } catch (SQLException e) {
            LOG.error("A database error ocurred: ", e);
        }
    }
}
