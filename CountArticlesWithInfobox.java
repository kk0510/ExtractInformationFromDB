import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class CountArticlesWithInfobox{
    public static void main(String args[]){			
	
        int count =0;
        String line = "";
	String title = "";
	boolean infobox_flag = false;
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
		rs = st.executeQuery("select * from instance_all");

		while(rs.next()){
			hits.add(rs.getString("instance"));
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
        try {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\kosuke\\Desktop\\jawikidump.xml"),"utf-8"));
		while((line = br.readLine()) != null){
			/* リダイレクト＋通常の記事を抽出 */
			if(line.contains("<title>") && !line.contains(":") && !line.contains("曖昧さ回避") && !line.contains("の一覧")){
				title = line.replaceFirst("<title>", "").replaceFirst("</title>", "").trim();
				//System.out.println(title);
				
				infobox_flag = true;
			}
			/*if(!line.contains("#redirect") && !line.contains("#REDIRECT") && !line.contains("#Redirect") && !line.contains("#転送") && !title.contains(":") && 
				!title.contains("曖昧さ回避") && !title.contains("の一覧"))
			{
				count++;
				//System.out.println(line);
			}*/
			if(line.contains("{{Infobox") && infobox_flag==true){
				infobox_flag = false;
				if(hits.contains(title)){
					count++;
				}
				//System.out.println(title+">>>>>>>>>>>>>"+"\n"+line);
			}
		}
		System.out.println(count);
		br.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
	}      
        
       
                        

}
}