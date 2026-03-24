package io.mosip.kernel.masterdata.service.impl;

import io.mosip.kernel.masterdata.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.download.url}")
    private String fileUrl;

    @Override
    public InputStream downloadZip() throws Exception {

        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();

        return connection.getInputStream();
    }
}