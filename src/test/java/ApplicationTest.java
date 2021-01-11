import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {

    Application target = new Application();

    @Test
    void testBasicRead() {
        String b = "2\n" +
                "5 5\n" +
                "4 0 4 4\n" +
                "1\n" +
                "1 4 2 3\n" +
                "3 3\n" +
                "0 0 2 2\n" +
                "2\n" +
                "1 1 0 2\n" +
                "0 2 1 1\n";
        List<Application.GameSetup> games = target.readInput(new ByteArrayInputStream(b.getBytes()));
        Assertions.assertNotNull(games);
        assertEquals(2, games.size());
        assertEquals(8, games.get(0).getObstacles().size());


        assertEquals(6, games.get(1).getObstacles().size());
    }
}
