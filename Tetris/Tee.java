package Tetris;

public class Tee extends Piece {
	public Tee(TetrisData data) {
		super(data);
		c[0] = 0;		r[0] = 0;
		c[1] = -1;		r[1] = 0;
		c[2] = 1;		r[2] = 0;
		c[3] = 0;		r[3] = 1;
	}
	
	public int getType() {
		return 2;
	}
	
	public int roteType() {
		return 4;
	}
}
