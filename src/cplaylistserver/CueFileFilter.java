package cplaylistserver;

/**
 *
 * @author Alan Jordan
 */
import java.io.*;

class CueFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".cue");
        //return f.getName().toLowerCase().endsWith(".cue");
    }

    public String getDescription() {
        return ".cue files";
    }
}