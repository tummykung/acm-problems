import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class wookie {
	
	public static void main(String[] args){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try{
		//====  Input =======
			boolean finished = false;
			while (!finished) {
				char[] board = new char[25];
				for (int r = 0; r < 5; r++) {
					StringTokenizer tokenizer = new StringTokenizer(in.readLine());
					for (int c = 0; c < 5; c++) {
						board[5*r + c] = tokenizer.nextToken().charAt(0);
					}
				}
				
				for (int i = 0; i < 25; i++) {
					if (board[i] == '*') {
						if (!wins(board, i, 'X') && !wins(board, i, 'O')) {
							System.out.println(i+1);
							break;
						}
					}
				}
				
				if (in.readLine().equals("Finished")) finished = true;
			}
		//==== SETUP ========
		//===== RUN ========
			
		//===== OUTPUT =====
		// System.out.println();
		
		} // Write before this line
		catch(Exception e){
			System.out.print("throwing up");
		}
	}
	
	public static boolean wins(char[] board, int i, char player) {
		int row = i / 5;
		int col = i % 5;
		int maxrun;
		
		maxrun = 1;
		for (int c = col+1; c < 5; c++)
			if (board[5*row + c] == player) maxrun++; else break;
		for (int c = col-1; c >= 0; c--)
			if (board[5*row + c] == player) maxrun++; else break;
		if (maxrun >= 4) return true;

		maxrun = 1;
		for (int r = row+1; r < 5; r++)
			if (board[5*r + col] == player) maxrun++; else break;
		for (int r = row-1; r >= 0; r--)
			if (board[5*r + col] == player) maxrun++; else break;
		if (maxrun >= 4) return true;

		maxrun = 1;
		for (int r = row+1, c = col+1; r < 5 && c < 5; r++, c++)
			if (board[5*r + c] == player) maxrun++; else break;
		for (int r = row-1, c = col-1; r >= 0 && c >= 0; r--, c--)
			if (board[5*r + c] == player) maxrun++; else break;
		if (maxrun >= 4) return true;

		maxrun = 1;
		for (int r = row+1, c = col-1; r < 5 && c >= 0; r++, c--)
			if (board[5*r + c] == player) maxrun++; else break;
		for (int r = row-1, c = col+1; r >= 0 && c < 5; r--, c++)
			if (board[5*r + c] == player) maxrun++; else break;
		if (maxrun >= 4) return true;
		
		return false;
	}
}
