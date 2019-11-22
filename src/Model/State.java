package Model;

import java.util.Collection;
import java.util.LinkedList;

public class State implements Comparable<State>{

    private int ROWS, COLUMNS;

    private State parent;
    private LinkedList<State> children;
    private int [][] board;

    //indices of the empty cell and which cell is swapped with it
    private int emptyCellI, emptyCellJ, movedCellI, movedCellJ;
    private int depth;

    //distance to goad state
    private Integer distance;

    public int getParity() {
        return parity;
    }

    private int parity;

    public int getDepth() {
        return depth;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getMovedCellI() {
        return movedCellI;
    }

    public int getMovedCellJ() {
        return movedCellJ;
    }

    public int getEmptyCellI() {
        return emptyCellI;
    }

    public int getEmptyCellJ() {
        return emptyCellJ;
    }

    public State (int[][] board, State parent){
        this.board = board;
        this.parent = parent;
        ROWS = board.length;
        COLUMNS = board[0].length;
        getEmptyCellIndices();
        this.movedCellI = this.emptyCellI;
        this.movedCellJ = this.emptyCellJ;
        parity = getInvCount(board)%2;
        depth = 1;
    }

    private State (int[][] board, State parent, int emptyCellI, int emptyCellJ, int movedCellI, int movedCellJ, int depth){
        this.board = board;
        this.parent = parent;

        this.emptyCellI = emptyCellI;
        this.emptyCellJ = emptyCellJ;
        this.movedCellI = movedCellI;
        this.movedCellJ = movedCellJ;

        this.depth = depth;

        ROWS = board.length;
        COLUMNS = board[0].length;
    }

    public int[][] getBoard(){
        return this.board;
    }

    public State getParent(){
        return this.parent;
    }

    public LinkedList<State> getChildren(){
        generateChilren();
        return this.children;
    }

    /*
    Sample State cases
    0  1  2  ---  1  2  3  ---  1  0  2
    5  4  7  ---  4  0  5  ---  3  4  5
    3  9  6  ---  6  7  8  ---  6  7  8
     */

    private void generateChilren() {
        this.children = new LinkedList<>();

        addChild(swapCellWithEmpty(emptyCellI - 1, emptyCellJ), emptyCellI - 1, emptyCellJ);//swap with up
        addChild(swapCellWithEmpty(emptyCellI + 1, emptyCellJ), emptyCellI + 1, emptyCellJ);//swap with down
        addChild(swapCellWithEmpty(emptyCellI, emptyCellJ - 1), emptyCellI, emptyCellJ - 1);//swap with left
        addChild(swapCellWithEmpty(emptyCellI, emptyCellJ + 1), emptyCellI, emptyCellJ + 1);//swap with right
    }

    private void getEmptyCellIndices(){
        boolean foundEmptyCell = false;

        for (int i = 0; i < ROWS && !foundEmptyCell; i++)
            for (int j = 0; j < COLUMNS && !foundEmptyCell; j++)
                if (board[i][j] == 0) {
                    foundEmptyCell = true;
                    this.emptyCellI = i;
                    this.emptyCellJ = j;
                }

    }

    private int[][] swapCellWithEmpty (int i2, int j2){
        if (i2 < 0 || i2 >= ROWS || j2 < 0 || j2 >= COLUMNS)
            return null;

        int[][] swapped = new int[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (i == emptyCellI && j == emptyCellJ)
                    swapped[i][j] = this.board[i2][j2];
                else if (i == i2 && j == j2)
                    swapped[i][j] = this.board[emptyCellI][emptyCellJ];
                else
                    swapped[i][j] = this.board[i][j];
            }
        }
        return swapped;
    }

    private void addChild(int[][] board, int movedCellI, int movedCellJ){
        if (board == null)
            return;

        this.children.addLast(new State(board, this,movedCellI, movedCellJ, emptyCellI, emptyCellJ, depth + 1));
    }

    public void showBoard(){
        System.out.println("Board");
        System.out.println("-----");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.printf(this.board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void showChildren(){
        if (children == null || children.isEmpty())
            generateChilren();

        System.out.println("Children States");
        System.out.println("---------------");

        int i = 1;
        for (State child: children) {
            System.out.println("Child " + i++);
            child.showBoard();
        }
    }

    public void showChildrenInline(){
        if (children == null || children.isEmpty())
            generateChilren();

        System.out.println("Children States");
        System.out.println("---------------");

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < children.size(); j++) {
                for (int k = 0; k < COLUMNS; k++) {
                    System.out.printf(children.get(j).getBoard()[i][k] + " ");
                }
                System.out.printf("\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isEqualTo(int[][] state){
        if (state.length != ROWS || state[0].length != COLUMNS)
            return false;

        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLUMNS; j++)
                if (this.board[i][j] != state[i][j])
                    return false;

        return true;
    }

    public boolean isInList(Collection<State> list){
        for (State s: list) {
            if (this.isEqualTo(s.getBoard()))
                return true;
        }

        return false;
    }

    @Override
    public int compareTo(State o) {
        return this.distance.compareTo(o.distance);
    }

    private int getInvCount(int[][] arr) {
        int inv_count = 0;
        for (int i = 0; i < COLUMNS - 1; i++)
            for (int j = i + 1; j < COLUMNS; j++)
                if (arr[j][i] > 0 && arr[j][i] > 0 && arr[j][i] > arr[i][j]) {
                    inv_count++;
                }
        return inv_count;
    }
}
