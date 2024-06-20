package icu.kevin.equals.equalitymethod;

import java.util.Objects;

public class PointRight {
    private final int x;
    private final int y;
    private final String desc;

    public PointRight(int x, int y, String desc) {
        this.x = x;
        this.y = y;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointRight that = (PointRight) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}