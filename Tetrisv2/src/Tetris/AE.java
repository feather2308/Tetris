package Tetris;

public class AE extends Piece { //Get Along Badly With Each Other
	public AE(TetrisData data) {
		super(data, 10, 2);
		c = new int[2]; r = new int[2];
		c[0] = -1;		r[0] = 0;
		c[1] = 2;		r[1] = 0;
	}
}
