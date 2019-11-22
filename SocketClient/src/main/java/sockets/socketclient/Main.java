package sockets.socketclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
  private SocketClient socket;

  public Main() {
    this.socket = new SocketClient("127.0.0.1", 4030);
  }

  public static void main(String[] args) {
    new Main().Run();
  }

  public void SendMatrix(Double[][] matrix) throws IOException {
    int cols = matrix[0].length;
    int rows = matrix.length;
    this.socket.SendMsg(cols);
    this.socket.SendMsg(rows);
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        this.socket.SendMsg(matrix[row][col].toString());
      }
    }
  }

  public void Run() {
    try {
      Double[][] m1 = {{1.0,0.0,2.0},{-1.0,3.0,1.0}};
      Double[][] m2 = {{3.0,1.0},{2.0,1.0},{1.0,0.0}};
      
      this.socket.Begin();
      
      this.SendMatrix(m1);
      this.SendMatrix(m2);
      this.socket.SendMsg(".");

      List<Double> listDoubles = Arrays.asList(
        this.socket.readStringsUltil(".")
          .stream()
          .map((String str) -> { return Double.parseDouble(str); })
          .toArray(Double[]::new)
      );
      ArrayList<Double> arrayDoubles = new ArrayList<>(listDoubles);
      
      int matrix_cols = (int)Math.round(arrayDoubles.get(0));
      arrayDoubles.remove(0);
      int matrix_rows = (int)Math.round(arrayDoubles.get(0));
      arrayDoubles.remove(0);

      Matrix<Double> matrixResult = new Matrix<>(matrix_cols, matrix_rows, arrayDoubles);
      Matrix.PrintMatrix(matrixResult.toArray());

      this.socket.End();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

}