package cplaylistserver;

import java.io.*;
import java.util.Vector;

/**
 *
 * @author Alan Jordan
 */

public class CPlayListEditorNetworkMessage implements Serializable {
  private boolean bConnectNotice; //true if requesting to connect to the server
  private boolean bDisconnectNotice; // true if requesting to disconnect
  private NetworkMessageType messageType;
  //private MusicLibrary library;
  private Vector<LibraryRoot> libraryRoots;
  private Playlist playlist;
  private boolean serverAllowsSamplingRateChanges;
  private boolean clientAllowsSamplingRateChanges;
  private CPlayIniIO.SAMPLING_RATE samplingRate;

  public CPlayListEditorNetworkMessage() {
    bConnectNotice = false;
    bDisconnectNotice = false;
  }

//  public MusicLibrary getMusicLibrary() {
//      return library;
//  }
//
//  public void setMusicLibrary(MusicLibrary library) {
//      this.library = library;
//  }

    public Vector<LibraryRoot> getLibraryRoots() {
        return libraryRoots;
    }

    public void setLibraryRoots(Vector<LibraryRoot> libraryRoots) {
        this.libraryRoots = libraryRoots;
    }   
    
    /**
     * @return the playlist
     */
    public Playlist getPlaylist() {
        return playlist;
    }

    /**
     * @param playlist the playlist to set
     */
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    /**
     * @return the allowSamplingRateChanges
     */
    public boolean isServerAllowSamplingRateChanges() {
        return serverAllowsSamplingRateChanges;
    }

    /**
     * @param allowSamplingRateChanges the allowSamplingRateChanges to set
     */
    public void setServerAllowsSamplingRateChanges(boolean allowSamplingRateChanges) {
        this.serverAllowsSamplingRateChanges = allowSamplingRateChanges;
    }

    /**
     * @return the clientAllowsSamplingRateChanges
     */
    public boolean isClientAllowsSamplingRateChanges() {
        return clientAllowsSamplingRateChanges;
    }

    /**
     * @param clientAllowsSamplingRateChanges the clientAllowsSamplingRateChanges to set
     */
    public void setClientAllowsSamplingRateChanges(boolean clientAllowsSamplingRateChanges) {
        this.clientAllowsSamplingRateChanges = clientAllowsSamplingRateChanges;
    }

    public CPlayIniIO.SAMPLING_RATE getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(CPlayIniIO.SAMPLING_RATE samplingRate) {
        this.samplingRate = samplingRate;
    }

  public enum NetworkMessageType {
      CONNECT_MESSAGE, DISCONNECT_MESSAGE, GET_LIBRARY_MESSAGE, 
      PLAYLIST_MESSAGE, NEXT_SONG_MESSAGE, PREVIOUS_SONG_MESSAGE,
      PAUSE_SONG_MESSAGE, FAST_FORWARD_MESSAGE, REWIND_MESSAGE, 
      PHASE_MESSAGE, VOLUME_UP_MESSAGE, VOLUME_DOWN_MESSAGE
  }

  public enum ConnectionStatus {
      SUCCESS, FAILURE
  }

  public enum NetworkApplicationType {
      SERVER, CLIENT
  }

  public void setNetworkMessageType(NetworkMessageType messageType) {
      this.messageType = messageType;
  }

  public NetworkMessageType getNetworkMessageType() {
      return messageType;
  }

  public void setConnectNotice(boolean bConnect) {
    bConnectNotice = bConnect;
  }

  public void setDisconnectNotice(boolean bDisconnect) {
    bDisconnectNotice = bDisconnect;
  }

  public boolean isConnectNotice() {
    return bConnectNotice;
  }

  public boolean isDisconnectNotice() {
    return bDisconnectNotice;
  }

}
