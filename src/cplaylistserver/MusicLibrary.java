package cplaylistserver;

/**
 *
 * @author Alan Jordan
 */

import java.util.*;
import java.io.*;

public class MusicLibrary implements java.io.Serializable {

    private Vector<Album> library;
    private Vector<String> genres;
    private String allGenres = " All";

    public MusicLibrary() {
        library = new Vector<Album>();
        genres = new Vector<String>(1,1);
        genres.add(allGenres);
    }

    private void addGenre(String genre) {
        boolean found = false;
        for (String g : getGenres()) {
            if (g.equalsIgnoreCase(genre)) {
                found = true;
                break;
            }
        }

        if (found == false) {
            genres.add(genre);
            Collections.sort(genres);
        }
    }

    /**
     * @return the genres
     */
    public Vector<String> getGenres() {
        return genres;
    }

    // Check to see if this is being used - if not, delete
    public enum LibrarySort {
        BY_ARTIST, BY_TITLE
    }

    public void changeTrackDisplay(CPlayListOptions options) {
        for (int counter = 0; counter < library.size(); counter++ ) {
            Album a = library.elementAt(counter);
            Track[] tracks = a.getTracks();
            for (int trackCounter = 0; trackCounter < tracks.length; trackCounter++ ) {
                Track t = tracks[trackCounter];
                String display = t.getTrackTitle();
                if (options.isArtistInTrackTitle()) {
                    if (t.getTrackPerformer() != null) {
                        display = t.getTrackPerformer() + " - " + display;
                    }
                }
               if (options.isShowSamplingRate()) {
                    if (t.getSamplingRate() != null) {
                        // convert sampling rate to a shorter format
                        int rate = Integer.parseInt(t.getSamplingRate());
                        float r = (float)rate / 1000;
                        display = r + "k " + display;
                    }
                }
                if (options.isShowFileType()) {
                    if (t.getFileType() != null) {
                        display = t.getFileType() + " " + display;
                    }
                }
                t.setDisplayName(display);
            }
        }
    }

//    public enum LibraryTrackDisplay {
//        SHOW_TITLE_ONLY, SHOW_ARTIST_AND_TITLE, SHOW_FILETYPE_AND_TITLE,
//        SHOW_FILETYPE_AND_ARTIST_AND_TITLE
//    }

    public void addAlbum(Album cd) {
        library.add(cd);
        if (cd.getGenre() != null && !cd.getGenre().isEmpty()) {
            addGenre(cd.getGenre());
        }
    }


    // return all albums in the library sorted accordingly
    public Vector<Album> getAllAlbums(LibrarySort sortType) {
        library.trimToSize();
        if (sortType == LibrarySort.BY_ARTIST) {
            Collections.sort(library);
        } else {
            Collections.sort(library,new LibrarySortByTitle());
        }
        return library;
    }

    public Vector<Album> getAlbumsByGenre(String genre) {
        library.trimToSize();

        Vector<Album> temp = new Vector<Album>();

        if (genre.equalsIgnoreCase(allGenres)) {
            temp.addAll(library);
        } else {
            Iterator<Album> i = library.iterator();
            while (i.hasNext()) {
                Album a = i.next();
                if (a.getGenre() != null && !a.getGenre().isEmpty()) {
                    if (a.getGenre().equalsIgnoreCase(genre)) {
                        temp.add(a);
                    }
                }
            }
        }

        temp.trimToSize();
        Collections.sort(temp);
        return temp;
    }

    // return all albums whose artist starts with the artist argument
    public Vector<Album> getAlbumsByArtistString(String artist) {
        library.trimToSize();

        Vector<Album> temp = new Vector<Album>();

        Iterator<Album> i = library.iterator();
        while (i.hasNext()) {
            Album a = i.next();
            if (a.getArtist().toLowerCase().startsWith(artist.toLowerCase())) {
                temp.add(a);
            }
        }

        temp.trimToSize();
        Collections.sort(temp);
        return temp;
    }

    // return all albums whose album title starts with the title argument
    public Vector<Album> getAlbumsByAlbumTitle(String title) {
        library.trimToSize();

        Vector<Album> temp = new Vector<Album>();

        Iterator<Album> i = library.iterator();
        while (i.hasNext()) {
            Album a = i.next();
            if (a.getAlbumTitle().toLowerCase().startsWith(title.toLowerCase())) {
                temp.add(a);
            }
        }

        temp.trimToSize();
        Collections.sort(temp, new LibrarySortByTitle());
        return temp;
    }

    public void saveLibrary() {
        try {
            FileOutputStream f_out = new FileOutputStream("cPlaylistEditor.lib");
            ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
            obj_out.writeObject(this);
        }
        catch (FileNotFoundException fe) {
            System.out.println(fe.getMessage());
            System.out.println(fe.getStackTrace());
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            System.out.println(ioe.getStackTrace());
        }
    }

}
