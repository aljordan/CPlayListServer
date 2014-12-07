package cplaylistserver;

import java.io.*;

// TODO: see http://www.javafaq.nu/java-example-code-126.html
// as an example of writing ini file line by line
/**
 *
 * @author Alan Jordan
 */
public class CPlayIniIO {

private CPlayListOptions options;

    public CPlayIniIO(CPlayListOptions options) {
        this.options = options;
    }

    public void writeSamplingRate(SAMPLING_RATE samplingRate) {
        if (!options.isEnableSamplingRateChanges()) {
            System.out.println("Sampling rate changes not allowed according to settings.");
            return;
        }
        
        backupIniFile();
        String strRate = getSamplingRateLineFromEnum(samplingRate);
        
        try {
       
            // Create FileReader Object
            FileReader inputFileReader   = new FileReader(options.getCplayIniPath());
            FileWriter outputFileReader  = new FileWriter(options.getCplayIniPath() + ".new");

            // Create Buffered/PrintWriter Objects
            BufferedReader inputStream   = new BufferedReader(inputFileReader);
            PrintWriter    outputStream  = new PrintWriter(outputFileReader);

            String inLine = null;

            while ((inLine = inputStream.readLine()) != null) {
                if (inLine.startsWith("RATE=")) {
                    outputStream.println(strRate);
                }
                else {
                    outputStream.println(inLine);
                }
            }
            outputStream.close();
            inputStream.close();
            
            //delete current ini file
            boolean deleteSuccess = new File(options.getCplayIniPath()).delete();
            //rename new ini to standard ini
            File newIni = new File(options.getCplayIniPath()+ ".new");
            newIni.renameTo(new File(options.getCplayIniPath()));

        } catch (IOException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public enum SAMPLING_RATE {
        RATE_44100, RATE_48000, RATE_88200,
        RATE_96000, RATE_176400, RATE_192000
    }

    public enum DYNAMIC_UPSAMPLING_RULE {
        UPSAMPLE_TO_HIGHEST_RATE_IN_PLAYLIST,
        UPSAMPLE_TO_HIGHEST_EVEN_MULTIPLE
    }
    
    public static int getSamplingRateIntegerFromEnum(SAMPLING_RATE sRate) {
        int result = 0;
        switch (sRate) {
            case RATE_44100:
                result = 44100;
                break;
            case RATE_48000:
                result = 48000;
                break;
            case RATE_88200:
                result = 88200;
                break;
            case RATE_96000:
                result = 96000;
                break;
            case RATE_176400:
                result = 176400;
                break;
            case RATE_192000:
                result = 192000;
                break;
        }
        return result;
    }

        public static SAMPLING_RATE getEnumFromSamplingRateInteger(int sRate) {
        SAMPLING_RATE result = null;
        switch (sRate) {
            case 44100:
                result = SAMPLING_RATE.RATE_44100;
                break;
            case 48000:
                result = SAMPLING_RATE.RATE_48000;
                break;
            case 88200:
                result = SAMPLING_RATE.RATE_88200;
                break;
            case 96000:
                result = SAMPLING_RATE.RATE_96000;
                break;
            case 176400:
                result = SAMPLING_RATE.RATE_176400;
                break;
            case 192000:
                result = SAMPLING_RATE.RATE_192000;
                break;
        }
        return result;
    }

    private void backupIniFile() {
        // first try deleting the existing backup file
        boolean deleteSuccess = new File(options.getCplayIniPath() + ".bak").delete();
        
        File backupFile = new File(options.getCplayIniPath() + ".bak");
        FileInputStream from = null;
        FileOutputStream to = null;


        try {
            from = new FileInputStream(options.getCplayIniPath());
            to = new FileOutputStream(backupFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead); // write
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (from != null) {
                try {
                    from.close();
                } catch (IOException e) {
                }
            }
            if (to != null) {
                try {
                    to.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    private String getSamplingRateLineFromEnum(SAMPLING_RATE rate) {
        String result = null;
        if (rate != null) {
            switch (rate) {
                case RATE_44100:
                    result = "RATE= 44100.000";
                    break;
                case RATE_48000:
                    result = "RATE= 48000.000";
                    break;
                case RATE_88200:
                    result = "RATE= 88200.000";
                    break;
                case RATE_96000:
                    result = "RATE= 96000.000";
                    break;
                case RATE_176400:
                    result = "RATE=176400.000";
                    break;
                case RATE_192000:
                    result = "RATE=192000.000";
                    break;
            }
        }
        return result;
    }

}
