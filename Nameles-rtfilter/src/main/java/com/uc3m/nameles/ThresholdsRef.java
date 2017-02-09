package com.uc3m.nameles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ThresholdsRef {
	public static List<Double> Thresholds(String bd, String day) {
		Connection c = null;
		Statement stmt = null;
		int UHR = 0;
		int IQR = 0;
		List<Double> thresholds = new ArrayList<Double>();
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://zompopo3.it.uc3m.es:5432/"+bd, "pcallejo", "callejo");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select max(score_"+day+"), "
					+ "percentile_cont(0.75) within group (order by score_"+day+") as p075, "
					+ "percentile_cont(0.50) within group (order by score_"+day+") as p05, "
					+ "percentile_cont(0.25) within group (order by score_"+day+") as p025 from stats.referrer "
					+ "where total_"+day+">1000;");
			while (rs.next()) {
				int max = 100;
				int percent_075 = rs.getInt("p075");
				int percent_05 = rs.getInt("p05");
				int percent_025 = rs.getInt("p025");

				UHR = max - percent_05;
				IQR = percent_075 - percent_025;
				
				double HighlySuspicious = percent_025 - 1.5*IQR;
				double Suspicious = max - 3*UHR;
				double LikelySuspicious = max - 2*UHR;

				thresholds.add(HighlySuspicious);
				thresholds.add(Suspicious);
				thresholds.add(LikelySuspicious);

			}	
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return thresholds;
	}
}
