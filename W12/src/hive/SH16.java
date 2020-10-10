package hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SH16 {


	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	private static Connection con = null;
	private static Statement stmt = null;
	private static ResultSet rset = null;


	public static void main(String[] args) throws SQLException,ClassNotFoundException {


		Class.forName(driverName);


		con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default","","");
		stmt = con.createStatement();


		System.out.println("Question 1 \n");

		System.out.println("Creating Olympic table");

		stmt.execute("CREATE TABLE IF NOT EXISTS"
				+" Olympic ( AthleteName String, Age int,"
				+" Country String, Year int,"
				+" Closing_date String, Sport String,"
				+" Gold_Medals int, Silver_Medals int,"
				+" Bronze_Medals int, Total_Medals int)"
				+" COMMENT 'Olympics data'"
				+" ROW FORMAT DELIMITED"
				+" FIELDS TERMINATED BY ','"); 
		System.out.println("Table Olympic created \n");


		System.out.println(" \nQuestion 2 \n");

		System.out.println("Loading data in Olympic table");

		stmt.execute("load data local inpath '/home/cloudera/Downloads/olympic.csv' overwrite into table olympic");

		System.out.println("Data loaded in Olympic table \n");

		
		System.out.println("Question 3 \n");

		stmt.execute("set hive.exec.dynamic.partition.mode=nonstrict");
		
		System.out.println("Creating a partitioned table");

		stmt.execute("CREATE TABLE IF NOT EXISTS"
				+" partolympic ( AthleteName String, Age int,"
				+" Country String,"
				+" Closing_date String, Sport String,"
				+" Gold_Medals int, Silver_Medals int,"
				+" Bronze_Medals int, Total_Medals int)"
				+" partitioned by (Year int)"
				+" ROW FORMAT DELIMITED"
				+" FIELDS TERMINATED BY ','");
		
		System.out.println("Partitioned Table created \n");
		
		System.out.println("Loading data into partitioned table");
		stmt.execute("insert overwrite table partolympic partition(Year)"
				+"select AthleteName,"
				+" Age, Country,"
				+" Year, Closing_date,"
				+" Sport, Gold_Medals,"
				+" Silver_Medals, Bronze_Medals,"
				+" Total_Medals from olympic");
		
		System.out.println("Data loaded in partitioned table\n");
		
		
		System.out.println("Question 4 \n");
		
		System.out.println("Bucketing a table");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS"
				+" bucketolympic (AthleteName String,Age int,"
				+" Country String, Year int,"
				+" Closing_date String, Sport String,"
				+" Gold_Medals int, Silver_Medals int,"
				+" Bronze_Medals int, Total_Medals int)"
				+" clustered by(Country) into 4 buckets"
				+" ROW FORMAT DELIMITED"
				+" FIELDS TERMINATED BY ','");
		
		System.out.println("Created bucketed table\n");
		
		System.out.println("Loading data in bucketed table");
		
		stmt.execute("set hive.enforce.bucketing = true;");
		
		stmt.execute("insert overwrite table bucketolympic"
				+" select*from olympic");
		
		System.out.println("Data loaded in bucketed table");


System.out.println("\nQuestion 5 \n");
		
		ResultSet que5 = stmt.executeQuery("select Country,sum(Total_Medals) from olympic"
				+" group by Country");
		
		System.out.println("Country \t Total Medals");
	    while (que5.next()) {
	    	int column = que5.getMetaData().getColumnCount();
	    	
	    	for(int i=1;i<=column;i++){
	    		System.out.print(que5.getString(i)+"\t");
	    	}
	        System.out.println();
	    }
	    
	    
System.out.println("\nQuestion 6 \n");
		
		ResultSet que6 = stmt.executeQuery("select Year,sum(Total_Medals) from olympic"
				+" where Country='India' group by Year");
		
		System.out.println("Year \t Medal Count");
	    while (que6.next()) {
	    	int column = que6.getMetaData().getColumnCount();
	    	
	    	for(int i=1;i<=column;i++){
	    		System.out.print(que6.getString(i)+"\t");
	    	}
	        System.out.println();
	    }
		
		con.close();

	}  

}
