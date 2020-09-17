package sh12;

import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;


public class PutData {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		Configuration con = HBaseConfiguration.create();
		HBaseAdmin admin = new HBaseAdmin(con);

		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Table Name: ");
		String name = scan.nextLine();

		if (admin.tableExists(name)) {
			HTable hTable = new HTable(con, name);

			System.out
					.println("Enter data in following format:\nRow, Column Family Name, Column Name, Value");
			String in = scan.nextLine();
			String[] tokens = in.split(",");

			Put p = new Put(Bytes.toBytes(tokens[0].trim()));

			p.add(Bytes.toBytes(tokens[1].trim()),
					Bytes.toBytes(tokens[2].trim()),
					Bytes.toBytes(tokens[3].trim()));

			hTable.put(p);
			System.out.println("Data inserted in table");

			hTable.close();
		} else
			System.out.println("Table doesn't exist");
		scan.close();
		admin.close();

	}
}
