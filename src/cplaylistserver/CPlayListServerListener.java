package cplaylistserver;

import java.net.*;       //For Socket behavior
import java.io.*;        //For Stream behavior
import cplaylistserver.CPlayListEditorNetworkMessage.*;

/**
 *
 * @author Alan jordan
 */
public class CPlayListServerListener implements Runnable {
  private int port = 6123; // default server port
  private ServerSocket ss;          // main connection listening socket
  private boolean running=false;     // used to shut down server when needed
  private CplayListGUI gui;
  private ServerConnectionThread sct;
  private CPlayListOptions options;

  public CPlayListServerListener(CplayListGUI gui, CPlayListOptions options) {
      this.gui = gui;
      this.options = options;
  }

  /*
    run method initializes server listening socket
    and passes connections off to separate threads when they occur
  */
  public void run() {

    try {

      InetAddress inetAddress = InetAddress.getLocalHost();
      String ipAddress = inetAddress.getHostAddress();

      ss=new ServerSocket(port);
//      System.out.println("CplayListEditor Server: listening on port "
//              + ss.getLocalPort() + " on " + ss.getInetAddress());
      gui.setNetworkStatus(CPlayListEditorNetworkMessage.ConnectionStatus.SUCCESS,
                    CPlayListEditorNetworkMessage.NetworkApplicationType.SERVER,
                    "Server running on port " + ss.getLocalPort() + " on " + ipAddress);
      running = true;
    }
    catch(Exception e) {
      System.out.println("Error starting server");
      gui.setNetworkStatus(CPlayListEditorNetworkMessage.ConnectionStatus.FAILURE,
                    CPlayListEditorNetworkMessage.NetworkApplicationType.SERVER,
                    "Error starting server");
    }
    //constantly listen for incoming connections
    do {
      try {
        // when a connection is recieved, start a new ServerConnectionThread
        // and add it to the vector holding the connection threads
        sct = new ServerConnectionThread(ss.accept());
      }
      catch(Exception e) {
        System.out.println("Error accepting connection");
        running=false;
      }
    } while(running);
   System.out.println("Server stopped successfully");
  }

  public void messageReceived(CPlayListEditorNetworkMessage networkMsg) {
    // if disconnected string comes through then kill the thread
    //CPlayListEditorNetworkMessage networkMsg = (CPlayListEditorNetworkMessage)msg;
    System.out.println("Server message received: " + networkMsg.getNetworkMessageType().name());

    // case of disconnect message
    if (networkMsg.isDisconnectNotice() == true) {
      sct.stopRunning();
      return;
    }
    
    switch (networkMsg.getNetworkMessageType()) {
        case PLAYLIST_MESSAGE:
            if (networkMsg.getSamplingRate() != null) {
                gui.setSamplingRate(networkMsg.getSamplingRate());
            }
            gui.playPlaylist(networkMsg.getPlaylist());
            break;
            
        case GET_LIBRARY_MESSAGE:
            networkMsg.setLibraryRoots(options.getLibraryRoots());
            networkMsg.setServerAllowsSamplingRateChanges(options.isEnableCplay()
                    && options.isEnableSamplingRateChanges());
            sct.sendData(networkMsg);
            break;
        
        case NEXT_SONG_MESSAGE:
            gui.controlCplay(CPlayControl.CPlayAction.NEXT_SONG);
            break;
        
        case PREVIOUS_SONG_MESSAGE:
            gui.controlCplay(CPlayControl.CPlayAction.PREVIOUS_SONG);
            break;

        case PAUSE_SONG_MESSAGE:
            gui.controlCplay(CPlayControl.CPlayAction.PAUSE_SONG);
            break;
            
        case FAST_FORWARD_MESSAGE:
            gui.controlCplay(CPlayControl.CPlayAction.FAST_FORWARD);
            break;

        case REWIND_MESSAGE:
            gui.controlCplay(CPlayControl.CPlayAction.REWIND);
            break;

        case PHASE_MESSAGE:
            gui.controlCplay(CPlayControl.CPlayAction.CHANGE_PHASE);
            break;
            
        case VOLUME_UP_MESSAGE:
            gui.controlCplay(CPlayControl.CPlayAction.VOLUME_UP);
            break;
            
        case VOLUME_DOWN_MESSAGE:
            gui.controlCplay(CPlayControl.CPlayAction.VOLUME_DOWN);
            break;
    }   
}


  public void startServer() {
    this.run();
  }

  public void stopServer() {
    if (running == true) {
      running = false;
      try {
        ss.close();
      }
      catch (IOException e) {
        System.out.println("Error closing server socket");
      }
      finally {
        ss = null;
      }
      gui.setNetworkStatus(CPlayListEditorNetworkMessage.ConnectionStatus.FAILURE,
                    CPlayListEditorNetworkMessage.NetworkApplicationType.SERVER,
                    "Server Stopped");
    }
  }


  /* Private inner class to encapsulate an individual Client Connection*/
  class ServerConnectionThread extends Thread {
    private Socket socket;            // socket for the client connection
    private ObjectInputStream in;
    private ObjectOutputStream out;     // for outgoing messages
    private boolean running=true;     // used to shutdown the client thread

    public ServerConnectionThread(Socket socket) {
      try {
        this.socket=socket;
      }
      catch(Exception e) {
        e.printStackTrace();
      }

      // set up input and output streams and start
      // running the client connection thread
      try {
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
      }
      catch(Exception e) {
        e.printStackTrace();
      }
      this.start();
    }

    @Override public void run() {
      // Listen for messages that come in from the
      // client via the GUIChatServer object
      while(running) {
        try {
            CPlayListEditorNetworkMessage doSomething = (CPlayListEditorNetworkMessage)in.readObject();
            messageReceived(doSomething);
        }
        catch(Exception e)
        {
          e.printStackTrace();
          running=false;
        }
      }
      // if running is false then shut down io streams and the socket
      try {
          out.close();
          in.close();
      } catch (IOException e) {
      }
      finally {
        out=null;
        in=null;
        socket=null;
      }
    }

    // send data to individual client connection
    public void sendData(CPlayListEditorNetworkMessage msg) {
      try {
        out.writeObject(msg);
        out.flush();
      } catch(Exception e) {
        e.printStackTrace();
      }
    }

    //used to shut down the thread
    public void stopRunning() {
      running=false;
    }
  }  // end of ServerConnectionThread inner class


} // end of outer class
