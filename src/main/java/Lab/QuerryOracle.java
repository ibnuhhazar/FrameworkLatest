package Lab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuerryOracle {

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@10.1.91.130:1521:BTPNPRD", "btpn_mobr5","P@ssw0rd");
			Statement stmt = con.createStatement();
			System.out.println("Created DB Connection....");
			ResultSet rs = stmt.executeQuery("SELECT * FROM MOB_CUSTOMERS_IDENTIFICATIONS");
			System.out.println(rs + ": INI ADALAH RESULT QUERY");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
