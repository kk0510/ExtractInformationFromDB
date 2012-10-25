import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


/**
 *
 * @author kosuke
 */
public class ExtractInformationFromDB{
	public static HashMap<String, String> ExtractRelationsFromDB(){
		HashMap<String, String> hits = new HashMap<String, String>();
		 try {
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wikipediaontology20111107","root","root");
			st = conn.createStatement();
			rs = st.executeQuery("select * from instance_all");

			while(rs.next()){
				hits.put(rs.getString("instance"),rs.getString("class"));
			}
			System.out.println(hits.size());

			rs.close();
			st.close();
			conn.close();
		} catch (ClassNotFoundException e) {
		System.out.println("ドライバを読み込めませんでした "+ e);
		} catch (SQLException e) { 
		System.out.println("データベース接続エラー　"+ e);
		}
		return hits;
	}
}