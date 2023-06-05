package Tetris;

public class Empty extends Piece {
	public Empty(TetrisData data) {
		super(data, 8, 0);
		c[0] = 0;		r[0] = 0;
		c[1] = 0;		r[1] = 0;
		c[2] = 0;		r[2] = 0;
		c[3] = 0;		r[3] = 0;
	}
}
