package cplaylistserver;

import java.io.*;

/**
 *
 * @author Alan Jordan
 */
public class LibraryProcessor extends Thread {

    private File[] files;
    private MusicLibrary library;
    private CplayListGUI parentWindow;
    private CPlayListOptions options;

    public LibraryProcessor(CPlayListOptions options, CplayListGUI parentWindow) {
        this.options = options;
        this.parentWindow = parentWindow;
        library = new MusicLibrary();
    }

    @Override
    public void run() {
        FilenameFilter filter = new FlacFilter();
        boolean atBeginningOfLibrary = true;
        boolean okToUseTags;
        TagReader tr;
        String path = "";
        Album rippedDisc = new Album();

        parentWindow.setStatus("Recursing directories to find music files");
        try { sleep(1);} catch (InterruptedException ie) {}
        
        this.files = RecurseDirectory.listFilesAsArray(options.getLibraryRoot(), filter ,true);
        for (File file : files) {
            if (options.isUseEmbeddedTags()  && ((file.getName().toLowerCase()).endsWith(".flac")
                    || (file.getName().toLowerCase()).endsWith(".mp3")))
                okToUseTags = true;
            else
                okToUseTags = false;
            
            File parentDirectory = file.getParentFile();
            
            String[] splitPath = (file.toString()).split("\\\\");
            if (parentDirectory.getPath().equals(path) == false) {
                //new album
                path = parentDirectory.getPath();
                parentWindow.setStatus("Processing " + path);
                try { sleep(1);} catch (InterruptedException ie) {}
                
                // add current Album first
                if (atBeginningOfLibrary == false) {
                    library.addAlbum(rippedDisc);
                }
                atBeginningOfLibrary = false;

                rippedDisc = new Album();


                // Get General Artist - Album information
                if (okToUseTags) {
                    try {
                        tr = new TagReader(file);
                        tr.processSong();
                        rippedDisc.setAlbumTitle(tr.getAlbum());
                        rippedDisc.setDate(tr.getDate());
                        
                        if (tr.getAlbumArtist() != null && !tr.getAlbumArtist().isEmpty())
                            rippedDisc.setArtist(tr.getAlbumArtist());
                        else
                            rippedDisc.setArtist(tr.getArtist());
                        
                        rippedDisc.setGenre(tr.getGenre());
                    }
                    catch (Exception e) {
                        rippedDisc.setAlbumTitle(splitPath[splitPath.length -2]);
                        rippedDisc.setArtist(splitPath[splitPath.length -3]);
                        parentWindow.setStatus(e.getMessage());
                        System.out.println(e.getStackTrace());
                    }
                    
                }
                else {
                    if (options.isParseTagsFromSingleFolder()) {
                        try {
                            rippedDisc.setAlbumTitle(getAlbumNameFromSingleFolder(splitPath[splitPath.length -2]));
                        } catch (Exception e) {
                            System.out.println("Can't parse album name from single folder name.");
                            System.out.println("You will need to manually edit " + splitPath[splitPath.length -2]);
                            System.out.println(e.getMessage());
                            System.out.println(e.getStackTrace());
                            rippedDisc.setAlbumTitle(splitPath[splitPath.length -2]);
                        }
                        try {
                            rippedDisc.setArtist(getArtistNameFromSingleFolder(splitPath[splitPath.length -2]));
                        } catch (Exception e) {
                            System.out.println("Can't parse artist from single folder name.");
                            System.out.println("You will need to manually edit " + splitPath[splitPath.length -2]);
                            System.out.println(e.getMessage());
                            System.out.println(e.getStackTrace());
                            rippedDisc.setArtist(splitPath[splitPath.length -2]);
                        }
                    }
                    else {
                        rippedDisc.setAlbumTitle(splitPath[splitPath.length -2]);
                        rippedDisc.setArtist(splitPath[splitPath.length -3]);
                    }
                }

                rippedDisc.setDirectoryOnDisc(parentDirectory);
            } //end new album block

            // song logic
            if (okToUseTags) {
                try {
                    tr = new TagReader(file);
                    tr.processSong();
                    if ((tr.getArtist() != null && !tr.getArtist().isEmpty())) {
                        Track t = new Track(splitPath[splitPath.length -1], tr.getTitle(), tr.getArtist());
                        t.setFilePath(file.getPath());
                        t.setFileType(this.getExtensionFromTrack(t.getFilePath()));
                        t.setSamplingRate(tr.getSamplingRate());
                        rippedDisc.addTrack(t);
                    }
                    else {
                        Track t = new Track(splitPath[splitPath.length -1], tr.getTitle());
                        t.setFilePath(file.getPath());
                        t.setFileType(this.getExtensionFromTrack(t.getFilePath()));
                        t.setDisplayName(tr.getSamplingRate());
                        rippedDisc.addTrack(t);
                    }
                }
                catch (Exception e) {
                    Track t = new Track(splitPath[splitPath.length -1], splitPath[splitPath.length -1]);
                    t.setFilePath(file.getPath());
                    t.setFileType(this.getExtensionFromTrack(t.getFilePath()));
                    rippedDisc.addTrack(t);
                    parentWindow.setStatus(e.getMessage());
                    System.out.println(e.getStackTrace());
                }
            } 
            else { // not okay to use tags
                String trackTitle;
                if (options.isParseTitle()) {
                    trackTitle = parseTrackTitle(splitPath[splitPath.length -1]);
                }
                else {
                    trackTitle = splitPath[splitPath.length -1];
                }
                Track t = new Track(splitPath[splitPath.length -1], trackTitle);
                t.setFilePath(file.getPath());
                t.setFileType(this.getExtensionFromTrack(t.getFilePath()));
                // get sampling rate if wav or flac file
                tr = new TagReader(file);
                if (t.getFileType().equals("wav")) {
                    t.setSamplingRate(tr.getSamplingRateFromWav());
                }
                else if (t.getFileType().equals("flac")){
                    try {
                        tr.processSong();
                        t.setSamplingRate(tr.getSamplingRate());
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        t.setSamplingRate("0");
                    }
                }
                rippedDisc.addTrack(t);
            }
            okToUseTags = false;
        }
        // add last album
        library.addAlbum(rippedDisc);

        library.changeTrackDisplay(options);
        
        //set main GUI library
        parentWindow.setLibrary(library);
        parentWindow.setStatus("Finished reading music library");
        options.getLibraryRoots().elementAt(options.getSelectedLibrary()).setLibrary(library);
        parentWindow.setLibraryAvailable();
    }

