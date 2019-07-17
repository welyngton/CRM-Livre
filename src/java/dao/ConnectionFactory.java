/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.sql.*;
/**
 *
 * @author W
 */
public class ConnectionFactory { 

public static Connection getConnection () throws ClassNotFoundException, SQLException {
    
    Class.forName("com.mysql.jdbc.Driver");     
    //Connection con = null;
    //PreparedStatement st = null; 
    //con = DriverManager.getConnection(url);
    return DriverManager.getConnection("http://localhost:3306/daccrm","root", "admin");
    //DriverManager.getConnection(url,nome,senha);
    }
} 
