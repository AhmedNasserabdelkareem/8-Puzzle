package Model;

public class State {
    private int [][] board;
    private final static int COLOUMNS = 3 ;
    private final static int ROWS = 3 ;

    public State(int[][] board) {
        board =  new int [COLOUMNS][ROWS];
        this.board = board;
    }
}
