package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteJDBC {

    private Connection c;
    public boolean connection_status = false;

    public SQLiteJDBC(String game, String db_name) {
        c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:src/Res/Games/" + game + "/" + db_name.toLowerCase() + ".sqlite");
            test_connection();
            connection_status = true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            connection_status = false;
        }
    }

    private void test_connection() throws Exception{
        ResultSet res = null;
        try {
            res = c.createStatement().executeQuery("select * from meta;");
        }
        catch (Exception e) {
            if(res != null){
                res.close();
            }
            System.err.println(e);
            throw e;
        }
    }

    public SQLResult get_database(String table){
        ResultSet res = null;
        try {
            res = c.createStatement().executeQuery("select * from " + table + ";");
        }
        catch (Exception e) {
            System.err.println(e);
        }
        return new SQLResult(res);
    }

    public SQLResult get_row_from_db(String table, int row) {
        ResultSet res = null;
        try {
            res = c.prepareStatement("select * from " + table + " where rowid = " + row + ";").executeQuery();
        }
        catch (Exception e) {
            System.err.println(e);
            System.exit(0);
        }
        return new SQLResult(res);
    }

    public SQLResult get_row_from_db(String table, String id) {
        ResultSet res = null;
        try {
            res = c.prepareStatement("select * from " + table + " where id = '" + id + "';").executeQuery();
        }
        catch (Exception e) {
            System.err.println(e);
            System.exit(0);
        }
        return new SQLResult(res);
    }

    public void close() {
        try {
            if(c != null){
                c.close();
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }

    public void update_db(String table, String column, boolean new_data){
        try {
            c.createStatement().executeQuery("UPDATE " + table + " SET " + column + " = " + new_data);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    public void update_db(String table, String column, String new_data){
        try {
            c.createStatement().executeQuery("UPDATE " + table + " SET " + column + " = " + new_data + ";");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void update_db(String table, String column, int new_data){
        try {
            c.createStatement().executeQuery("UPDATE " + table + " SET " + column + " = " + new_data + ";");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void update_db(String table, String column, String row, boolean new_data){
        try {
            c.createStatement().executeQuery("UPDATE " + table + " SET " + column + " = " + new_data + " WHERE " + column + " = " + row + ";");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void update_db(String table, String column, String row, String new_data){
        try {
            c.createStatement().executeQuery("UPDATE " + table + " SET " + column + " = " + new_data + " WHERE " + column + " = " + row + ";");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void update_db(String table, String column, String row, int new_data){
        try {
            c.createStatement().executeQuery("UPDATE " + table + " SET " + column + " = " + new_data + " WHERE " + column + " = " + row + ";");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
