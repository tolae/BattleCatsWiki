package com.spitfirex2.battlecatsmasterwiki.util;

public class MutablePair<L, R> {
    public L left;
    public R right;

    public MutablePair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MutablePair)) return false;
        MutablePair pair = (MutablePair) o;
        return this.left.equals(pair.left) &&
                this.right.equals(pair.right);
    }
}
