package sh12;

import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableNotFoundException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class IsDisabled {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		Configuration con = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(con);

		Scanner scan = new Scanner(System.in);
		System.out.println("To check the disable Status\nEnter Table Name: ");
		String name = scan.nextLine();
		scan.close();

		try {
			if (admin.isTableDisabled(name))
				System.out.println("Disable: TRUE");
			else
				System.out.println("Disable: FALSE");

		} catch (TableNotFoundException e) {
			System.out.println("Table not found\n" + e.toString());
		}

		admin.close();
	}

}
