package com.college.management.util;

import java.util.List;

public class TableFormatter {

    public static void printTable(String[] headers, List<String[]> rows) {
        int[] widths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            widths[i] = headers[i].length();
        }
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null && row[i].length() > widths[i]) {
                    widths[i] = row[i].length();
                }
            }
        }

        printDivider(widths);
        
        System.out.print("|");
        for (int i = 0; i < headers.length; i++) {
            System.out.print(" " + padRight(headers[i], widths[i]) + " |");
        }
        System.out.println();
        
        printDivider(widths);
        
        for (String[] row : rows) {
            System.out.print("|");
            for (int i = 0; i < row.length; i++) {
                System.out.print(" " + padRight(i < row.length ? row[i] : "", widths[i]) + " |");
            }
            System.out.println();
        }
        
        printDivider(widths);
    }

    public static void printDivider(int[] widths) {
        System.out.print("+");
        for (int w : widths) {
            for (int i = 0; i < w + 2; i++) {
                System.out.print("-");
            }
            System.out.print("+");
        }
        System.out.println();
    }

    public static String padRight(String text, int length) {
        return String.format("%-" + length + "s", text == null ? "" : text);
    }
}
