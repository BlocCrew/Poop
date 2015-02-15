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
	      System.err.println("[Poop]" + e.getClass().getName() + ": " + e.getMessage() );
	    }
	}
	
	public int[] getEaten(String name){
		int[] eaten = new int[3];
		Connection c = null;
	    Statement stmt = null;
		int poop=0, pee=0, diarrhea=0;
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	c = DriverManager.getConnection("jdbc:sqlite:"+path);
	    	c.setAutoCommit(false);

	    	stmt = c.createStatement();
	    	ResultSet rs = stmt.executeQuery("SELECT * FROM poopdata WHERE name = '"+name+"';");
	    	while(rs.next()){
	    		poop = rs.getInt("poop");
	    		pee = rs.getInt("pee");
	    		diarrhea = rs.getInt("diarrhea");
	    	}
	    	rs.close();
	    	stmt.close();
	    	c.close();
	    } catch ( Exception e ) {
	    	System.err.println("[Poop]" + e.getClass().getName() + ": " + e.getMessage() );
	    }
	    eaten[0] = poop;
	    eaten[1] = pee;
	    eaten[2] = diarrhea;
		return eaten;
	}
}