import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Pair implements Comparable<Pair> {
    int x;
    int y;

    public Pair add(Pair o) {
        return Pair.builder().x(x+o.x).y(y+o.y).build();
    }

    public int hashCode() {
        return x+y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (x != pair.x) return false;
        return y == pair.y;
    }

    @Override
    public int compareTo(Pair o) {
        if (x<o.x)
            return -1;
        else if (x>o.x)
            return 1;
        else
            return Integer.compare(y, o.y);
    }
}
