import lombok.Builder;

import java.util.*;

public class Solver {

    @Builder
    static class State {
        Pair pos;
        Pair vel;
        int steps;
    }

    public Application.Solution tryToSolve(Application.GameSetup game) {
        Map<Pair, Integer> board = new HashMap<>();
        board.put(game.getStartPos(), 0);
        Queue<State> nextSteps = new LinkedList<>();
        nextSteps.add(State.builder()
                .pos(Pair.builder().build())
                .vel(Pair.builder().build())
                .steps(0)
                .build());
        while (!nextSteps.isEmpty()) {
            State s = nextSteps.poll();
            for (Pair v1 : getNextVelocities(s.vel)) {
                State nextState = State.builder().vel(v1).pos(s.pos.add(v1)).steps(s.steps + 1).build();
                if (!validPos(game, nextState.pos))
                    continue;
                if (!board.containsKey(nextState.pos)) {
                    board.put(nextState.pos, nextState.steps);
                    if (board.containsKey(game.getEndPos())) {
                        return Application.Solution.builder()
                                .solvable(true)
                                .numberOfHops(board.get(game.getEndPos()))
                                .build();
                    } else {
                        nextSteps.offer(nextState);
                    }
                }
            }
        }

        return Application.Solution.builder().solvable(false).build();
    }

    private boolean validPos(Application.GameSetup game, Pair pos) {
        return pos.getX() >= 0 && pos.getX() <= game.getMaxX() &&
                pos.getY() >= 0 && pos.getY() <= game.getMaxY();
    }


    List<Pair> getNextVelocities(Pair vel) {
        ArrayList<Pair> agg = new ArrayList<>();
        for (int i = vel.getX() - 1; i <= vel.getX() + 1; i++) {
            for (int j = vel.getY() - 1; j <= vel.getY() + 1; j++) {
                agg.add(Pair.builder().x(i).y(j).build());
            }
        }
        return agg;
    }
}
