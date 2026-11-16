package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GcdTest {

    @Test
    @DisplayName("gcd(7,0): loop skipped; covers (x,13,20), (y,13,15F)")
    void gcdZeroY() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("gcd(12,8): 2 iterations; covers (x,13,16), (x,17,16), (x,17,20), (y,13,15T), (y,13,16), (y,13,17), (y,18,15), (y,18,16), (y,18,17), (tmp,16,18)")
    void gcdTwoIterations() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }
}