package com.yerokha.neotour.util;

public class Months {

    public static final int JAN = 1 << 0;
    public static final int FEB = 1 << 1;
    public static final int MAR = 1 << 2;
    public static final int APR = 1 << 3;
    public static final int MAY = 1 << 4;
    public static final int JUN = 1 << 5;
    public static final int JUL = 1 << 6;
    public static final int AUG = 1 << 7;
    public static final int SEP = 1 << 8;
    public static final int OCT = 1 << 9;
    public static final int NOV = 1 << 10;
    public static final int DEC = 1 << 11;

    public static final int[] ALL_ARR = {JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG,
            SEP, OCT, NOV, DEC};

    public static final int ALL = JAN | FEB | MAR | APR | MAY | JUN |
            JUL | AUG | SEP | OCT | NOV | DEC;
}
