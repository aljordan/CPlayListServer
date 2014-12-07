package cplaylistserver;

import java.util.Vector;
import java.io.File;

public class Album implements Comparable<Album>, java.io.Serializable  {
    private String genre;
    private String artist;
    private String albumTitle;
    private String date;
    private Vector<Track> tracks;
    private File directoryOnDisc;
    
    public Album() {
        tracks = new Vector<Track>();
    }
    
    public void deleteTrack(int index) {
        tracks.removeElementAt(index);
        tracks.trimToSize();
    }
    
    public void addTrack(Track song) {
        tracks.add(song);
    }
    
    public Track getTrack(int index) {
        return tracks.get(index);
    }
    
    public Track[] getTracks() {
        Track[] returnValue = new Track[tracks.size()];
        for (int counter = 0; counter < returnValue.length; counter++) {
            returnValue[counter] = (Track)tracks.get(counter);
        }
        return returnValue;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public File getDirectoryOnDisc() {
        return directoryOnDisc;
    }

    public void setDirectoryOnDisc(File directoryOnDisc) {
        this.directoryOnDisc = directoryOnDisc;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int compareTo(Album o) {
        Album other = (Album)o;
        int result = this.artist.compareTo(other.getArtist());
        if (result == 0) {
            result = this.albumTitle.compareTo(other.getAlbumTitle());
        }

        return result;
    }
    
    @Override
    public String toString() {
        return artist + ": " + albumTitle;
    }

}
