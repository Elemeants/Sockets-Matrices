package sockets.serversocket;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Main
 */
public class Main {
  private SocketServer socketServer;

  public Main() throws IOException {
    this.socketServer = new SocketServer(4030);
  }

  public Double[][] MultiplicacionMatrix(List<Double> arrayDoubles) {
    int matrx1_cols = (int)Math.round(arrayDoubles.get(0));
    int matrx1_rows = (int)Math.round(arrayDoubles.get(1));
    int matrx1_size_index = (matrx1_cols * matrx1_rows) + 2;
    Double[][] matrx1 = MatrixOperations.GetMatrix(matrx1_cols, matrx1_rows, arrayDoubles.subList(2, matrx1_size_index));

    int matrx2_cols = (int)Math.round(arrayDoubles.get(matrx1_size_index));
    int matrx2_rows = (int)Math.round(arrayDoubles.get(matrx1_size_index + 1));
    int matrx2_size_index = (matrx2_cols * matrx2_rows) + matrx1_size_index + 2;
    Double[][] matrx2 = MatrixOperations.GetMatrix(matrx2_cols, matrx2_rows, arrayDoubles.subList(matrx1_size_index + 2, matrx2_size_index));

    if (matrx1_cols != matrx2_rows) { throw new RuntimeException("Las matrices no tiene el tamaño correcto"); }
    
    Double[][] matrixResult = new Double[matrx1_rows][matrx2_cols];

    for (int x=0; x < matrx1_rows; x++) {
      for (int y=0; y < matrx2_cols; y++) {
        matrixResult [x][y] = 0.0;
      }
    }
    
    for (int x=0; x < matrx1_rows; x++) {
      for (int y=0; y < matrx2_cols; y++) {
        for (int z=0; z < matrx1_cols; z++) {
          matrixResult [x][y] += matrx1[x][z] * matrx2[z][y]; 
        }
      }
    }

    MatrixOperations.PrintMatrix(matrx1_cols, matrx1_rows, matrx1);
    MatrixOperations.PrintMatrix(matrx2_cols, matrx2_rows, matrx2);
    MatrixOperations.PrintMatrix(matrx2_cols, matrx1_rows, matrixResult);
    return matrixResult;
  }

  public void SendMatrix(int cols, int rows, Double[][] matrix) throws IOException {
    this.socketServer.SendMsg(rows);
    this.socketServer.SendMsg(cols);
    for (int col = 0; col < cols; col++) {
      for (int row = 0; row < rows; row++) {
        this.socketServer.SendMsg(matrix[row][col].toString());
      }
    }
  }

  public void Run() throws IOException {
    while(true) {
      try {
        this.socketServer.Begin();
  
        List<Double> inputDoubles = Arrays.asList(
          this.socketServer.readArrayListUntil(".")
            .stream()
            .map((String str) -> { return Double.parseDouble(str); })
            .toArray(Double[]::new)
        );
        Double[][] matrix = this.MultiplicacionMatrix(inputDoubles);
        SendMatrix(matrix.length, matrix[0].length, matrix);
        this.socketServer.SendMsg(".");

        this.socketServer.End();
      } catch (Exception ex) {
        System.err.println(ex.getMessage());
        this.socketServer.End();
      }
    }
  }

  public static void main(String[] args) {
    try {
      new Main().Run();
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
  }
}