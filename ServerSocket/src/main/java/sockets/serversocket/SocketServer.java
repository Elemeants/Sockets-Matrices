package sockets.serversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * ServerSocket
 */
public class SocketServer {
  private int Port;
  private ServerSocket serverSocket; 
  private Socket clientSc;
  private PrintWriter  outputStream;
  private BufferedReader inputStrReader;

  public Socket getSocket() {
    return this.clientSc;
  }

  public SocketServer(int port) throws IOException {
    this.Port = port;
  }

  public void Begin() throws UnknownHostException, IOException {
    this.serverSocket = new ServerSocket(this.Port);
    this.clientSc = this.serverSocket.accept();
    this.inputStrReader = new BufferedReader(new InputStreamReader(this.clientSc.getInputStream()));
    this.outputStream = new PrintWriter(this.clientSc.getOutputStream(), true);
  }

  public void End() throws IOException {
    this.inputStrReader.close();
    this.outputStream.close();
    this.clientSc.close();
    this.serverSocket.close();
  }

  public void SendMsg(String str) throws IOException {
    this.outputStream.println(str);
  }
  
  public void SendMsg(Double str) throws IOException {
    this.outputStream.println(str);
  } 

  public void SendMsg(int str) throws IOException {
    this.outputStream.println(str);
  }

  public ArrayList<String> readArrayListUntil(String stopString) throws IOException {
    ArrayList<String> inpuStrings = new ArrayList<>();
    String str;
    while((str = this.readLine()) != null) {
      if (stopString.equals(str)) { break; }
      inpuStrings.add(str);
    }
    return inpuStrings;
  }

  public String readLine() throws IOException {
    String resp = this.inputStrReader.readLine();
    return resp;
  }
}