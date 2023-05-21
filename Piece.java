import java.util.*;

public class Piece {
    public char c;
    public int h, w, x, y;
    public boolean[][] shape;
    private int hashCode;

    Piece(char c, boolean[][] shape) {
        this.c = c;
        this.h = shape.length;
        this.w = shape[0].length;
        this.shape = shape;
        this.initializeTopLeftPosition();
        this.hashCode = calculateHashCode(shape);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return this.hashCode() == ((Piece) obj).hashCode();
    }

    public void print() {
        for (boolean[] row: shape) {
            for (boolean cell: row) {
                System.out.print(cell ? c : ' ');
            }
            System.out.println();
        }
        System.out.println();
    }

    private void initializeTopLeftPosition() {
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                if (shape[i][j]) {
                    this.x = i;
                    this.y = j;
                    return;
                }
            }
        }
    }

    private Piece flip() {
        boolean[][] flipShape = new boolean[h][w];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                flipShape[i][w - 1 - j] = shape[i][j];
            }
        }
        return new Piece(c, flipShape);
    }

    private Piece rotate90Degrees() {
        boolean[][] rotatedShape = new boolean[w][h];
        for (int i = 0; i < h; ++i) {
            for (int j = 0; j < w; ++j) {
                rotatedShape[j][h - 1 - i] = shape[i][j];
            }
        }

        return new Piece(c, rotatedShape);
    }

    private int calculateHashCode(boolean[][] shape) {
        StringBuffer sb = new StringBuffer();
        for (boolean[] row: shape) {
            for (boolean cell: row) {
                if (cell) sb.append('T');
                else sb.append('F');
            }
        }
        return sb.toString().hashCode();
    }

    public static List<Piece> getRotatedPiece(Piece piece, boolean flip) {
        int rotateTimes = 3;
        Set<Piece> pieceSet = new HashSet<>();
        pieceSet.add(piece);

        while (rotateTimes-- > 0) {
            piece = piece.rotate90Degrees();
            pieceSet.add(piece);
        }

        if (flip) pieceSet.addAll(getRotatedPiece(piece.flip(), false));
        return new ArrayList<>(pieceSet);
    }
}
