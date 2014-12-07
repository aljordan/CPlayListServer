package cplaylistserver;

/**
 *
 * @author Alan Jordan
 */
import cplaylistserver.CPlayIniIO.*;
import java.util.Vector;
import java.io.*;

public class CPlayListOptions implements java.io.Serializable {
    private String playlistFolderPath;
    private File playlistFolder;
    private File libraryRoot; //Root folder of music library
    private String libraryRootPath; //String representation of above var because can't always serialize File object
    private Vector<LibraryRoot> libraryRoots;
    private boolean parseTagsFromSingleFolder; //Parse artist and album from single parent folder instead of two parent folders.
    private String parseSingleFolderSeperator; //String to use when parsing tags from single parent folder
    private boolean useEmbeddedTags; //Read embedded tags from FLAC and MP3 files
    private boolean artistInTrackTitle; //Various artist - performer - title overrides
    private boolean parseTitle; //Remove track number and file extension from track names   
    private String cplayExecutablePath;
    private String cplayIniPath;
    private boolean enableCplay;
    private boolean exitAppWhenRunningCPlay;
    private String foobarExecutablePath;
    private boolean enableFoobar;
    private int selectedLibrary;
    private boolean showSamplingRate;
    private boolean showFileType;
    private String serverIpAddress;
    private boolean runAsServer;
    private boolean enableSamplingRateChanges;
    private CPlayIniIO.SAMPLING_RATE samplingRate;
    private boolean enableDynamicUpsampling;
    private CPlayIniIO.SAMPLING_RATE maximumSamplingRate;
    private CPlayIniIO.DYNAMIC_UPSAMPLING_RULE upsamplingRule;
    private PLAYLIST_FORMAT playlistType;


    public CPlayListOptions () {
        initOptions();
    }

    public PLAYLIST_FORMAT getPlaylistType() {
        return playlistType;
    }

    public void setPlaylistType(PLAYLIST_FORMAT playlistType) {
        this.playlistType = playlistType;
    }

    /**
     * @return the exitAppWhenRunningCPlay
     */
    public boolean isExitAppWhenRunningCPlay() {
        return exitAppWhenRunningCPlay;
    }

    /**
     * @param exitAppWhenRunningCPlay the exitAppWhenRunningCPlay to set
     */
    public void setExitAppWhenRunningCPlay(boolean exitAppWhenRunningCPlay) {
        this.exitAppWhenRunningCPlay = exitAppWhenRunningCPlay;
    }

    public enum PLAYLIST_FORMAT {
        CUE_SHEET, M3U_EXTENDED
    }

