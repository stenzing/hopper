import lombok.Builder;
import lombok.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Application {

    @Value
    @Builder
    static class GameSetup {
        Pair startPos;
        Pair endPos;
        int maxX;
        int maxY;
        List<Pair> obstacles;
    }

    @Value
    @Builder
    static class Solution {
        int numberOfHops;
        boolean solvable;
    }


    public static void main(String[] args) {
        Solver s = new Solver();
        readInput(System.in)
                .stream()
                .map(s::tryToSolve)
                .forEach(solution -> {
                    if (!solution.isSolvable())
                        System.out.println("No solution.");
                    else {
                        System.out.printf("Optimal solution takes %d hops.%n", solution.getNumberOfHops());
                    }
                });
    }

    static List<GameSetup> readInput(InputStream stream) {
        ArrayList<GameSetup> acc = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            int setups = Integer.parseInt(reader.readLine());
            for (int i = 0; i < setups; i++) {
                GameSetup.GameSetupBuilder b = GameSetup.builder();
                String[] parts = reader.readLine().split(" ");
                b.maxX(Integer.parseInt(parts[0]));
                b.maxY(Integer.parseInt(parts[1]));
                String[] positions = reader.readLine().split(" ");
                b.startPos(Pair.builder().x(Integer.parseInt(positions[0])).y(Integer.parseInt(positions[1])).build());
                b.endPos(Pair.builder().x(Integer.parseInt(positions[2])).y(Integer.parseInt(positions[3])).build());
                int nObstacles = Integer.parseInt(reader.readLine());
                b.obstacles(new ArrayList<>());
                for (int j = 0; j < nObstacles; j++) {
                    positions = reader.readLine().split(" ");
                    b.obstacles.addAll(getObstacles(
                            Integer.parseInt(positions[0]),
                            Integer.parseInt(positions[1]),
                            Integer.parseInt(positions[2]),
                            Integer.parseInt(positions[3])
                    ));
                }
                acc.add(b.build());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not initialize game");
        }
        return acc;
    }

    private static List<Pair> getObstacles(int x1, int x2, int y1, int y2) {
        ArrayList<Pair> agg = new ArrayList<>();
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                agg.add(Pair.builder().x(i).y(j).build());
            }
        }
        return agg;
    }
}
