package cplaylistserver;

import java.util.*;
/**
 *
 * @author Alan Jordan
 */
public class LibrarySortByTitle implements Comparator<Album> {

    public int compare(Album a1, Album a2) {
        return a1.getAlbumTitle().compareTo(a2.getAlbumTitle());
    }

}
