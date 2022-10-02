package com.glaikunt.framework;

public class Ansi {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static final String[] rainbow = new String[] {ANSI_RED, ANSI_YELLOW, ANSI_GREEN, ANSI_CYAN, ANSI_BLUE, ANSI_PURPLE};

    private Ansi() {}

    public static String rainbow(int i, String s) {
        return rainbow[i%rainbow.length]+s+ANSI_RESET;
    }

    public static String purple(int i) {
        return ANSI_PURPLE + i + ANSI_RESET;
    }

    public static String purple(String s) {
        return ANSI_PURPLE + s + ANSI_RESET;
    }

    public static String green(String s) {
        return ANSI_GREEN + s + ANSI_RESET;
    }

    public static String green(int i) {
        return ANSI_GREEN + i + ANSI_RESET;
    }

    public static String yellow(String s) {
        return ANSI_YELLOW+ s + ANSI_RESET;
    }

    public static String yellow(int i) {
        return ANSI_YELLOW + i + ANSI_RESET;
    }

    public static String red(String s) {
        return ANSI_RED + s + ANSI_RESET;
    }

    public static String red(int i) {
        return ANSI_RED + i + ANSI_RESET;
    }

    public static String cyan(String s) {
        return ANSI_CYAN + s + ANSI_RESET;
    }

    public static String cyan(int i) {
        return ANSI_CYAN + i + ANSI_RESET;
    }

    public static String whiteOnRed(String s) {
        return ANSI_RED_BACKGROUND+ANSI_WHITE + s + ANSI_RESET;
    }

    public static String blackOnBlue(String s) {
        return ANSI_BLUE_BACKGROUND+ANSI_BLACK + s + ANSI_RESET;
    }

    public static String blackOnPurple(String s) {
        return ANSI_PURPLE_BACKGROUND+ANSI_BLACK + s + ANSI_RESET;
    }
}
