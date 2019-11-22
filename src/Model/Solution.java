package Model;

import java.util.LinkedList;

/**
 * Created by Sharaf on 21/11/2019.
 */
public class Solution {

    private static final String MOVINGCELL = "\u001B[33m";
    private static final String NORMALCELL = "\u001B[31m";
    private static final String RESETCOLOR = "\u001B[0m";

    private State leafState;
    private long iterations, time;
    private LinkedList<State> path = new LinkedList<>();

    public Solution(State leafState, long iterations, long time) {
        this.leafState = leafState;
        this.iterations = iterations;
        this.time = time / 1000000;

        State temp = leafState;
        while (temp != null) {
            path.addFirst(temp);
            temp = temp.getParent();
        }
    }

    public State getLeafState() {
        return leafState;
    }

    public long getIterations() {
        return iterations;
    }

    public LinkedList<State> getPath() {
        return path;
    }

    public void showSolution() {
        System.out.println("Solution");
        for (State state : path) {
            System.out.print("-----\n");
            state.showBoard();
        }
    }

    public int getDepth() {
        return path.size();
    }

    public void showSolutionInline(int statesPerLine, String method) {
        if (statesPerLine <= 0)
            statesPerLine = 1;

        State currentPrintedState, nextState;

        int lineNum = (int) Math.ceil(path.size() / (double) statesPerLine);

        System.out.println("Solution Using ( " + NORMALCELL + method + RESETCOLOR
                + " ) In ( " + NORMALCELL + (path.size() - 1) + RESETCOLOR + " ) Steps,"
                + " Explored ( " + NORMALCELL + iterations + RESETCOLOR + " ) States"
                + " In ( " + NORMALCELL + time + RESETCOLOR + " ) ms"
                + " With search depth ( " + NORMALCELL + leafState.getDepth() + RESETCOLOR + " )\n");

        for (int line = 0; line < lineNum; line++) {
            for (int i = 0; i < leafState.getBoard().length; i++) {
                for (int j = 0; j < statesPerLine; j++) {
                    for (int k = 0; k < leafState.getBoard()[0].length; k++) {
                        if (j + line * statesPerLine >= path.size())
                            continue;

                        nextState = currentPrintedState = path.get(j + line * statesPerLine);
                        if (j + line * statesPerLine + 1 < path.size())
                            nextState = path.get(j + line * statesPerLine + 1);
                        if (currentPrintedState.getBoard()[i][k] == 0) {
                            System.out.printf("  ");
                        } else {
                            if (nextState.getEmptyCellI() == i && nextState.getEmptyCellJ() == k)
                                System.out.printf(MOVINGCELL + currentPrintedState.getBoard()[i][k] + " ");
                            else
                                System.out.printf(NORMALCELL + currentPrintedState.getBoard()[i][k] + " ");
                        }
                    }
                    System.out.printf("\t");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.printf(RESETCOLOR);
    }

}
