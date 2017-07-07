package com.TestAPI;

import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.*;

/**
 * Created by amittawade on 07/07/2017.
 * Implements the API for listing the files in a directory and also providing the ability to find unsupported files
 * Prints the list of files and list if supported/unsupported files in log file /log/Logging.txt
 */
public class ListFiles implements IListFiles {

    private static FileHandler fileTxt;
    private static SimpleFormatter formatterTxt;

    private List<FileInfo> fileInfoList;
    private List<FileInfo> unsupportedFileList;
    private List<FileInfo> supportedFileList;

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    /**
     * Constructor
     */

    ListFiles(){

        try {
            logger.setLevel(Level.FINE);

            String curDir = System.getProperty("user.dir");

            fileTxt = new FileHandler(curDir + "/log/Logging.txt");

            // create a TXT formatter
            formatterTxt = new SimpleFormatter();
            //fileTxt.setFormatter(formatterTxt);
            fileTxt.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    SimpleDateFormat logTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                    Calendar cal = new GregorianCalendar();
                    cal.setTimeInMillis(record.getMillis());
                    return record.getLevel()
                            + logTime.format(cal.getTime())
                            + " || "
                            + record.getSourceClassName().substring(
                            record.getSourceClassName().lastIndexOf(".")+1,
                            record.getSourceClassName().length())
                            + "."
                            + record.getSourceMethodName()
                            + "() : "
                            + record.getMessage() + "\n";
                }
            });

            logger.addHandler(fileTxt);
            logger.setUseParentHandlers(false);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /* Initialise the fileInfoLists */
        this.supportedFileList = new ArrayList<FileInfo>();
        this.fileInfoList = new ArrayList<FileInfo>();
        this.unsupportedFileList = new ArrayList<FileInfo>();
    }


    private Tika mimeTika = new Tika();

    /**
     * Traverses through the directory and returns the list of files
     * If the directory does not exist, it returns a null
     * If the directory
     * @param directoryName to be listed
     * @return List<FileInfo> - List of </FileInfo>
     *
     */
    public List<FileInfo> listFiles(String directoryName) {

        Path directoryPath = Paths.get(directoryName);

        File directory = null;

        if(Files.exists(directoryPath, LinkOption.NOFOLLOW_LINKS) && Files.isDirectory(directoryPath))
        {

            directory = new File(directoryName);

            //get all the files from a directory
            File[] fList = directory.listFiles();
            String mimeType = null;
            String type = null;

            logger.info("FileList in the directory: " + directoryName);
            String title = String.format("%-20s %-72s %20s %-20s ","Filename", "Mimetype", "Size", "Extension" );
            logger.info(title);

            for (File file : fList) {

                if (file.isFile()) {


                    try {
                        mimeType = mimeTika.detect(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    type = getFileExtension(file);


                    fileInfoList.add(new FileInfo(file.getName(), type, mimeType, file.length(), true));
                    //logger.info(file.getName() + ", " +  type + ", " + mimeType + ", " + file.length());
                    String row = String.format("%-20s %-72s %20d %-20s", file.getName(), mimeType, file.length(), type);
                    logger.info(row);
                }
            }
            logger.info("");
            return fileInfoList;
        }
        else
            return null;
    }


    /**
     * List all the files under a directory and return a list of supported and unsupported files
     * @param directoryName to be listed
     * @param unsupportedFileType one or more comma separated unsupported file extensions
     * @return List<FileInfo> - List of supported and unsupported files</FileInfo>
     */
    public List<FileInfo> listUnsupportedFileTypes(String directoryName, String unsupportedFileType) {

        fileInfoList = this.listFiles(directoryName);

        for (FileInfo myfile : fileInfoList)
        {

            if (unsupportedFileType.contains(myfile.getType())) {
                myfile.setSupported(false);
                unsupportedFileList.add(myfile);
            } else {
                supportedFileList.add(myfile);
            }

        }
        printSupportedFiles();
        printUnsupportedFiles();

        return fileInfoList;

    }


    /**
     * Print the list of supported files
     */
    public void printSupportedFiles()
    {

        if(!supportedFileList.isEmpty())
        {
            logger.info("FileList supported files:");
            String title = String.format("%-20s| %-72s| %-20s|  %-20s|", "Filename", "Mimetype", "Size", "Extension" );
            logger.info(title);

            for (FileInfo myfile : supportedFileList)
            {
                String row = String.format("%-20s| %-72s| %20d| %-20s| ", myfile.getName(), myfile.getMimeType(), myfile.getSize(), myfile.getType());
                logger.info(row);
            }
            logger.info("");
        }
    }


    /**
     * Print the list of unsupported files
     */
    public void printUnsupportedFiles()
    {

        if(!unsupportedFileList.isEmpty())
        {
            logger.info("FileList unsupported files:");
            String title = String.format("%-20s| %-72s| %-20s|  %-20s|", "Filename", "Mimetype", "Size", "Extension" );
            logger.info(title);

            for (FileInfo myfile : unsupportedFileList)
            {
                String row = String.format("%-20s| %-72s| %20d| %-20s| ", myfile.getName(), myfile.getMimeType(), myfile.getSize(), myfile.getType());
                logger.info(row);
            }
            logger.info("");
        }
    }



    /**
     * get the file extension if any
     * @param File whose extension to get
     */

    private String getFileExtension(File file)
    {
        String fileName = file.getName();
        if (file.isDirectory())
            return "directory";
        else if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return " ";
    }


}
