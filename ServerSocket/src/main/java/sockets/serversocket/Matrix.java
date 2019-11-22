package sockets.serversocket;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Matrix
 */
public class Matrix<T> {
    protected int cols;
    protected int rows;
    protected ArrayList<ArrayList<T>> Matrix;

    public Matrix(final T[][] matrix) {
        this.cols = matrix[0].length;
        this.rows = matrix.length;
        this.Matrix = new ArrayList<ArrayList<T>>();
        for (int row = 0; row < this.rows; row++) {
            final ArrayList<T> list = new ArrayList<>();
            for (int col = 0; col < this.cols; col++) {
                list.add(matrix[row][col]);
            }
            this.Matrix.add(list);
        }
    }

    public Matrix(final int cols, final int rows, final List<T> ODvalues) {
        this.cols = cols;
        this.rows = rows;
        this.Matrix = new ArrayList<ArrayList<T>>();
        for (int row = 0; row < this.rows; row++) {
            final ArrayList<T> list = new ArrayList<>();
            for (int col = 0; col < this.cols; col++) {
                list.add(ODvalues.get((row * cols) + col));
            }
            this.Matrix.add(list);
        }
    }

    public Matrix(final int cols, final int rows) {
        this.cols = cols;
        this.rows = rows;
        this.Matrix = new ArrayList<ArrayList<T>>();
        for (int row = 0; row < this.rows; row++) {
            final ArrayList<T> list = new ArrayList<>();
            for (int col = 0; col < this.cols; col++) {
                list.add(null);
            }
            this.Matrix.add(list);
        }
    }

    private void ValidateColRowIndex(final int col, final int row) throws RuntimeException {
        if (col < 0 || this.cols <= col) { throw new RuntimeException("Col out of index"); }
        if (row < 0 || this.rows <= row) { throw new RuntimeException("Row out of index"); }
    }

    public T get(final int col, final int row) {
        this.ValidateColRowIndex(col, row);
        final T var = this.Matrix.get(row).get(col);
        return var;
    }

    public void set(final int col, final int row, final T value) {
        this.ValidateColRowIndex(col, row);
        final ArrayList<T> aList = this.Matrix.get(row);
        aList.set(col, value);
        this.Matrix.set(row, aList);
    }

    public T[][] toArray() {
        T[][] matrix = (T[][])Array.newInstance(this.get(0, 0).getClass(), this.rows, this.cols);
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                matrix[row][col] = this.get(col, row);
            }
        }
        return matrix;
    }

    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < this.rows; row++) {
            stringBuilder.append("[ ");
            for (int col = 0; col < this.cols; col++) {
                stringBuilder.append(this.get(col, row));
                stringBuilder.append(" ");
            }
            stringBuilder.append("]\n");
        }
        return stringBuilder.toString();
    }

    static public Double[][] GetMatrix(int cols, int rows, List<Double> list) {
        Double[][] arrDoubles = new Double[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                arrDoubles[row][col] = list.get((row * rows) + col);
            }
        }
        return arrDoubles;
    }
  
    static public void PrintMatrix(int cols, int rows, Double[][] matrix) {
      for (int row = 0; row < rows; row++) {
        System.out.print("[ ");
        for (int col = 0; col < cols; col++) {
          System.out.printf("%02.1f ", matrix[row][col]);
        }
        System.out.println("]");
      }
    }
}