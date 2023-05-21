import java.util.*;

public class RectangleFiller {
    private static final MapPrinter MAP_PRINTER = new MapPrinter();

    private static void addPiece(Map<Character, List<Piece>> pieceMap, char c, boolean[][] shape) {
        Piece piece = new Piece(c, shape);
        pieceMap.put(c, Piece.getRotatedPiece(piece, true));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Loading pieces");
        Map<Character, List<Piece>> pieceMap = new HashMap();
        int pieceNumber = in.nextInt();
        while (pieceNumber-- > 0) {
            char c = in.next().charAt(0);
            int n = in.nextInt();
            int m = in.nextInt();
            boolean[][] shape = new boolean[n][m];
            for (int i = 0; i < n; ++i) {
                String line = in.next();
                for (int j = 0; j < m; ++j) {
                    if (line.charAt(j) == 'T') shape[i][j] = true;
                }
            }
            addPiece(pieceMap, c, shape);
        }
        System.out.println("Number of pieces: " + pieceMap.keySet().size());

        System.out.println("Loading map");
        int n = in.nextInt();
        int m = in.nextInt();
        char[][] map = new char[n][m];
        for (int i = 0; i < n; ++i) {
            String line = in.next();
            for (int j = 0; j < m; ++j) {
                char c = line.charAt(j);
                if (c == '0') map[i][j] = 0;
                else if (c == '1') map[i][j] = 1;
                else map[i][j] = c;
            }
        }

        System.out.println("Start finding solution");
        RectangleFiller solution = new RectangleFiller();
        solution.solve(map, pieceMap);
    }

    private void solve(char[][] map, Map<Character, List<Piece>> pieceMap) {
        int n = map.length;
        int m = map[0].length;
        Set<Character> unprocessedPiece = new HashSet<>(pieceMap.keySet());
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) unprocessedPiece.remove(map[i][j]);
        }
        // Smallest empty tile
        int tile = getNextUnprocessedTile(map, 0);

        boolean foundSolution = solve(map, tile, pieceMap, unprocessedPiece);
        if (foundSolution) MAP_PRINTER.print(map);
        else System.out.println("Could not find solution!");
    }

    private int getNextUnprocessedTile(char[][] map, int tile) {
        int n = map.length;
        int m = map[0].length;

        while (tile < n * m) {
            if (map[tile / m][tile % m] == 0) return tile;
            ++tile;
        }
        return -1;
    }

    private boolean fit(char[][] map, int tile, Piece piece) {
        int n = map.length;
        int m = map[0].length;

        boolean[][] shape = piece.shape;
        int x = tile / m - piece.x;
        int y = tile % m - piece.y;
        if (x < 0 || y < 0) return false;
        for (int i = 0; i < piece.h; ++i) {
            for (int j = 0; j < piece.w; ++j) {
                if (x + i >= n || y + j >= m) return false;
                if (shape[i][j] && map[x + i][y + j] != 0) return false;
            }
        }

        return true;
    }

    private void updatePieceToMap(char[][] map, int tile, Piece piece, char c) {
        int n = map.length;
        int m = map[0].length;

        boolean[][] shape = piece.shape;
        int x = tile / m - piece.x;
        int y = tile % m - piece.y;
        for (int i = 0; i < piece.h; ++i) {
            for (int j = 0; j < piece.w; ++j) {
                if (shape[i][j]) map[x + i][y + j] = c;
            }
        }
    }

    private boolean solve(char[][] map, int tile, Map<Character, List<Piece>> pieceMap,
                          Set<Character> unprocessedPiece) {
        if (unprocessedPiece.size() == 0) return true;

        for (char c: unprocessedPiece) {
            List<Piece> pieceList = pieceMap.get(c);
            Set<Character> nextUnprocessedPiece = new HashSet<>(unprocessedPiece);
            nextUnprocessedPiece.remove(c);
            for (Piece piece: pieceList) {
                if (fit(map, tile, piece)) {
                    updatePieceToMap(map, tile, piece, piece.c);
                    int nextTile = getNextUnprocessedTile(map, tile);
                    boolean foundSolution = solve(map, nextTile, pieceMap, nextUnprocessedPiece);
                    if (foundSolution) return true;
                    updatePieceToMap(map, tile, piece, (char) 0);
                }
            }
        }
        return false;
    }
}
