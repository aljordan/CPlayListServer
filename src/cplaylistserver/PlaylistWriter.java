package cplaylistserver;

/**
 *
 * @author Alan Jordan
 */

import java.io.*;

public class PlaylistWriter {
    private Playlist cd;
    private Track[] tracks;
    private FileWriter outFile;
    private CPlayListOptions options;

    public PlaylistWriter(Playlist somethingGood, CPlayListOptions options) {
        cd = somethingGood;
        this.options = options;

        tracks = cd.getTracks();
    }

    public void writeCue() {
        try {
            outFile = new FileWriter(cd.getDirectoryOnDisc().getAbsolutePath());
            if (options.getLibraryRootPath().equalsIgnoreCase(options.getPlaylistFolder().getPath())) {
                process(false);
            }
            else {
                process(true);
            }
            outFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    public void writeM3u() {
        try {
            outFile = new FileWriter(cd.getDirectoryOnDisc().getAbsolutePath());
            if (options.getLibraryRootPath().equalsIgnoreCase(options.getPlaylistFolder().getPath())) {
                processM3u(false);
            }
            else {
                processM3u(true);
            }
            outFile.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    private void process(boolean writePath) {
        try {
            PrintWriter out = new PrintWriter(outFile);

            out.println("REM GENRE Playlist");

            if (cd.getDate() != null) {
                out.println("REM DATE " + cd.getDate());
            }

            out.println("PERFORMER \"" + cd.getArtist() + "\"");
            out.println("TITLE \"" + cd.getAlbumTitle() + "\"");
            for (int counter = 1; counter <= tracks.length; counter++) {
                if (writePath == true  && tracks[counter - 1].getFilePath() != null) {
                    out.println("FILE \"" + tracks[counter - 1].getFilePath() + "\" WAVE");
                } else {
                    out.println("FILE \"" + tracks[counter - 1].getFileName() + "\" WAVE");
                }

                if (counter < 10) {
                    out.println("  TRACK 0" + counter + " AUDIO");
                } else {
                    out.println("  TRACK " + counter + " AUDIO");
                }

                if (options.isArtistInTrackTitle() && tracks[counter - 1].getTrackPerformer() != null) {
                    out.println("    TITLE \"" + tracks[counter - 1].getTrackPerformer() + " - " + tracks[counter - 1].getTrackTitle() + "\"");
                }
                else {
                    out.println("    TITLE \"" + tracks[counter - 1].getTrackTitle() + "\"");
                }

                if (tracks[counter - 1].getTrackPerformer() != null) {
                    out.println("    PERFORMER \"" + tracks[counter - 1].getTrackPerformer() + "\"");
                } else {
                    out.println("    PERFORMER \"" + cd.getArtist() + "\"");
                }
                out.println("    INDEX 01 00:00:00");
                out.println("    REM \"" + tracks[counter - 1].getSamplingRate() + "\"");
            }
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

        private void processM3u(boolean writePath) {
        try {
            PrintWriter out = new PrintWriter(outFile);

            out.println("#CURTRACK 0");
            out.println("#EXTM3U");

            for (int counter = 1; counter <= tracks.length; counter++) {
                if (options.isArtistInTrackTitle() && tracks[counter - 1].getTrackPerformer() != null) {
                    out.println("#EXTINF:-1," + tracks[counter - 1].getTrackPerformer() + " - " + tracks[counter - 1].getTrackTitle());
                }
                else {
                    out.println("#EXTINF:-1," + tracks[counter - 1].getTrackTitle());
                }

                if (writePath == true && tracks[counter - 1].getFilePath() != null) {
                    out.println(tracks[counter - 1].getFilePath());
                } else {
                    out.println(tracks[counter - 1].getFileName());
                }
            }
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }
}

// Write M3U files as follows:
//#CURTRACK 0
//#EXTM3U
//#EXTINF:-1,How Am I Different
//G:\MusicLibrary\Mann, Aimee\Bachelor No. 2\01 - How Am I Different.flac
//#EXTINF:-1,Nothing Is Good Enough
//G:\MusicLibrary\Mann, Aimee\Bachelor No. 2\02 - Nothing Is Good Enough.flac
//#EXTINF:-1,Red Vines
//G:\MusicLibrary\Mann, Aimee\Bachelor No. 2\03 - Red Vines.flac
//#EXTINF:-1,The Fall Of The World's Own Optimist
//G:\MusicLibrary\Mann, Aimee\Bachelor No. 2\04 - The Fall Of The World's Own Optimist.flac
//#EXTINF:-1,Satellite
//G:\MusicLibrary\Mann, Aimee\Bachelor No. 2\05 - Satellite.flac

// Write cue files as follows:
//PERFORMER "Tracy Chapman"
//TITLE "Tracy Chapman"
//FILE "C:\Music\01 Track01.wav" WAVE
//  TRACK 01 AUDIO
//    TITLE "Track01"
//    PERFORMER "Tracy Chapman"
//    INDEX 01 00:00:00
//FILE "Pink Floyd - The Wall 1.flac" WAVE
//  TRACK 02 AUDIO
//    TITLE "Track02"
//    INDEX 01 00:00:00
//FILE "Track03.wav" WAVE
//  TRACK 03 AUDIO
//    TITLE "Track03"
//    INDEX 01 00:00:00
