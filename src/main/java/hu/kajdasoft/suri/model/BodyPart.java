package hu.kajdasoft.suri.model;

public enum BodyPart {
    JOBB_COMB(7),
    JOBB_HAS(0),
    JOBB_FENEK(1),
    JOBB_KAR(2),
    BAL_COMB(3),
    BAL_HAS(4),
    BAL_FENEK(5),
    BAL_KAR(6);
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
