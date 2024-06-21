package hu.kajdasoft.suri.model;

public enum BodyPart {
    JOBB_COMB(0),
    JOBB_HAS(1),
    JOBB_FENEK(2),
    JOBB_KAR(3),
    BAL_COMB(4),
    BAL_HAS(5),
    BAL_FENEK(6),
    BAL_KAR(7);

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
