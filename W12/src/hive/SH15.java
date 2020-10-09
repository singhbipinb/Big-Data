package hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SH15 {
	
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	private static Connection con = null;
	private static Statement stmt = null;
	private static ResultSet rset = null;
	

	public static void main(String[] args) throws SQLException,ClassNotFoundException {
		
		
		Class.forName(driverName);
		
		
		con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default","","");
		stmt = con.createStatement();
		
//		Question 1:  create a table for Employee and Salary data set
		
		System.out.println("Question 1 \n");
		
		System.out.println("Creating Employee table");
		
		stmt.execute("CREATE TABLE IF NOT EXISTS"
		         +" Employee ( emp_id int, birthday String,"
		         +" first_name String, last_name String,"
		         +" gender varchar(5), work_day String)"
		         +" COMMENT 'Employee details'"
		         +" ROW FORMAT DELIMITED"
		         +" FIELDS TERMINATED BY ','"); 
		      System.out.println("Table Employee created \n");
                    
		System.out.println("Creating Salary table");
				
		stmt.execute("CREATE TABLE IF NOT EXISTS"
		         +" Salary ( emp_id int, salary String,"
		         +" start_date String, end_date String)"
		         +" COMMENT 'Salary details'"
		         +" ROW FORMAT DELIMITED"
		         +" FIELDS TERMINATED BY ','"); 
		      System.out.println("Table Salary created \n");
	
//		Question 2: loading data into Employee and Salary Tables from the given the data set files.
		      
		System.out.println(" \nQuestion 2 \n");
		
		System.out.println("Loading data in Employee table");

		stmt.execute("load data local inpath '/home/cloudera/Downloads/employee.csv' overwrite into table Employee");
		
			System.out.println("Data loaded in Employee table \n");

		
		System.out.println("Loading data in Salary table");

		stmt.execute("load data local inpath '/home/cloudera/Downloads/salary.csv' overwrite into table Salary");
			
			System.out.println("Data loaded in Salary table \n");
		
	
//		Question 3: retrieve the top 10 oldest employees.
			
			System.out.println("Question 3 \n");
			
		ResultSet que3 = stmt.executeQuery("select * from Employee order by birthday limit 10");
		
		System.out.println("Emp_ID \t FirstName \t LastName \t Gender \t Work_day");
		while (que3.next()) {
	    	int column = que3.getMetaData().getColumnCount();
	    	
	    	for(int i=1;i<=column;i++){
	    		System.out.print(que3.getString(i)+"\t");
	    	}
	        System.out.println();
	    }
		
		
//		Question 4: find the top 10 employees earned the highest average salary
		
		System.out.println("\nQuestion 4 \n");
		
		ResultSet que4 = stmt.executeQuery("select e.first_name,e.last_name,round(avg(s.salary),2) as avgsal from employee as e"
				+ " join salary as s on (e.emp_id == s.emp_id) group by e.first_name,e.last_name order by avgsal desc limit 10");
		
		System.out.println("First Name \t Last Name \t Average Salary");
		while (que4.next()) {
	    	int column = que4.getMetaData().getColumnCount();
	    	
	    	for(int i=1;i<=column;i++){
	    		System.out.print(que4.getString(i)+"\t");
	    	}
	        System.out.println();
	    }
		
		
//		Question 5: find the number of male and female employees.
		
		System.out.println("\nQuestion 5 \n");
		
		ResultSet que5 = stmt.executeQuery("select gender,count(*) from Employee"
				+" group by gender");
		
		System.out.println("Gender \t Employee Count");
	    while (que5.next()) {
	    	int column = que5.getMetaData().getColumnCount();
	    	
	    	for(int i=1;i<=column;i++){
	    		System.out.print(que5.getString(i)+"\t");
	    	}
	        System.out.println();
	    }
		
		con.close();
		
	}
	
	
}
