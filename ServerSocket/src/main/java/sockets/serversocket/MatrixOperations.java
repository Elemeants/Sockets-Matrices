package sockets.serversocket;

import java.util.List;

/**
 * MatrixOperations
 */
public class MatrixOperations {
  static public Double[][] GetMatrix(int cols, int rows, List<Double> list) {
	  Double[][] arrDoubles = new Double[rows][cols];
    for (int col = 0; col < cols; col++) {
      for (int row = 0; row < rows; row++) {
        arrDoubles[row][col] = list.get((col * rows) + row);
      }
    }
    return arrDoubles;
  }

  static public void PrintMatrix(int cols, int rows, Double[][] matrix) {
    for (int col = 0; col < cols; col++) {
      System.out.print("[ ");
      for (int row = 0; row < rows; row++) {
        System.out.printf("%02.1f ", matrix[row][col]);
      }
      System.out.println("]");
    }
    System.out.println("");
  }
}