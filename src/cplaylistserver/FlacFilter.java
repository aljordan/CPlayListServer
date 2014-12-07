package cplaylistserver;

import java.io.FilenameFilter;
import java.io.File;

public class FlacFilter implements FilenameFilter {
    public boolean accept(File dir, String name) {
        return ((name.toLowerCase()).endsWith(".flac") 
                || (name.toLowerCase()).endsWith(".wav") 
                || (name.toLowerCase()).endsWith(".mp3") 
                || (name.toLowerCase()).endsWith(".ape"));
    }

}