    private String getExtensionFromTrack(String trackTitle) {
        String result;

        try {
            //strip extension from track title
            int period = trackTitle.lastIndexOf(".");
            result = trackTitle.substring(period + 1).toLowerCase();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            result = null;
        }
        return result.toLowerCase();
    }


    private String parseTrackTitle(String trackTitle) {
        String result;

        try {
            //strip extension from track title
            int period = trackTitle.lastIndexOf(".");
            result = trackTitle.substring(0, period);

            while (isFirstCharStripable(result))
            {
                result = result.substring(1);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            result = trackTitle;
        }

        return result;
    }

    private boolean isFirstCharStripable(String str) {
        if (Character.isDigit(str.charAt(0)))
            return true;

        if (str.startsWith(" "))
            return true;

        if (str.startsWith("-"))
            return true;

        if (str.startsWith("_"))
            return true;

        if (str.startsWith("."))
            return true;

        return false;
    }

    private String getAlbumNameFromSingleFolder(String folderName) {
        String[] splitFolder = folderName.split(options.getParseSingleFolderSeperator());
        return splitFolder[1];
    }

    private String getArtistNameFromSingleFolder(String folderName) {
        String[] splitFolder = folderName.split(options.getParseSingleFolderSeperator());
        return splitFolder[0];
    }
    
}
