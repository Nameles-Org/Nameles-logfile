package com.uc3m.nameles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapFilteringRef {
	

	public static ConcurrentHashMap<String, List<Integer>> FiltersHashMap(String bd, String day) {
		
		ConcurrentHashMap<String, List<Integer>> list = new ConcurrentHashMap<String, List<Integer>>();

		Connection c = null;
		Statement stmt = null;
		List<Double> thresholds = ThresholdsRef.Thresholds(bd, day);
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://zompopo3.it.uc3m.es:5432/"+bd, "patricia", "callejo");
			c.setAutoCommit(false);
			stmt = c.createStatement();

			// Highly Supicious (3)
			ResultSet rs = stmt.executeQuery(
					"select referrer, score_"+day+" from stats.referrer where score_"+day+"<="+thresholds.get(0)+" and total_"+day+">1000;");
			while (rs.next()) {
				String referrer = rs.getString("referrer");
				int score = rs.getInt("score_"+day);
				List<Integer> values = new ArrayList<Integer>();
				values.add(3);
				values.add(score);
				list.put(referrer, values);
			}
			
			// Suspicious (2)
			rs = stmt.executeQuery(
					"select referrer, score_"+day+" from stats.referrer where score_"+day+">"+thresholds.get(0)+" and score_"+day+"<="+thresholds.get(1)+" and total_"+day+">1000;");
			while (rs.next()) {
				String referrer = rs.getString("referrer");
				int score = rs.getInt("score_"+day);
				List<Integer> values = new ArrayList<Integer>();
				values.add(2);
				values.add(score);
				list.put(referrer, values);
			}
			
			// Likely suspicious (1)
			rs = stmt.executeQuery(
					"select referrer, score_"+day+" from stats.referrer where score_"+day+">"+thresholds.get(1)+" and score_"+day+"<="+thresholds.get(2)+" and total_"+day+">1000;");
			while (rs.next()) {
				String referrer = rs.getString("referrer");
				int score = rs.getInt("score_"+day);
				List<Integer> values = new ArrayList<Integer>();
				values.add(1);
				values.add(score);
				list.put(referrer, values);
			}

			// Legit (0)
			rs = stmt.executeQuery(
					"select referrer, score_"+day+" from stats.referrer where score_"+day+">"+thresholds.get(2)+" and total_"+day+">1000;");
			while (rs.next()) {
				String referrer = rs.getString("referrer");
				int score = rs.getInt("score_"+day);
				List<Integer> values = new ArrayList<Integer>();
				values.add(0);
//				values.add(total);
				values.add(score);
				list.put(referrer, values);
			}
			
			rs.close();
			stmt.close();
			c.close();
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return list;

	}
}
