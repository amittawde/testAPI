package com.TestAPI;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test for .com.TestAPI
 */
public class AppTest 
    extends TestCase
{

    private IListFiles listFiles;

    @Override
    protected void setUp() throws Exception{

        listFiles = new ListFiles();
    }


    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );

    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }



    /**
     * Test to check if the api returns the list of files
     */
    @org.junit.Test
    public void testListOfFiles()
    {
        List<FileInfo> fileInfoList = listFiles.listFiles("src/test/resources");

        assertTrue( "There are files in the directory", (fileInfoList.size() > 0) );

    }


    /**
     * Test to check when the directory is empty
     */
    @org.junit.Test
    public void testDirectoryWithNoFiles()
    {
        List<FileInfo> fileInfoList = listFiles.listFiles("src/test/resources/example");

        assertTrue( "Directory is empty", (fileInfoList.size() == 0) );
    }

    /**
     * Test to check if the input directory does not exist
     */
    @org.junit.Test
    public void testCheckDirectoryShouldExist()
    {
        List<FileInfo> fileInfoList = listFiles.listFiles("src/test/noDirectory");

        assertTrue( "Directory does not exist", (fileInfoList == null) );
    }

    /**
     * Test to check if the input is a file rather than directory
     */
    @org.junit.Test
    public void testInputFileInsteadofDirectory()
    {
        List<FileInfo> fileInfoList = listFiles.listFiles("src/test/resources/a1.jpg");

        assertTrue( "Its a file not directory", (fileInfoList == null) );
    }

    /**
     * Test to check for unsupported files of type xml and docs
     * prints the supported files list on the console
     * prints the unsupported files list on the console
     */
    @org.junit.Test
    public void testShouldHaveUnsupportedFiles()
    {

        String unsupportedFileTypes = "xml,docx";
        int countUnsupportedFiles = 0;

        List<FileInfo> fileInfoList = listFiles.listUnsupportedFileTypes("src/test/resources", unsupportedFileTypes);

        for(FileInfo fileInfo : fileInfoList)
        {
            if(fileInfo.isSupported() == false)
                countUnsupportedFiles ++;
        }

        assertTrue( "unsupportedFiles:" + unsupportedFileTypes, (countUnsupportedFiles > 0) );

        // print the list of supported files
        printFiles(fileInfoList,true);

        // print the list unsupported files
        printFiles(fileInfoList, false);
    }


    /**
     * Prints the files list in a table on the console
     */
    public void printFiles(List<FileInfo> fileInfoList, boolean supported)
    {

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        if(supported) {
            System.out.println("FileList supported files:");
        }
        else {
            System.out.println("FileList unsupported files:");
        }
        String title = String.format("%-20s| %-72s| %-20s| %-20s|", "Filename", "Mimetype", "Size", "Extension" );
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(title);
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");

        String row;

        for (FileInfo myfile : fileInfoList)
        {
            if(myfile.isSupported() && (supported)) {
                row = String.format("%-20s| %-72s| %20d| %-20s| ", myfile.getName(), myfile.getMimeType(), myfile.getSize(), myfile.getType());
                System.out.println(row);
            }
            else
            {
                if(!myfile.isSupported() && !(supported)) {
                    row = String.format("%-20s| %-72s| %20d| %-20s| ", myfile.getName(), myfile.getMimeType(), myfile.getSize(), myfile.getType());
                    System.out.println(row);
                }
            }

        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
    }



}
