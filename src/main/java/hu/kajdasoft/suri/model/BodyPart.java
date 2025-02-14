package hu.kajdasoft.suri.model;

public enum BodyPart {
    JOBB_COMB(5),
    JOBB_HAS(6),
    JOBB_FENEK(7),
    JOBB_KAR(0),
    BAL_COMB(1),
    BAL_HAS(2),
    BAL_FENEK(3),
    BAL_KAR(4);
    private final int index;
    BodyPart(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }
    public static BodyPart getNextBodyPart(int index) {
        for (BodyPart bodyPart : values()) {
            if (bodyPart.index == index) {
                return bodyPart;
            }
        }
        throw new IllegalArgumentException("Invalid BodyPart index: " + index);
    }
}
