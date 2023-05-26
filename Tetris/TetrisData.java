package Tetris;

//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;

public class TetrisData
//	implements Serializable 
	{
	public static final int ROW = 20, COL = 10;
	
	private int data[][];
	private transient int line;
	
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
		MyTetris.getLblScoreLabel().setText("Score: " + getLine() * 175 * MyTetris.getTetrisCanvas().level);
		MyTetris.getLblLineLabel().setText("Line: " + getLine());
		if(MyTetris.getTetrisCanvas().lineTmp != line) {
			switch(line / 10) {
				default:
					MyTetris.getTetrisCanvas().level = line / 10 + 2;
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
	
	public int[][] getData() {
		return data;
	}
	
	public void setData(int[][] data) {
		this.data = data;
	}
	
//	private void writeObject(ObjectOutputStream oos) {
//		try {
//			oos.defaultWriteObject();
//			oos.writeObject(this.data);
//			oos.writeObject(this.line);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//	}
//	
//	private void readObject(ObjectInputStream oos) {
//		try {
//			oos.defaultReadObject();
//			this.data = (int[][]) oos.readObject();
//			this.line = (int) oos.readObject();
//		} catch (IOException | ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//	}
}
