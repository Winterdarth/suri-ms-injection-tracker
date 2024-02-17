package hu.kajdasoft.suri.model;

public enum BodyPart {
    RIGHT_THIGH,
    RIGHT_BELLY,
    RIGHT_BUTT,
    RIGHT_ARM,
    LEFT_THIGH,
    LEFT_BELLY,
    LEFT_BUTT,
    LEFT_ARM;

    private static BodyPart[] values = values();
    private static int index = 0;

    public static BodyPart getNextBodyPart() {
        BodyPart nextBodyPart = values[index];
        index = (index + 1) % values.length;
        return nextBodyPart;
    }

    public static void setIndex(int index) {
        BodyPart.index = index;
    }
}
