import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.*;

public class Solver {

    @Builder
    @EqualsAndHashCode
    static class State {
        Pair pos;
        Pair vel;
        @EqualsAndHashCode.Exclude
        int steps;
    }

    public Application.Solution tryToSolve(Application.GameSetup game) {
        Map<Pair, Integer> board = new HashMap<>();
        List<State> visitedAlready = new ArrayList<>();
        board.put(game.getStartPos(), 0);
        game.getObstacles().forEach(o -> board.put(o, -1));
        Queue<State> nextSteps = new LinkedList<>();
        State startState = State.builder()
                .pos(Pair.builder().build())
                .vel(Pair.builder().build())
                .steps(0)
                .build();
        nextSteps.add(startState);
        visitedAlready.add(startState);
        while (!nextSteps.isEmpty()) {
            State s = nextSteps.poll();
            for (Pair v1 : getNextVelocities(s.vel)) {
                State nextState = State.builder().vel(v1).pos(s.pos.add(v1)).steps(s.steps + 1).build();
                if (!validPos(game, nextState.pos))
                    continue;
                if (board.getOrDefault(nextState.pos, 0) < 0)
                    continue;
                if (!visitedAlready.contains(nextState)) {
                    board.put(nextState.pos, nextState.steps);
                    if (game.getEndPos().equals(nextState.pos)) {
                        return Application.Solution.builder()
                                .solvable(true)
                                .numberOfHops(nextState.steps)
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
