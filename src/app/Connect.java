/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author Lemansky
 */
public class Connect {
    public Connection conn;
    public Statement stmt;
    public ResultSet rs;
    
    public Connect(){
        try {

            conn = DriverManager.getConnection("jdbc:sqlite:test.db");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
    
    public ArrayList<String> select(String[] columnsArray, String table){
        ArrayList<String> data = new ArrayList<String>();
        
        String columnsString = String.join("], [", columnsArray);
        String sql = "SELECT " + '[' + columnsString + ']' + " FROM " + '[' + table + ']';
        
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row += rs.getString(columnsArray[i]) + "---";
                }
                data.add(row);
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
     public ArrayList<String> selectWhere(String[] columnsArray, int[] whereColIndex, String[] whereValue, String table){
        ArrayList<String> data = new ArrayList<String>();
        
        String columnsString = String.join("], [", columnsArray);
        
        String sql = "SELECT " + '[' + columnsString + ']' + " FROM " + '['+ table + ']' + " WHERE ";
            for (int i = 0; i < whereColIndex.length; i++) {
                sql += '[' + columnsArray[whereColIndex[i]] +']' + " LIKE '" + whereValue[i] + "' AND ";
            }
        sql = sql.substring(0, sql.length() - 4);
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row += rs.getString(columnsArray[i]) + "---";
                }
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(sql);
        }
        return data;
    }
     
    public void delete(String column, int id, String table){
        String sql = "DELETE FROM " + '[' + table + ']' + " WHERE " + '[' + column + ']' + " = " + id;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }    
    
    public void insert(String[] columnsArr, String[] valuesArr, String table)
    {
        String columns = String.join("], [", columnsArr);
        String values  = "'" + String.join("', '", valuesArr) + "'";
        
        values = values.replace("''", "null");
        
        String sql = "INSERT INTO " + '[' + table + ']' + " (" + "[" + columns + "]" + ") VALUES (" + values + ")";
        
        
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
             System.out.println(e.getMessage());
        }
        
    }
    
    public void update (String[] colsArr, String[] valuesArr, String whereCol, String whereVal, String table)
    {
        String sql;
        String[] pairs = new String[colsArr.length];
        for (int i = 0; i < colsArr.length; i++) {
            pairs[i] = '[' + colsArr[i] + ']' + " = '" + valuesArr[i] + "'";
        }
        sql = "UPDATE " + '[' + table + ']' + " SET " + String.join(", ", pairs) + " WHERE " + '[' + whereCol + ']' + " = '" + whereVal + "'";
        sql = sql.replace("''", "null");
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
             System.out.println(e.getMessage());
             System.out.println(sql);
        }
    }
    
    public void close(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Throwable ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
