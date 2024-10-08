import java.io.*;
import java.util.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder answer = new StringBuilder();
	static ColorTree[] colorTree = new ColorTree[100_001];
	static ArrayList<ColorTree> roots = new ArrayList<>();

	static int read() throws Exception {
		if (st == null || !st.hasMoreTokens()) st = new StringTokenizer(br.readLine());
		return Integer.parseInt(st.nextToken());
	}

	static class ColorTree {
		int pId, maxDepth, color;
		ArrayList<Integer> childId;

		public ColorTree(int pId, int color, int maxDepth) {
			this.pId = pId;
			this.color = color;
			this.maxDepth = maxDepth;
			childId = new ArrayList<>();
		}
	}

	public static void main(String[] args) throws Exception {
		int Q = read();
		while (Q-- > 0) {
			switch (read()) {
				case 100:
					registerNode(read(), read(), read(), read());
					break;
				case 200:
					changeColor(read(), read());
					break;
				case 300:
					showColor(read());
					break;
				case 400:
					showScore();
					break;
			}
		}
		System.out.println(answer.toString());
	}

	static void registerNode(int m_id, int p_id, int color, int max_depth) {

		colorTree[m_id] = new ColorTree(p_id, color, max_depth);
		if (p_id == -1) {
			roots.add(colorTree[m_id]);
			return;
		}
		int cnt = 1;
		while (p_id != -1) {
			if(colorTree[p_id].maxDepth - ++cnt < 0) {
				colorTree[m_id] = null;
				return;
			}
			p_id = colorTree[p_id].pId;
		}
		colorTree[colorTree[m_id].pId].childId.add(m_id);
	}

	static void changeColor(int m_id, int color) {

		colorTree[m_id].color = color;
		for (int child : colorTree[m_id].childId) {
			changeColor(child, color);
		}
	}

	static void showColor(int m_id) {

		answer.append(colorTree[m_id].color).append("\n");
	}

	static int sum;
	static void showScore() {

		sum = 0;
		for (ColorTree root : roots) calcScore(root);
		answer.append(sum).append("\n");
	}

	static int calcScore(ColorTree cur) {

		int res = 1 << cur.color;
		for (int child : cur.childId) {
			res |= calcScore(colorTree[child]);
		}
		sum += Integer.bitCount(res) * Integer.bitCount(res);
		return res;
	}
}