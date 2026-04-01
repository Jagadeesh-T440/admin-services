package io.mosip.kernel.masterdata.service.impl;

import io.mosip.kernel.masterdata.service.FileDownloadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class FileDownloadServiceImpl implements FileDownloadService {

	private static final Logger logger = LoggerFactory.getLogger(FileDownloadServiceImpl.class);
	
    @Value("${mosip.kernel.biosdk.file.url}")
    private String fileUrl;

    public InputStream downloadZip() throws Exception {
    	logger.info("Starting Bio SDK zip download from URL: {}", fileUrl);
    	
        URL url = new URL(fileUrl);
        URLConnection connection = url.openConnection();
        logger.debug("Connection established to URL: {}", fileUrl);
        
        InputStream inputStream = connection.getInputStream();
        logger.info("Successfully obtained InputStream for Bio SDK zip");

        return inputStream;
    }
}