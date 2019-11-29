/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package krypto;

/**
 *
 * @author dell
 */
public class Plotkowy {

    private final int sizeOfHurdle;
    String[][] hashArray;

    public Plotkowy(int sizeOfHurdle) {
        this.sizeOfHurdle = sizeOfHurdle;
    }

    public String hash(String textToHash) {
        char[] textInCharTable = textToHash.toCharArray();
        String tempText = "";
        int row = 0;
        int col = 0;
        boolean down = true;
        String[][] text = new String[sizeOfHurdle][textInCharTable.length];
        for (int i = 0; i < textInCharTable.length; i++) {
            if (row == 0) {
                down = true;
            }
            if ((row < sizeOfHurdle | row == 0) && down == true) {
                text[row][col] = String.valueOf(textInCharTable[i]);
                if (row == sizeOfHurdle - 1) {
                } else {
                    row++;
                }
                col++;
            }
            if (down == false) {
                text[row][col] = String.valueOf(textInCharTable[i]);
                row--;
                col++;
            }
            if (row == sizeOfHurdle - 1) {
                down = false;
            }

        }
        for (String[] text1 : text) {
            for (int j = 0; j < text[0].length; j++) {
                if (text1[j] != null) {
                    tempText += text1[j];
                }
            }
        }
        return tempText;
    }

    public String decode(String txt) {
        String textAfterDecode = "";

        int[] rows = new int[txt.length()];
        int[] cols = new int[txt.length()];

        char[] textInCharTable = txt.toCharArray();
        int row = 0;
        int col = 0;
        boolean down = true;
        String[][] text = new String[sizeOfHurdle][textInCharTable.length];
        for (int i = 0; i < textInCharTable.length; i++) {
            if (row == 0) {
                down = true;
            }
            if ((row < sizeOfHurdle | row == 0) && down == true) {
                rows[i] = row;
                cols[i] = col;
                if (row == sizeOfHurdle - 1) {
                } else {
                    row++;
                }
                col++;
            }
            if (down == false) {
                rows[i] = row;
                cols[i] = col;
                row--;
                col++;
            }
            if (row == sizeOfHurdle - 1) {
                down = false;
            }
        }
        int iter = 0;
        for (int i = 0; i < sizeOfHurdle; i++) {
            for (int j = 0; j < rows.length; j++) {
                if (rows[j] == i) {
                    text[rows[j]][cols[j]] = String.valueOf(txt.charAt(iter));
                    iter++;
                }
            }
        }

        for (int i = 0; i < text[0].length; i++) {
            for (String[] text1 : text) {
                if (text1[i] != null) {
                    textAfterDecode += text1[i];
                }
            }
        }
        return textAfterDecode;
    }

}
