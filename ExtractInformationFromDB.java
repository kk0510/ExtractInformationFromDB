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
    public static void main(String args[]){			
	
        int count =0;
        
        HashSet<String> hits = new HashSet<String>();
       
        try {
		Connection conn = null;
		Statement st = null;
		Statement std = null;
		ResultSet rs = null;
		ResultSet rsd = null;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/wikipediaontology20111107","root","root");
		st = conn.createStatement();
		std = conn.createStatement();
		rs = st.executeQuery("select * from propertytriple");

		while(rs.next()){
			hits.add(rs.getString("INSTANCE"));
		}
		for(Iterator itr = hits.iterator();itr.hasNext();){
			count = 0;
			rsd = std.executeQuery("select * from propertytriple where INSTANCE='" +itr.next().toString()  +"'");
			while(rsd.next()){
				count++;
			}
			System.out.println(count);
		}
		rs.close();
		st.close();
		conn.close();
        } catch (ClassNotFoundException e) {
        System.out.println("ドライバを読み込めませんでした "+ e);
        } catch (SQLException e) { 
        System.out.println("データベース接続エラー　"+ e);
        }     
       
                        

}
}