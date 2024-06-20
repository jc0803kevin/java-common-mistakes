package icu.kevin.equals.equalitymethod;

public class PointWrong {
    private final String desc;
    private int x;
    private int y;

    public PointWrong(int x, int y, String desc) {
        this.x = x;
        this.y = y;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        PointWrong that = (PointWrong) o;
        return x == that.x && y == that.y;
    }
}
