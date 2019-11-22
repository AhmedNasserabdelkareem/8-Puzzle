package solver;

import Model.Solution;
import Model.State;

import java.util.Stack;

/**
 * Created by Sharaf on 21/11/2019.
 */
public class DFS extends Solver {

    private Stack<State> frontier = new Stack<>();

    @Override
    protected Solution solve() {
        frontier.add(initialState);

        long startTime = System.nanoTime();

        while (!frontier.isEmpty()){
            ++iterations;

            State state = frontier.pop();
            exploerd.add(state);

            /*Must compare the arrays bby looping through elements as comparing objects can
            return they are not equal (visited) while they are */
            if (state.isEqualTo(goalState.getBoard())){
                solution = new Solution(state, iterations, System.nanoTime() - startTime);
                return solution;
            }

            for (State child: state.getChildren()) {
                if (!child.isInList(frontier) && !child.isInList(exploerd))
                    frontier.push(child);
            }
        }

        return null;

    }
}
