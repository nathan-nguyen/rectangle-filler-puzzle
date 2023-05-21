import java.util.*;

public class MapPrinter {
    private static final int RED = 0, GREEN = 1, YELLOW = 2, BLUE = 3, MAGENTA = 4, CYAN = 5,
        WHITE = 6, GRAY = 7;
    private static final List<Integer> COLORS = Arrays.asList(
        new Integer[]{RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE, GRAY}
    );

    public void print(char[][] map) {
        int n = map.length;
        int m = map[0].length;

        Map<Character, Integer> colorMap = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                char c = map[i][j];
                System.out.print((c == 0 || c == 1) ? "  " : formatCharacter(c, getCellColor(colorMap, c)));
            }
            System.out.println();
        }
        System.out.println();
    }

    private int getCellColor(Map<Character, Integer> colorMap, char c) {
        if (colorMap.get(c) == null) {
            int color = COLORS.get(colorMap.keySet().size() % COLORS.size());
            colorMap.put(c, color);
        }
        return colorMap.get(c);
    }

    private String formatCharacter(Character c, int color) {
        String s = String.valueOf(c) + c;
        String bg = BashConstant.NO_COLOR_BG;
        String textColor = BashConstant.NO_COLOR;

        switch (color) {
            case RED:
                bg = BashConstant.RED_BG;
                textColor = BashConstant.RED;
                break;
            case GREEN:
                bg = BashConstant.GREEN_BG;
                textColor = BashConstant.GREEN;
                break;
            case YELLOW:
                bg = BashConstant.YELLOW_BG;
                textColor = BashConstant.YELLOW;
                break;
            case BLUE:
                bg = BashConstant.BLUE_BG;
                textColor = BashConstant.BLUE;
                break;
            case MAGENTA:
                bg = BashConstant.MAGENTA_BG;
                textColor = BashConstant.MAGENTA;
                break;
            case CYAN:
                bg = BashConstant.CYAN_BG;
                textColor = BashConstant.CYAN;
                break;
            case WHITE:
                bg = BashConstant.WHITE_BG;
                textColor = BashConstant.WHITE;
                break;
            case GRAY:
                bg = BashConstant.GRAY_BG;
                textColor = BashConstant.GRAY;
                break;
        }
        return bg + textColor + s + BashConstant.RESET;
    }
}
