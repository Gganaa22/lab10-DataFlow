package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BonusTest {

    @Test
    @DisplayName("ALLDEFS-1: [1,1,1,1,1,0,1], thr 3; defs 35,36,37,38,40,41,43,46,51,54 reach uses")
    void allDefsLongStreakThenReset() {
        assertEquals(8, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1, 0, 1}, 3));
    }

    @Test
    @DisplayName("ALLDEFS-2: [], thr 1; def 49 reaches use 56 via 50F, 53F")
    void allDefsEmptyBelowThreshold() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {}, 1));
    }
}