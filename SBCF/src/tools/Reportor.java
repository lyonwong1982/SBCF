/**
 * Tools about printing.
 */
package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * @author Liang Wang
 *
 */
public class Reportor {
	public static void report(ArrayList<LinkedHashMap<String, String>> printContent, String fileName) {
		if(printContent == null) return;
		if(printContent.size() == 0) return;
		FileWriter fw = null;
		PrintWriter pw = null;
		String text = "";
		try {
			File f = new File(fileName +".csv");
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			fw = new FileWriter(f, true);
			pw = new PrintWriter(fw);
//			Print the header.
			for(String t : printContent.get(0).keySet()) {
				text = text + t + ",";
			}
			text = text.substring(0, text.length() - 1);
			pw.println(text);
			System.out.println(text);
//			Print the body.
			for(int i = 0; i < printContent.size(); i ++) {
				text = "";
				for(String t : printContent.get(i).keySet()) {
					text = text + printContent.get(i).get(t) + ",";
				}
				text = text.substring(0, text.length() - 1);
				pw.println(text);
				System.out.println(text);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				pw.flush();
				fw.flush();
				pw.close();
				fw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