    public void initOptions() {
        try {
            FileInputStream f_in = new FileInputStream("cPlaylistOptions.data");
            ObjectInputStream obj_in = new ObjectInputStream (f_in);
            Object obj = obj_in.readObject();

            if (obj instanceof CPlayListOptions) {
                CPlayListOptions tempOptions = (CPlayListOptions)obj;
                this.parseTagsFromSingleFolder = tempOptions.isParseTagsFromSingleFolder();
                this.parseSingleFolderSeperator = tempOptions.getParseSingleFolderSeperator();
                this.useEmbeddedTags = tempOptions.isUseEmbeddedTags();
                this.artistInTrackTitle = tempOptions.isArtistInTrackTitle();
                this.parseTitle = tempOptions.isParseTitle();
                this.enableCplay = tempOptions.isEnableCplay();
                this.exitAppWhenRunningCPlay = tempOptions.isExitAppWhenRunningCPlay();
                this.cplayExecutablePath = tempOptions.getCplayExecutablePath();
                this.setCplayIniPath((String) tempOptions.getCplayIniPath());
                this.enableFoobar = tempOptions.isEnableFoobar();
                this.foobarExecutablePath = tempOptions.getFoobarExecutablePath();
                this.selectedLibrary = tempOptions.getSelectedLibrary();
                this.showFileType = tempOptions.isShowFileType();
                this.showSamplingRate = tempOptions.isShowSamplingRate();
                this.runAsServer = tempOptions.isRunAsServer();
                this.enableSamplingRateChanges = tempOptions.isEnableSamplingRateChanges();
                this.samplingRate = tempOptions.getSamplingRate();
                this.enableDynamicUpsampling = tempOptions.isEnableDynamicUpsampling();
                this.maximumSamplingRate = tempOptions.getMaximumSamplingRate();
                this.upsamplingRule = tempOptions.getUpsamplingRule();
                this.playlistType = tempOptions.getPlaylistType();

                if (tempOptions.getPlaylistFolderPath() != null) {
                    this.playlistFolder = new File(tempOptions.getPlaylistFolderPath());
                    this.playlistFolderPath = playlistFolder.getPath();
                }
                else {
                    this.playlistFolder = null;
                    this.playlistFolderPath = null;
                }
                
                if (tempOptions.getLibraryRootPath() != null) {
                    this.libraryRoot = new File(tempOptions.getLibraryRootPath()); 
                    this.libraryRootPath = libraryRoot.getPath();
                }
                else {
                    this.libraryRoot = null;
                    this.libraryRootPath = null;
                }

                if (tempOptions.getLibraryRoots() != null) {
                    this.setLibraryRoots(tempOptions.getLibraryRoots());
                }
                else {
                    this.setLibraryRoots(new Vector<LibraryRoot>());
                }

                this.serverIpAddress = tempOptions.getServerIpAddress();

            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            playlistFolder = null;
            playlistFolderPath = null;
            libraryRoot = null;
            libraryRootPath = null;
            useEmbeddedTags = false;
            parseTagsFromSingleFolder = false;
            artistInTrackTitle = false;
            parseTitle = false;
            cplayExecutablePath = null;
            cplayIniPath = null;
            enableCplay = false;
            exitAppWhenRunningCPlay = false;
            foobarExecutablePath = null;
            enableFoobar = false;
            setLibraryRoots(new Vector<LibraryRoot>());
            enableSamplingRateChanges = false;
            samplingRate = null;
            enableDynamicUpsampling = false;
            maximumSamplingRate = null;
            upsamplingRule = null;
            playlistType = null;
        }
        
    }

    
    public void saveOptions() {
        //set objects in a manner that will be serializable.  File object at root of drive
        // can't be serialized and unserialized.

        // rely on string instead of File object
        try {
            playlistFolderPath = playlistFolder.getPath();
            playlistFolder = null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            playlistFolder = null;
            playlistFolderPath = null;
        }

        try {
            libraryRootPath = libraryRoot.getPath();
            libraryRoot = null;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
            libraryRootPath = null;
            libraryRoot = null;
        }
        
        try {
            FileOutputStream f_out = new FileOutputStream("cPlaylistOptions.data");
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
    
    /**
     * @return the playlistDirectoryFolderPath
     */
    public String getPlaylistFolderPath() {
        return playlistFolderPath;
    }

    /**
     * @param playlistDirectoryFolderPath the playlistDirectoryFolderPath to set
     */
    public void setPlaylistFolderPath(String playlistDirectoryFolderPath) {
        this.playlistFolderPath = playlistDirectoryFolderPath;
    }

    /**
     * @return the playlistFolder
     */
    public File getPlaylistFolder() {
        return playlistFolder;
    }

    /**
     * @param playlistFolder the playlistFolder to set
     */
    public void setPlaylistFolder(File playlistFolder) {
        this.playlistFolder = playlistFolder;
    }

    /**
     * @return the libraryRoot
     */
    public File getLibraryRoot() {
        return libraryRoot;
    }

    public Vector<LibraryRoot> getLibraryRoots() {
        return libraryRoots;
    }

    /**
     * @param libraryRoot the libraryRoot to set
     */
    public void setLibraryRoot(File libraryRoot) {
        this.libraryRoot = libraryRoot;
        this.libraryRootPath = libraryRoot.getPath();
    }

        /**
     * @param libraryRoot the libraryRoot to set
     */
    public void addLibraryRoot(LibraryRoot libraryRoot) {
        this.libraryRoots.add(libraryRoot);
    }

    public void deleteLibraryRoot(int index) {
        libraryRoots.removeElementAt(index);
        libraryRoots.trimToSize();
    }

    public String getLibraryRootPath() {
        return libraryRootPath;
    }

    /**
     * @return the parseTagsFromSingleFolder
     */
    public boolean isParseTagsFromSingleFolder() {
        return parseTagsFromSingleFolder;
    }

    /**
     * @param parseTagsFromSingleFolder the parseTagsFromSingleFolder to set
     */
    public void setParseTagsFromSingleFolder(boolean parseTagsFromSingleFolder) {
        this.parseTagsFromSingleFolder = parseTagsFromSingleFolder;
    }

    /**
     * @return the parseSingleFolderSeperator
     */
    public String getParseSingleFolderSeperator() {
        return parseSingleFolderSeperator;
    }

    /**
     * @param parseSingleFolderSeperator the parseSingleFolderSeperator to set
     */
    public void setParseSingleFolderSeperator(String parseSingleFolderSeperator) {
        this.parseSingleFolderSeperator = parseSingleFolderSeperator;
    }
    
    /**
     * @return the useEmbeddedTags
     */
    public boolean isUseEmbeddedTags() {
        return useEmbeddedTags;
    }

    /**
     * @param useEmbeddedTags the useEmbeddedTags to set
     */
    public void setUseEmbeddedTags(boolean useEmbeddedTags) {
        this.useEmbeddedTags = useEmbeddedTags;
    }

     /**
     * @return the artistInTrackTitle
     */
    public boolean isArtistInTrackTitle() {
        return artistInTrackTitle;
    }

    /**
     * @param artistInTrackTitle the artistInTrackTitle to set
     */
    public void setArtistInTrackTitle(boolean compilationOptimization) {
        this.artistInTrackTitle = compilationOptimization;
    }

    /**
     * @return the parseTitle
     */
    public boolean isParseTitle() {
        return parseTitle;
    }

    /**
     * @param parseTitle the parseTitle to set
     */
    public void setParseTitle(boolean parseTitle) {
        this.parseTitle = parseTitle;
    }

    public String getCplayExecutablePath() {
        return cplayExecutablePath;
    }

    public void setCplayExecutablePath(String cplayExecutablePath) {
        this.cplayExecutablePath = cplayExecutablePath;
    }

    public String getFoobarExecutablePath() {
        return foobarExecutablePath;
    }

    public void setFoobarExecutablePath(String foobarExecutablePath) {
        this.foobarExecutablePath = foobarExecutablePath;
    }

    public boolean isEnableCplay() {
        return enableCplay;
    }

    public void setEnableCplay(boolean enableCplay) {
        this.enableCplay = enableCplay;
    }

    public boolean isEnableFoobar() {
        return enableFoobar;
    }

    public void setEnableFoobar(boolean enableFoobar) {
        this.enableFoobar = enableFoobar;
    }

    /**
     * @return the selectedLibrary
     */
    public int getSelectedLibrary() {
        return selectedLibrary;
    }

    /**
     * @param selectedLibrary the selectedLibrary to set
     */
    public void setSelectedLibrary(int selectedLibrary) {
        this.selectedLibrary = selectedLibrary;
    }

    /**
     * @return the showSamplingRate
     */
    public boolean isShowSamplingRate() {
        return showSamplingRate;
    }

    /**
     * @param showSamplingRate the showSamplingRate to set
     */
    public void setShowSamplingRate(boolean showSamplingRate) {
        this.showSamplingRate = showSamplingRate;
    }

    /**
     * @return the showFileType
     */
    public boolean isShowFileType() {
        return showFileType;
    }

    /**
     * @param showFileType the showFileType to set
     */
    public void setShowFileType(boolean showFileType) {
        this.showFileType = showFileType;
    }

    /**
     * @return the serverIpAddress
     */
    public String getServerIpAddress() {
        return serverIpAddress;
    }

    /**
     * @param serverIpAddress the serverIpAddress to set
     */
    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public void setLibraryRoots(Vector<LibraryRoot> libraryRoots) {
        this.libraryRoots = libraryRoots;
    }

    public boolean isRunAsServer() {
        return runAsServer;
    }

    public void setRunAsServer(boolean runAsServer) {
        this.runAsServer = runAsServer;
    }

    /**
     * @return the cplayIniPath
     */
    public String getCplayIniPath() {
        return cplayIniPath;
    }

    /**
     * @param cplayIniPath the cplayIniPath to set
     */
    public void setCplayIniPath(String cplayIniPath) {
        this.cplayIniPath = cplayIniPath;
    }

    /**
     * @return the enableSamplingRateChanges
     */
    public boolean isEnableSamplingRateChanges() {
        return enableSamplingRateChanges;
    }

    /**
     * @param enableSamplingRateChanges the enableSamplingRateChanges to set
     */
    public void setEnableSamplingRateChanges(boolean enableSamplingRateChanges) {
        this.enableSamplingRateChanges = enableSamplingRateChanges;
    }

    /**
     * @return the samplingRate
     */
    public CPlayIniIO.SAMPLING_RATE getSamplingRate() {
        return samplingRate;
    }

    /**
     * @param samplingRate the samplingRate to set
     */
    public void setSamplingRate(CPlayIniIO.SAMPLING_RATE samplingRate) {
        this.samplingRate = samplingRate;
    }

    /**
     * @return the enableDynamicUpsampling
     */
    public boolean isEnableDynamicUpsampling() {
        return enableDynamicUpsampling;
    }

    /**
     * @param enableDynamicUpsampling the enableDynamicUpsampling to set
     */
    public void setEnableDynamicUpsampling(boolean enableDynamicUpsampling) {
        this.enableDynamicUpsampling = enableDynamicUpsampling;
    }

    /**
     * @return the maximumSamplingRate
     */
    public CPlayIniIO.SAMPLING_RATE getMaximumSamplingRate() {
        return maximumSamplingRate;
    }

    /**
     * @param maximumSamplingRate the maximumSamplingRate to set
     */
    public void setMaximumSamplingRate(CPlayIniIO.SAMPLING_RATE maximumSamplingRate) {
        this.maximumSamplingRate = maximumSamplingRate;
    }

    /**
     * @return the upsamplingRule
     */
    public CPlayIniIO.DYNAMIC_UPSAMPLING_RULE getUpsamplingRule() {
        return upsamplingRule;
    }

    /**
     * @param upsamplingRule the upsamplingRule to set
     */
    public void setUpsamplingRule(CPlayIniIO.DYNAMIC_UPSAMPLING_RULE upsamplingRule) {
        this.upsamplingRule = upsamplingRule;
    }


}
