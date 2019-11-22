package solver;

import Model.Solution;
import Model.State;

import java.util.PriorityQueue;

/**
 * Created by Sharaf on 21/11/2019.
 */
public class Astar extends Solver {

    private PriorityQueue<State> frontier = new PriorityQueue<>();
    private boolean usingEuclidean;

    //false means Manhattan Distance will be used
    public Astar(boolean usingEuclidean) {
        this.usingEuclidean = usingEuclidean;
    }

    @Override
    protected Solution solve() {
        if (usingEuclidean)
            calculateEuclidean(initialState);
        else
            calculateManhattan(initialState);
        frontier.add(initialState);
        long startTime = System.nanoTime();

        while (!frontier.isEmpty()) {
            iterations++;

            State state = frontier.poll();
            exploerd.add(state);

            /*Must compare the arrays bby looping through elements as comparing objects can
            return they are not equal (visited) while they are */
            if (state.isEqualTo(goalState.getBoard())) {
                solution = new Solution(state, iterations, System.nanoTime() - startTime);
                return solution;
            }

            for (State child : state.getChildren()) {
                if (child.isInList(frontier) || !child.isInList(exploerd)) {
                    calculateDistance(child);
                    frontier.add(child);
                }
            }
        }
        return null;
    }

    private void calculateDistance(State child) {
        if (usingEuclidean)
            calculateEuclidean(child);
        else
            calculateManhattan(child);
    }

    private void calculateManhattan(State child) {
        child.setDistance(child.getDepth() + Math.abs(child.getEmptyCellI() - goalState.getEmptyCellI())
                + Math.abs(child.getEmptyCellJ() - goalState.getEmptyCellJ()));
    }

    private void calculateEuclidean(State child) {
        child.setDistance(child.getDepth() + (int) Math.sqrt(Math.pow(child.getEmptyCellI() - goalState.getEmptyCellI(), 2)
                + Math.pow(child.getEmptyCellJ() - goalState.getEmptyCellJ(), 2)));
    }
}
