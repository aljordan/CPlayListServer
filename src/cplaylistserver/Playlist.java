package cplaylistserver;

import java.util.Vector;
import java.io.File;
import cplaylistserver.CPlayIniIO.*;

/**
 *
 * @author Alan Jordan
 */
public class Playlist implements Comparable<Album>, java.io.Serializable {
    private String genre;
    private String artist;
    private String albumTitle;
    private String date;
    private Vector<Track> tracks;
    private File directoryOnDisc;
    private int highestSamplingRateInTracks;

    public Playlist() {
        tracks = new Vector<Track>();
        highestSamplingRateInTracks = 0;
    }

    private void searchAndSetHighestSampleRate() {
        this.resetHighestSamplingRate();
        for (Track song : tracks) {
            if (song.getSamplingRate() != null &&
                    !song.getSamplingRate().isEmpty() &&
                    Integer.parseInt(song.getSamplingRate()) > highestSamplingRateInTracks) {
                highestSamplingRateInTracks = Integer.parseInt(song.getSamplingRate());
            }
        }
    }

    public void deleteTrack(int index) {
        tracks.removeElementAt(index);
        tracks.trimToSize();
        searchAndSetHighestSampleRate();
    }

    public void addTrack(Track song) {
        if (song.getSamplingRate() != null) {
            if (Integer.parseInt(song.getSamplingRate()) > highestSamplingRateInTracks) {
                highestSamplingRateInTracks = Integer.parseInt(song.getSamplingRate());
            }
        }
        tracks.add(song);
    }

    public Track getTrack(int index) {
        return tracks.get(index);
    }

    public void setTrack(Track track, int index) {
        tracks.setElementAt(track, index);
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

    /**
     * @return the highestSamplingRateInTracks
     */
    public int getHighestSamplingRateInTracks() {
        return highestSamplingRateInTracks;
    }

    public void resetHighestSamplingRate() {
        this.highestSamplingRateInTracks = 0;
    }

}
