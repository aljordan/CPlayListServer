/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cplaylistserver;

import org.alansaudiotagger.audio.*;
import org.alansaudiotagger.tag.*;
import java.io.File;
import java.util.regex.*;
import javax.sound.sampled.*;
//import java.util.*;
//import java.util.Iterator;

/**
 *
 * @author Alan Jordan
 */
public class TagReader {
    
    private File song;
    private Tag t;
    private String genre;
    private String title;
    private String artist;
    private String albumArtist;
    private String date;
    private String album;
    private String composer;
    private String samplingRate;
    
    public TagReader(File song) {
        this.song = song;
    }

//    public String getSamplingRateFromWav() {
//        String result;
//        try {
//            Wav wav = Wav.parse(song.getAbsolutePath());
//            result = Integer.toString(wav.getSamplesPerSec());
//        }
//        catch(Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//            result = "0";
//        }
//        return result;
//    }

        public String getSamplingRateFromWav() {
        String result;
        try {
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(song);
            result = String.valueOf((int)aff.getFormat().getSampleRate());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            result = "0";
        }
        return result;
    }

    public void processSong() throws Exception {
        //char[] badCharacters = {'\\',':','|'};
        try {
            AudioFile f = AudioFileIO.read(song);
            this.t = f.getTag();
            this.artist = t.getFirstArtist().trim();
            try {
                this.albumArtist = t.getFirst(TagFieldKey.ALBUM_ARTIST).trim();
            }
            catch (Exception e) {
                this.albumArtist = null;
            }
            this.title = t.getFirstTitle().trim();
            this.genre = scrubString(t.getFirstGenre().trim());
            this.date = t.getFirstYear().trim();
            this.album = scrubString(t.getFirstAlbum().trim());
            this.composer = t.getFirst(TagFieldKey.COMPOSER).trim();
            try {
                this.samplingRate = f.getAudioHeader().getSampleRate();
            }
            catch (Exception e) {
                this.samplingRate = null;
                System.out.println(e.getMessage());
            }

//            Iterator<TagField> itf = t.getFields();
//            while (itf.hasNext()) {
//                TagField tf = itf.next();
//                System.out.println(tf.toString());
//            }

//            this.samplingRate = t.get("FIELD_SAMPLERATE").get(0).toString();

            
//            Iterator iterator = t.getFields();
//            while(iterator.hasNext())
//            {
//                System.out.println(iterator.next());
//            }	


        }
        catch (Exception e){
            throw e;
        }
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getDate() {
        return date;
    }

    public String getAlbum() {
        return album;
    }
    
    private String scrubString(String s) {
        String returnString;
        returnString = s.replaceAll("/", "");
        returnString = returnString.replaceAll("|","");
        returnString = returnString.replaceAll(":","");
        returnString = returnString.replaceAll("&","and");
        returnString = returnString.replaceAll("\"","");
        returnString = returnString.replaceAll(Matcher.quoteReplacement("\\"),"");
        return returnString;
    }
    
    public String getComposer() {
        return composer;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    /**
     * @return the samplingRate
     */
    public String getSamplingRate() {
        return samplingRate;
    }

}
