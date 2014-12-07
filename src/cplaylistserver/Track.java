package cplaylistserver;

/**
 *
 * @author Alan Jordan
 */
public class Track implements java.io.Serializable {
    private String displayName;
    private String fileName;
    private String filePath;
    private String fileType;
    private String samplingRate;
    private String trackPerformer;
    private String trackTitle;

    public Track(String fileName, String trackTitle) {
        this.fileName       = fileName;
        this.trackTitle     = trackTitle;
        this.trackPerformer = null;
        this.displayName    = trackTitle;
    }

    public Track(String fileName, String trackTitle, String trackPerformer) {
        this.fileName       = fileName;
        this.trackTitle     = trackTitle;
        this.trackPerformer = trackPerformer;
        this.displayName    = trackTitle;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }

    /**
     * @return the trackPerformer
     */
    public String getTrackPerformer() {
        return trackPerformer;
    }

    /**
     * @param trackPerformer the trackPerformer to set
     */
    public void setTrackPerformer(String trackPerformer) {
        this.trackPerformer = trackPerformer;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the samplingRate
     */
    public String getSamplingRate() {
        return samplingRate;
    }

    /**
     * @param samplingRate the samplingRate to set
     */
    public void setSamplingRate(String samplingRate) {
        this.samplingRate = samplingRate;
    }

    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
