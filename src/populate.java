
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
public class populate {
	//Connection Variables

  	static String Building = null;
  	static String Students = null;
  	static String AS = null;
  	
	public populate() throws IOException
    {
		System.out.println();
	//	ConnectToDB();
		buildingdb();
		studentdb();
		asdb();
	
		
	}
  	
  	public static void main(String[] args) throws IOException 
    {
  		
  		if(args.length <= 3)
  		Building = args[0];
  		Students = args[1];
  		AS = args[2];
  		
  		populate e = new populate();
    }
  	

	

		public void buildingdb() throws IOException {
		dbaction db = new dbaction();
		db.connection();
		db.delete("building");
		BufferedReader br = new BufferedReader(new FileReader(Building));
		
		String query = null;
		String line = null;
		
		//String query_delete = "delete from building";
		
		//mainStatement.executeUpdate( query_delete );
		
		String coordinate = "";
		
		while((line = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(line,",");
			   	
			String num = st.nextToken().trim();
			String name = st.nextToken().trim();
			String poly = st.nextToken().trim();
			String startx = st.nextToken().trim();
			String starty = st.nextToken().trim();
			coordinate = startx + "," + starty;
			    
			    while(st.hasMoreTokens()){
			    	coordinate += "," + st.nextToken() +","+st.nextToken();
			    	
			    }
			    //coordinate += "," + startx + "," + starty;
			   query= "INSERT INTO building VALUES ('" + num + "','" + name + "',"+ poly +", SDO_GEOMETRY(2003,NULL,NULL,MDSYS.SDO_ELEM_INFO_ARRAY(1,1003,1),MDSYS.SDO_ORDINATE_ARRAY(" + coordinate +")))";
			  
			   db.insert(query);
			   
			    
			    System.out.println("inserted");
			}
		br.close();
		db.close();
	}
	public void studentdb() throws IOException {
		dbaction db = new dbaction();
		db.connection();
		db.delete("student");
		
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(Students));
		
		String query = null;
		String line = null;
		
		
			while((line = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(line,",");
			     			
			query="INSERT INTO student VALUES ('"+ st.nextToken().trim() +"',MDSYS.SDO_GEOMETRY(2001,NULL,MDSYS.SDO_POINT_TYPE("+ st.nextToken().trim()+","+st.nextToken().trim()+",NULL),NULL, NULL))";
			db.insert(query);   
			
			}

			br.close();
			db.close();
		
		
	}
public void asdb() throws IOException{
	dbaction db = new dbaction();
	db.connection();
	db.delete("anns");
	
	BufferedReader br = null;
	br = new BufferedReader(new FileReader(AS));
	
    String query = null;
	String line = null;
	
	
	
	while((line = br.readLine()) != null){
		StringTokenizer st = new StringTokenizer(line,",");
		String asid = st.nextToken().trim();
		String asx = st.nextToken().trim();
		String asy = st.nextToken().trim();
		String radius = st.nextToken().trim();
		
		String points = Integer.parseInt(asx) + "," + (Integer.parseInt(asy) + Integer.parseInt(radius)) + "," + Integer.parseInt(asx) + "," + (Integer.parseInt(asy) - Integer.parseInt(radius)) + "," + (Integer.parseInt(asx) + Integer.parseInt(radius)) + "," + Integer.parseInt(asy);
		query="INSERT INTO anns VALUES ('" + asid + "'," + asx + "," + asy + ", sdo_geometry (2003, null, null, sdo_elem_info_array (1,1003,4), sdo_ordinate_array (" + points + "))," + radius+ ")";
		
		db.insert(query);
		
		}
		
		br.close();
		db.close();
}
	


}
