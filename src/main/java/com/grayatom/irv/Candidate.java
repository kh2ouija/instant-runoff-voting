package com.grayatom.irv;

import java.util.Objects;

public class Candidate<K> {

    private K key;

    public Candidate(K key) {
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate<?> candidate = (Candidate<?>) o;
        return Objects.equals(key, candidate.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return key.toString();
    }
}
