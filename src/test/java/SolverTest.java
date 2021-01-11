import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SolverTest {

    Solver target = new Solver();

    @Test
    void solveBasic() {
        Application.Solution result = target.tryToSolve(Application.GameSetup.builder()
                .startPos(Pair.builder().x(0).y(0).build())
                .endPos(Pair.builder().x(1).y(0).build())
                .maxX(1)
                .maxY(0)
                .obstacles(List.of())
                .build());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isSolvable());
        Assertions.assertEquals(1, result.getNumberOfHops());
    }

    @Test
    void checkNextVelocity() {
        List<Pair> result = target.getNextVelocities(Pair.builder().x(0).y(0).build());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(9, result.size());
    }
}
