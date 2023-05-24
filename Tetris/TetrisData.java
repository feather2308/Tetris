package tetris;

public class TetrisData {
	public static final int ROW = 20, COL = 10;
	
	private int data[][], line;
	
	public TetrisData() {
		data = new int[ROW][COL];
	}
	
	public int getAt(int x, int y) {
		if(x < 0 || x >= ROW || y < 0 || y >= COL) return 0;
		return data[x][y];
	}
	
	public void setAt(int x, int y, int v) {
		data[x][y] = v;
	}
	
	public int getLine() {
		return line;
	}
	
	public synchronized void removeLiness() {
		NEXT:
			for(int i = ROW - 1; i >= 0; i--) {
				boolean done = true;
				for(int k = 0; k < COL; k++) {
					if(data[i][k] == 0) {
						done = false;
						continue NEXT;
					}
				}
				if(done) {
					line++;
					for(int x = i; x > 0; x--) {
						for(int y = 0; y < COL; y++) {
							data[x][y] = data[x-1][y];
						}
					}
					if(i != 0) {
						for(int y = 0; y < COL; y++) {
							data[0][y] = 0;
						}
					}
					i++;
				}
			}
		MyTetris.getLblScoreLabel().setText("Score: " + getLine() * 175 * MyTetris.tetrisCanvas.level);
		MyTetris.getLblLineLabel().setText("Line: " + getLine());
		if(MyTetris.tetrisCanvas.lineTmp != line) {
			switch(line / 10) {
				default:
					MyTetris.tetrisCanvas.level = line / 10 + 2;
					break;
			}
		}
	}
	
	public void clear() {
		line = 0;
		for(int i = 0; i < ROW; i++) {
			for(int k = 0; k < COL; k++) {
				data[i][k] = 0;
			}
		}
	}
	
	public void dump() {
		for(int i = 0; i < ROW; i++) {
			for(int k = 0; k < COL; k++) {
				System.out.print(data[i][k] + " ");
			}
			System.out.println();
		}
	}
}
