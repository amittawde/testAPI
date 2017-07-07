package com.TestAPI;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

/**
 * Unit test for .com.TestAPI
 */
public class AppTest 
    extends TestCase
{


    private ListFiles listFiles;

    @Override
    protected void setUp() throws Exception{
        listFiles = new ListFilesImpl();
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
     * Rigourous Test :-)
     */

    /**
     * Test to check if the api returns the list of files
     */
    public void testListOfFiles()
    {
        List<MyFile> myFileList = listFiles.listFiles("src/test/resources");

        assertTrue( "There are files in the directory", (myFileList.size() > 0) );
        //System.out.println("testApp ran");
    }

    /**
     * Test to check when the directory is empty
     */
    public void testDirectoryWithNoFiles()
    {
        List<MyFile> myFileList = listFiles.listFiles("src/test/resources/example");

        assertTrue( "Directory is empty", (myFileList.size() == 0) );
    }

    /**
     * Test to check if the input directory does not exist
     */
    public void testCheckDirectoryShouldExist()
    {

        List<MyFile> myFileList = listFiles.listFiles("src/test/noDirectory");

        assertTrue( "Directory does not exist", (myFileList == null) );
        //System.out.println("testCheckDirectoryShouldExist");

    }

    /**
     * Test to check if the input is a file rather than directory
     */
    public void testInputFileInsteadofDirectory()
    {
        List<MyFile> myFileList = listFiles.listFiles("src/test/resources/a1.jpg");

        assertTrue( "Its a file not directory", (myFileList == null) );
        //System.out.println("testCheckDirectoryShouldExist");

    }

    /**
     * Test to check for unsupported files
     * prints the supported files in the log file
     * prints the unsupported files in the log file
     */
    public void testShouldHaveUnsupportedFiles()
    {

        String unsupportedFileTypes = "xml,docx";
        int countUnsuportedFiles = 0;

        List<MyFile> myFileList = listFiles.listUnsupportedFileTypes("src/test/resources", unsupportedFileTypes);

        for(MyFile myFile : myFileList)
        {
            if(myFile.isSupported() == false)
                countUnsuportedFiles ++;
        }

        assertTrue( "unsupportedFiles:" + unsupportedFileTypes, (countUnsuportedFiles > 0) );

    }



}
