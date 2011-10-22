import java.util.Scanner;

public class ski {

	private static int[][] runs;
	private static int T;
	private static int S;
	private static int N;
	private static int[][] lessonTimes;
	private static int[][] slopeTimes;
	
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		T = s.nextInt();
		S = s.nextInt(); // number of lessons
		N = s.nextInt(); // number of slopes
		
		lessonTimes = new int[S][3];		
		for (int lesson = 0; lesson < S; lesson++){
			lessonTimes[lesson][0] = s.nextInt(); // start time
			lessonTimes[lesson][1] = s.nextInt(); // length
			lessonTimes[lesson][2] = s.nextInt(); // ability after it
		}
		
		slopeTimes = new int[N][2];
		for(int slope = 0; slope < N; slope++){
			slopeTimes[slope][0] = s.nextInt(); // ability
			slopeTimes[slope][1] = s.nextInt(); // time to ski
		}
		
		runs = new int[N+1][T]; 
		for(int i = 0; i < N+1; i++)
			for(int j = 0; j < T; j++)
				runs[i][j] = -1;
		
		// possible times = 0, 1, 2... T
		// possible abilities = 0, lessonTimes[0], etc
		System.out.println(calculate(0, 0));
		}
	
	private static int calculate(int abilityLevel, int startTime){
		if (startTime >= T)
			return 0;
		
		// call is cached
		if (runs[abilityLevel][startTime] != -1)
			return runs[abilityLevel][startTime];
		
		// otherwise, try everything she can do
		int maxSlopes = 0;
		
		// use it or lose it for every lesson
		for(int lesson = 0; lesson < S; lesson++){
			int[] thisLesson = lessonTimes[lesson]; 
			int endTime = thisLesson[0] + thisLesson[1];
			if (lessonTimes[lesson][0] >= startTime && endTime <= T){ // check if Bessie can take this leson
				maxSlopes = Math.max(maxSlopes, calculate(lesson + 1, endTime));
			}
		}
		
		for(int slope = 0; slope < N; slope++){
			int[] thisSlope = slopeTimes[slope];
			int ability = (abilityLevel == 0) ? 1 : lessonTimes[abilityLevel - 1][2];
			
			int endTime = startTime + thisSlope[1];
			if (ability >= thisSlope[0] && endTime <= T) // check if Bessie can ski this slope
				maxSlopes = Math.max(maxSlopes, 1 + calculate(abilityLevel, endTime));
		}
		
		runs[abilityLevel][startTime] = maxSlopes;
		return maxSlopes;
	}
}
