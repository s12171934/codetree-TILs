import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static int read() throws Exception {
		if (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}

	static int R, C, K, answer;
	static int[][] deltas = {{-1,0},{0,1},{1,0},{0,-1}};
	static Golem[][] forest;
	static boolean[][] doors;

	static class Golem{
		int max;

		Golem(int max) {
			this.max = max;
		}
	}

	public static void main(String[] args) throws Exception {

		R = read(); C = read(); K = read(); forest = new Golem[R + 2][C]; doors = new boolean[R + 2][C];
		while (K-- > 0) solve();
		System.out.println(answer);
	}

	static void solve() throws Exception {

		int[] endPoint = moveGolem(read() - 1, read());
		if (endPoint[0] < 3) {
			forest = new Golem[R + 2][C];
			doors = new boolean[R + 2][C];
			return;
		}
		moveSpirit(endPoint);
	}

	static int[] moveGolem(int col, int dist) {

		int[] golemCenter = new int[]{0, col, dist};

		while(true) {
			if(isEmptySouth(golemCenter)) {
				golemCenter[0]++;
				continue;
			}
			if(isEmptyWest(golemCenter)) {
				golemCenter[0]++;
				golemCenter[1]--;
				golemCenter[2] = (golemCenter[2] + 3) % 4;
				continue;
			}
			if(isEmptyEast(golemCenter)) {
				golemCenter[0]++;
				golemCenter[1]++;
				golemCenter[2] = (golemCenter[2] + 1) % 4;
				continue;
			}
			break;
		}

		return golemCenter;
	}

	static boolean isEmptySouth(int[] center) {

		if (center[0] == R) return false;
		if (forest[center[0] + 1][center[1] - 1] != null) return false;
		if (forest[center[0] + 2][center[1]] != null) return false;
		if (forest[center[0] + 1][center[1] + 1] != null) return false;
		return true;
	}

	static boolean isEmptyWest(int[] center) {

		if (center[0] == R || center[1] == 1) return false;
		if (center[0] != 0 && forest[center[0] - 1][center[1] - 1] != null) return false;
		if (forest[center[0]][center[1] - 2] != null) return false;
		if (forest[center[0] + 1][center[1] - 1] != null) return false;
		if (forest[center[0] + 1][center[1] - 2] != null) return false;
		if (forest[center[0] + 2][center[1] - 1] != null) return false;
		return true;
	}

	static boolean isEmptyEast(int[] center) {

		if (center[0] == R || center[1] == C - 2) return false;
		if (center[0] != 0 && forest[center[0] - 1][center[1] + 1] != null) return false;
		if (forest[center[0]][center[1] + 2] != null) return false;
		if (forest[center[0] + 1][center[1] + 1] != null) return false;
		if (forest[center[0] + 1][center[1] + 2] != null) return false;
		if (forest[center[0] + 2][center[1] + 1] != null) return false;
		return true;
	}

	static void moveSpirit(int[] endPoint) {

		Golem golem = new Golem(endPoint[0]);
		for (int i = 0; i < 4; i++) {
			int x = endPoint[0] + deltas[endPoint[2]][0] + deltas[i][0];
			int y = endPoint[1] + deltas[endPoint[2]][1] + deltas[i][1];
			if (x < 0 || y < 0 || x >= R + 2 || y >= C || forest[x][y] == null) continue;
			if (golem.max < forest[x][y].max) golem = forest[x][y];
		}
		forest[endPoint[0]][endPoint[1]] = golem;
		doors[endPoint[0] + deltas[endPoint[2]][0]][endPoint[1] + deltas[endPoint[2]][1]] = true;
		for (int i = 0; i < 4; i++) {
			forest[endPoint[0] + deltas[i][0]][endPoint[1] + deltas[i][1]] = golem;
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int x = endPoint[0] + deltas[i][0] + deltas[j][0];
				int y = endPoint[1] + deltas[i][1] + deltas[j][1];
				if (x < 0 || y < 0 || x >= R + 2 || y >= C || !doors[x][y]) continue;
				if(forest[x][y].max < golem.max) forest[x][y].max = golem.max;
			}
		}

		answer += golem.max;
	}
}