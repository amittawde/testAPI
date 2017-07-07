package com.TestAPI;

/**
 * Created by amittawade on 07/07/2017.
 * This class stores the meta data about the files like its name, extension type, MIME type, size
 * and whether it is supported or unsupported type
 */
public class FileInfo {

    private String name;
    private String type;
    private String mimeType;
    private long size;
    private boolean supported;


    /*
     * Constructor class assigning values
     */
    FileInfo(String name, String type, String mimeType, long size, boolean supported){
        this.name = name;
        this.type = type;
        this.mimeType = mimeType;
        this.size = size;
        this.supported = supported;

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public long getSize() {
        return size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public boolean isSupported() {
        return supported;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setSupported(boolean supported) {
        this.supported = supported;
    }
}
