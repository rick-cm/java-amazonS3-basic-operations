package rcm.project_aws1.model;

import java.io.InputStream;

public class DownloadedResource {
    private String id;
    private String fileName;
    private Long contentLength;
    private InputStream inputStream;

    public DownloadedResource(String id, String fileName, Long contentLength, InputStream inputStream) {
        this.id = id;
        this.fileName = fileName;
        this.contentLength = contentLength;
        this.inputStream = inputStream;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
