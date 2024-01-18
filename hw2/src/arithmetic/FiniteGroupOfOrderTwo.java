package arithmetic;
import core.Group;

public class FiniteGroupOfOrderTwo implements Group<PlusOrMinusOne> {
    @Override
    public PlusOrMinusOne identity() {
        return PlusOrMinusOne.PLUS_ONE;
    }
    @Override
    public PlusOrMinusOne binaryOperation(PlusOrMinusOne a, PlusOrMinusOne b) {
        int result = a.getValue() * b.getValue();
        return (result == 1) ? PlusOrMinusOne.PLUS_ONE : PlusOrMinusOne.MINUS_ONE;
    }
    @Override
    public PlusOrMinusOne inverseOf(PlusOrMinusOne a) {
        return (a == PlusOrMinusOne.PLUS_ONE) ? PlusOrMinusOne.PLUS_ONE : PlusOrMinusOne.MINUS_ONE;
    }
    public static enum PlusOrMinusOne {
        PLUS_ONE(1),
        MINUS_ONE(-1);
        private final int value;
        PlusOrMinusOne(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        @Override
        public String toString() {
            return Integer.toString(value);
        }
    }
}
