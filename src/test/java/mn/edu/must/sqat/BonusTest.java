package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        @Test
    @DisplayName("DU-3: null attendance; (attendance,31,32T)")
    void nullAttendance() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 1));
    }

    @Test
    @DisplayName("DU-4: negative threshold; (threshold,31,32T)")
    void negativeThreshold() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[] {1}, -1));
    }

    @Test
    @DisplayName("DU-5: [], thr 0; (present,35,50T), (best,37,53F)")
    void emptyThresholdZero() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {}, 0));
    }

    @Test
    @DisplayName("DU-6: [1], thr 2; (present,40,50F), (best,43,53F), (49,56)")
    void onePresentBelowThreshold() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {1}, 2));
    }

    @Test
    @DisplayName("DU-7: [1,1,1,1,1], thr 6; (score,49,54): 50F then 53T")
    void streakFiveBelowThreshold() {
        assertEquals(3, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1}, 6));
    }

    @Test
    @DisplayName("DU-8: [1], thr 1 (exact threshold); (score,51,56): 50T then 53F")
    void exactThresholdShortStreak() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {1}, 1));
    }
}