package com.norway240.poop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLStuffs {
	
	String path = null;
	
	public SQLStuffs(String p){
		path = p;
	}
	
	public void execute(String sql){
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+path);
	      c.setAutoCommit(false);

	      stmt = c.createStatement();
	      stmt.executeUpdate(sql);

	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
	public int getEaten(String name){
		Connection c = null;
	    Statement stmt = null;
		int eaten = 0;
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:"+path);
	    	c.setAutoCommit(false);

	    	stmt = c.createStatement();
	    	ResultSet rs = stmt.executeQuery("SELECT * FROM poopdata WHERE name = '"+name+"';");
	    	while(rs.next()){
	    		eaten = rs.getInt("eaten");
	    	}
	    	rs.close();
	    	stmt.close();
	    	c.close();
	    } catch ( Exception e ) {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    	System.exit(0);
	    }
		return eaten;
	}
}