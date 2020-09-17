package sh12;

import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class DropAllTables {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		Configuration con = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(con);

		Scanner scan = new Scanner(System.in);
		System.out.println("To drop multiple Tables \nEnter Table Regex: ");
		String regex = scan.nextLine();
		scan.close();

		try {
			for (String name : admin.getTableNames(regex)) {
				if (admin.isTableEnabled(name))
					admin.disableTable(name);
				admin.deleteTable(name);
				System.out.println(name + "tables dropped");
			}

		} catch (TableNotFoundException e) {
			System.out.println("Table not found\n" + e.toString());
		}

		admin.close();
	}

}
