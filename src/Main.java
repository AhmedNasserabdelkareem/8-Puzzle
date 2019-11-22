import Model.Solution;
import Model.State;
import solver.Astar;
import solver.BFS;
import solver.DFS;

public class Main {
    public static void main(String[] args) {
        final int statesPerLine = 23;
        BFS bfs = new BFS();
        DFS dfs = new DFS();
        Astar astarMan = new Astar(false);
        Astar astarEuc = new Astar(true);

//        int [][] initBoard = {{1,2,5}
//                             ,{3,4,0}
//                             ,{6,7,8}};

        int [][] initBoard = {{3,5,1}
                             ,{2,0,8}
                             ,{7,4,6}};

        int [][] goalBoard = {{0,1,2}
                             ,{3,4,5}
                             ,{6,7,8}};


        State initialState = new State(initBoard, null);
        State goalState = new State(goalBoard, null);

        //BFS
        Solution BFSsol = bfs.getSolutionLeafState(initialState, goalState);
        if (BFSsol != null)
            BFSsol.showSolutionInline(statesPerLine, "BFS");

        //DFS
//        Solution DFSsol = dfs.getSolutionLeafState(initialState, goalState);
//        if (DFSsol != null)
//            DFSsol.showSolutionInline(statesPerLine, "DFS");

        //A* Manhattan
        Solution manSol = astarMan.getSolutionLeafState(initialState, goalState);
        if (manSol != null)
            manSol.showSolutionInline(statesPerLine, "A* Manhattan Distance");

        //A* Euclidean
        Solution eucSol = astarEuc.getSolutionLeafState(initialState, goalState);
        if (eucSol != null)
            eucSol.showSolutionInline(statesPerLine, "A* Euclidean Distance");

    }
}
