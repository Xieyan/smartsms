import java.io.*;
import java.util.*;

public class TestChineseSpliter {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		File filein = new File ("Spliter.in");
		File fileout = new File ("Spliter.out");
		Scanner input = new Scanner (filein);
		PrintWriter output = new PrintWriter(fileout);
		while (input.hasNext()){
			String strin = input.nextLine();
			String strout = ChineseSpliter.split(strin, "  ");
			//System.out.println(strout);
			System.out.println("i am here");
			output.println(strout);
		}
		input.close();
		output.close();
	}

}
