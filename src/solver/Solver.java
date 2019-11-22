package solver;

import Model.Solution;
import Model.State;

import java.util.LinkedList;

/**
 * Created by Sharaf on 22/11/2019.
 */
public abstract class Solver {

    State initialState, goalState;
    Solution solution;
    long iterations;

    LinkedList<State> exploerd;

    public Solution getSolutionLeafState(State initalState, State goalState){

        //incase of calling more than one time with same states
        if (initalState == this.initialState && goalState == this.goalState && this.solution != null)
            return this.solution;

        if (initalState.getBoard().length != goalState.getBoard().length ||
                initalState.getBoard()[0].length != goalState.getBoard()[0].length){

            System.out.println("Invalid game coordinates");
            return null;
        }

        this.initialState = initalState;
        this.goalState = goalState;

        exploerd = new LinkedList<>();

        return solve();
    }

    protected abstract Solution solve();

}
