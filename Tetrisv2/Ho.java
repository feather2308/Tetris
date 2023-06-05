package Tetris;

public class Ho extends Piece { //Hole
    public Ho(TetrisData data) {
        super(data, 10, 1);
        c[0] = 1;   r[0] = 0;
        c[1] = -1;  r[1] = 0;
        c[2] = 0;   r[2] = 1;
        c[3] = 0;   r[3] = -1;
    }
}
