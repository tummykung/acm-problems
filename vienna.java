import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class vienna{
	public static void main(String[] args){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try{
		//====  Input =======
		StringTokenizer tokenizer = new StringTokenizer(in.readLine());
		int N = Integer.parseInt(tokenizer.nextToken());
		//==== SETUP ========
		String s1 = tokenizer.nextToken();
		String s2 = tokenizer.nextToken();
		System.out.println(common(s1,s2));
		//===== RUN ========
			
		//===== OUTPUT =====
		// System.out.println();
		
		} // Write before this line
		catch(Exception e){
			System.out.print("throwing up");
			e.printStackTrace();
		}
		
	}
	public static int common(String s1, String s2) {
		if(s1.equals("") || s2.equals(""))
			return 0;
		if(s1.charAt(0) == s2.charAt(0))
			return 1 + common(s1.substring(1), s2.substring(1));
		if(s1.charAt(0) < s2.charAt(0))
			return common(s1.substring(1), s2.substring(0));
		else
			return common(s1.substring(0), s2.substring(1));
			
	}
}
