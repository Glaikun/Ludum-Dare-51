package com.glaikunt.framework.application;

/**
 * Creates a pair of two objects
 *
 * @param <key> the type of object 1
 * @param <value> the type of object 2
 */
public class Pair<key, value> {

    /**
     * The first object
     */
    private final key key;

    /**
     * The second object
     */
    private value value;

    /**
     * Creates a new pair of objects
     *
     * @param first the first object
     * @param second the second object
     */
    public Pair(key first, value second) {
        this.key = first;
        this.value = second;
    }

    /**
     * @return the first object
     */
    public key getKey() {
        return this.key;
    }

    /**
     * @return the second object
     */
    public value getValue() {
        return this.value;
    }

    public void setValue(value value) {
        this.value = value;
    }

    public static <key, value> Pair<key, value> of(key first, value second) {
        return new Pair<>(first, second);
    }
}
