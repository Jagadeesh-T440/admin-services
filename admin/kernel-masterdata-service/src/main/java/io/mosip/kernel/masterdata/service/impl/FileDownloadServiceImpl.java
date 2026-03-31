package io.mosip.kernel.masterdata.service.impl;

import io.mosip.kernel.masterdata.service.FileDownloadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class FileDownloadServiceImpl implements FileDownloadService {

    @Value("${mosip.kernel.biosdk.file.url}")
    private String fileUrl;

    public InputStream downloadZip() throws Exception {
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();
        return connection.getInputStream();
    }
}