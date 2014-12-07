/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cplaylistserver;

/**
 *
 * @author Alan Jordan
 */
public class LibraryRoot implements java.io.Serializable {
    private String libraryRootPath;
    private String libraryName;
    private MusicLibrary library;

    public LibraryRoot(String libraryRootPath, String libraryName) {
        this.libraryRootPath = libraryRootPath;
        this.libraryName = libraryName;
    }

    public String getLibraryRootPath() {
        return libraryRootPath;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public MusicLibrary getLibrary() {
        return library;
    }

    public void setLibrary(MusicLibrary library) {
        this.library = library;
    }

    @Override
    public String toString() {
        return libraryName;
    }
}
