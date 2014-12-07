/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cplaylistserver;

/**
 *
 * @author Alan Jordan
 */
import java.io.*;

class M3uFileFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".m3u");
    }

    public String getDescription() {
        return ".m3u files";
    }
}