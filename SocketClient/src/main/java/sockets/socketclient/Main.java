package sockets.socketclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main
 */
public class Main {
  private SocketClient socket;

  public Main() {
    this.socket = new SocketClient("127.0.0.1", 4030);
  }

  public static void main(String[] args) {
    new Main()
      .Run();
  }

  public void SendMatrix(int cols, int rows, Double[][] matrix) throws IOException {
    this.socket.SendMsg(cols);
    this.socket.SendMsg(rows);
    for (int col = 0; col < cols; col++) {
      for (int row = 0; row < rows; row++) {
        this.socket.SendMsg(matrix[row][col].toString());
      }
    }
  }

  public void Run() {
    try {
      Double[][] m1 = {{1.0,0.0,2.0},{-1.0,3.0,1.0}};
      Double[][] m2 = {{3.0,1.0},{2.0,1.0},{1.0,0.0}};
      
      this.socket.Begin();
      
      this.SendMatrix(m1[0].length, m1.length, m1);
      this.SendMatrix(m2[0].length, m2.length, m2);
      this.socket.SendMsg(".");
      List<Double> arrayDoubles = Arrays.asList(
        this.socket.readArrayListUntil(".")
          .stream()
          .map((String str) -> { return Double.parseDouble(str); })
          .toArray(Double[]::new)
      );

      int matrix_cols = (int)Math.round(arrayDoubles.get(0));
      int matrix_rows = (int)Math.round(arrayDoubles.get(1));
      int matrix_size_index = (matrix_cols * matrix_rows) + 2;
      Double[][] matrix = MatrixOperations.GetMatrix(matrix_cols, matrix_rows, arrayDoubles.subList(2, matrix_size_index));
      MatrixOperations.PrintMatrix(matrix_cols, matrix_rows, matrix);

      this.socket.End();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

}