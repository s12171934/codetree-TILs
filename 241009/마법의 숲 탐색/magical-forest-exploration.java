import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	static int read() throws Exception {
		if (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}

	static int R, C, K, answer, sequence;
	static int[][] deltas = {{-1,0},{0,1},{1,0},{0,-1}};
	static Golem[][] forest;

	static class Golem {
		int max, doorX, doorY;

		Golem(int[] endPoint){
			this.max = endPoint[0];
			this.doorX = endPoint[0] + deltas[endPoint[2]][0];
			this.doorY = endPoint[1] + deltas[endPoint[2]][1];
		}
	}

	public static void main(String[] args) throws Exception {

		R = read(); C = read(); K = read(); forest = new Golem[R + 2][C];
		while (K-- > 0) solve();
		System.out.println(answer);
	}

	static void solve() throws Exception {

		int[] endPoint = moveGolem(read() - 1, read());
		if (endPoint[0] < 3) {
			forest = new Golem[R + 2][C];
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

		forest[golemCenter[0]][golemCenter[1]] = new Golem(golemCenter);
		for (int i = 0; i < 4; i++) {
			if (golemCenter[0] + deltas[i][0] < 0) continue;
			forest[golemCenter[0] + deltas[i][0]][golemCenter[1] + deltas[i][1]] = forest[golemCenter[0]][golemCenter[1]];
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

		int max = 0;
		Golem start = forest[endPoint[0]][endPoint[1]];
		boolean[][] visited = new boolean[R + 2][C];
		visited[start.doorX][start.doorY] = true;
		Queue<Golem> q = new ArrayDeque<>();
		q.add(start);

		while (!q.isEmpty()) {
			Golem cur = q.poll();
			max = Math.max(max, cur.max);
			for (int[] d : deltas) {
				int x = cur.doorX + d[0];
				int y = cur.doorY + d[1];
				if (x < 0 || y < 0 || x >= R + 2 || y >= C || forest[x][y] == null) continue;
				if (visited[forest[x][y].doorX][forest[x][y].doorY]) continue;
				visited[forest[x][y].doorX][forest[x][y].doorY] = true;
				q.add(forest[x][y]);
			}
		}

		answer += max;
	}
}