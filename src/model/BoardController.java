package model;

public class BoardController {

    static public String[][] createNewBoard() {
        String[][] arr = new String[15][15];
        for(int i = 0; i < 15; i++) {
            for(int j = 0; j < 15; j++) {
                arr[i][j] = "0";
            }
        }
        return  arr;
    }

    static public String parseBoardToStringJSON(String[][] arr) {
        String s = "{";
        for (int i = 0; i < 15; i++) {
            s += "{";
            for (int j = 0; j < 15; j++) {
                s += arr[i][j];
                if (j != 14) {
                    s += ",";
                }
            }
            s += "}";
            if (i != 14) {
                s += ",";
            }
        }
        s += "}";
        return s;
    }

    static public String[][] parseStringToBoard(String s) {
        String newString = s.substring(1);
        newString = removeLastChar(newString);

        String[][] arr = new String[15][15];
        String[] sss = newString.split("}");
        for(int i = 0; i < 15; i++) {
            String temp = sss[i].substring(1);
            String[] newArr = temp.split(",");
            for(int j = 0; j < 15; j++) {
                arr[i][j] = newArr[j];
            }
        }

        return arr;
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

}
