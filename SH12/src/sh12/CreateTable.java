package sh12;

import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableExistsException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class CreateTable {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		Configuration con = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(con);

		Scanner scan = new Scanner(System.in);
		System.out.println("Create Table\nEnter Table Name: ");
		String name = scan.nextLine();
		scan.close();

		HTableDescriptor table = new HTableDescriptor(TableName.valueOf(name));
		HColumnDescriptor fam1 = new HColumnDescriptor("colFamily1");
		HColumnDescriptor fam2 = new HColumnDescriptor("colFamily2");

		table.addFamily(fam1);
		table.addFamily(fam2);

		try{
		admin.createTable(table);
		System.out.println("Created the table");
		}
		catch(TableExistsException e){
			System.out.println("Table already exist.\n" + e.toString());
		}
		admin.close();

	}

}
