package solver;

import Model.Solution;
import Model.State;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Sharaf on 21/11/2019.
 */
public class BFS extends Solver{

    private Queue<State> frontier = new LinkedList<>();

    @Override
    protected Solution solve(){
        frontier.add(initialState);

        long startTime = System.nanoTime();

        while (!frontier.isEmpty()){
            iterations++;

            State state = frontier.poll();
            exploerd.add(state);

            /*Must compare the arrays bby looping through elements as comparing objects can
            return they are not equal (visited) while they are */
            if (state.isEqualTo(goalState.getBoard())){
                solution = new Solution(state, iterations, System.nanoTime() - startTime);
                return solution;
            }

            for (State child: state.getChildren()) {
                if (!child.isInList(frontier) && !child.isInList(exploerd))
                    frontier.add(child);
            }
        }

        return null;
    }

}
