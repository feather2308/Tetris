package Tetris;

public class Dg extends Piece { // Diagonal
	public Dg(TetrisData data) {
		super(data, 10, 2);
		c[0] = -1;		r[0] = -1;
		c[1] = 0;		r[1] = 0;
		c[2] = 1;		r[2] = 1;
		c[3] = 2;		r[3] = 2;
	}
}
