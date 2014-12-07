package cplaylistserver;

/**
 *
 * @author Alan Jordan
 */

import java.io.*;
import java.util.*;

public class PlaylistProcessor {

    static Playlist openPlaylist(File playlistFile) {
        Playlist result = new Playlist();
        result.setArtist("Various Artists");
        Scanner scanner;
        String line;
        String path;
        String title;
        String performer;
        String rate;
        Track track;

        try {
            scanner = new Scanner(playlistFile);

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.startsWith("FILE ")) {
                    path = getQuotedAreaFromString(line);
                    line = scanner.nextLine(); // ignore this line
                    line = scanner.nextLine(); // this should be song title
                    title = getQuotedAreaFromString(line);
                    line = scanner.nextLine(); // this should be performer
                    performer = getQuotedAreaFromString(line);
                    line = scanner.nextLine(); // ignore this line
                    line = scanner.nextLine(); // this should be sampling rate
                    rate = getQuotedAreaFromString(line);
                    track = new Track(path,title,performer);
                    track.setSamplingRate(rate);
                    result.addTrack(track);
                }
            }

            scanner.close();
        }
        catch (FileNotFoundException fnf) {
            System.out.println("Input file not found");
        }

        return result;
    }


        static Playlist openM3uPlaylist(File playlistFile) {
        Playlist result = new Playlist();
        result.setArtist("Various Artists");
        Scanner scanner;
        String line;
        String path;
        String title;
        String performer;
        try {
            scanner = new Scanner(playlistFile);

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.startsWith("#EXTINF:")) {
                    title = getTitleFromM3uLine(line);
                    path = scanner.nextLine();
                    result.addTrack(new Track(path,title,null));
                }
            }

            scanner.close();
        }
        catch (FileNotFoundException fnf) {
            System.out.println("Input file not found");
        }

        return result;
    }

    static String getTitleFromM3uLine(String title) {
        String result;
        int startingChar = title.indexOf(",");
        result = title.substring(startingChar + 1);
        return result;
    }

    static String getQuotedAreaFromString(String fileLine) {
        String filePath;
        int startingChar = fileLine.indexOf("\"");
        int endingChar = fileLine.lastIndexOf("\"");
        if (startingChar > -1 && endingChar > -1) {
            filePath = fileLine.substring(startingChar + 1, endingChar);
        } else {
            filePath = null;
        }

        return filePath;
    }

}
