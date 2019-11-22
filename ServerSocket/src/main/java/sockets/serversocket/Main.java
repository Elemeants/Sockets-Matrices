package sockets.serversocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
  private SocketServer socketServer;

  public Main() throws IOException {
    this.socketServer = new SocketServer(4030);
  }

  public Double[][] MultiplicacionMatrix(ArrayList<Double> arrayDoubles) {
    int matrx1_cols = (int)Math.round(arrayDoubles.get(0));
    arrayDoubles.remove(0);
    int matrx1_rows = (int)Math.round(arrayDoubles.get(0));
    arrayDoubles.remove(0);
    Double[][] matrx1 = new Matrix<Double>(
        matrx1_cols,
        matrx1_rows,
        arrayDoubles
      ).toArray();

    int matrx1_size_index = (matrx1_cols * matrx1_rows);
    for (int i = 0; i < matrx1_size_index; i++) {  
      arrayDoubles.remove(0);
    }
  
    int matrx2_cols = (int)Math.round(arrayDoubles.get(0));
    arrayDoubles.remove(0);
    int matrx2_rows = (int)Math.round(arrayDoubles.get(0));
    arrayDoubles.remove(0);
    
    Double[][] matrx2 = new Matrix<Double>(
        matrx2_cols,
        matrx2_rows,
        arrayDoubles
      ).toArray();

    if (matrx1_cols != matrx2_rows) { throw new RuntimeException("Las matrices no tiene el tamaño correcto"); }
    
    Double[][] matrixResult = new Double[matrx2_cols][matrx1_rows];
    
    for (int x=0; x < matrx1_rows; x++) {
      for (int y=0; y < matrx2_cols; y++) {
        for (int z=0; z < matrx1_cols; z++) {
          if (matrixResult[x][y] == null) { 
            matrixResult[x][y] = 0.0;
          }
          matrixResult [x][y] += matrx1[x][z] * matrx2[z][y]; 
        }
      }
    }

    System.out.println("Matriz A");
    Matrix.PrintMatrix(matrx1_cols, matrx1_rows, matrx1);
    System.out.println("Matriz B");
    Matrix.PrintMatrix(matrx2_cols, matrx2_rows, matrx2);
    System.out.println("Matriz resultante");
    Matrix.PrintMatrix(matrx2_cols, matrx1_rows, matrixResult);
    return matrixResult;
  }

  public void SendMatrix(Double[][] matrix) throws IOException {
    int cols = matrix[0].length;
    int rows = matrix.length;
    this.socketServer.SendMsg(cols);
    this.socketServer.SendMsg(rows);
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        this.socketServer.SendMsg(matrix[row][col].toString());
      }
    }
  }

  public void Run() throws IOException {
    while(true) {
      try {
        this.socketServer.Begin();

        List<Double> listDoubles = Arrays.asList(
          this.socketServer.readArrayListUntil(".")
            .stream()
            .map((String str) -> { return Double.parseDouble(str); })
            .toArray(Double[]::new)
        );
        ArrayList<Double> inputDoubles = new ArrayList<Double>(listDoubles);
        Double[][] matrix = this.MultiplicacionMatrix(inputDoubles);
        this.SendMatrix(matrix);
        this.socketServer.SendMsg(".");

        this.socketServer.End();
      } catch (Exception ex) {
        System.err.println(ex.getMessage());
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