package io.mosip.kernel.masterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.mosip.kernel.masterdata.service.FileDownloadService;

import java.io.InputStream;

@RestController
@RequestMapping("/download")
public class DownloadBioSdkZipController {

	private static final Logger logger = LoggerFactory.getLogger(DownloadBioSdkZipController.class);
	
    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("/bio-sdk")
    public ResponseEntity<StreamingResponseBody> downloadFile() throws Exception {
        logger.info("Received request to download Bio SDK zip file");

        InputStream inputStream = fileDownloadService.downloadZip();

        StreamingResponseBody stream = outputStream -> {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
        };
        
        logger.info("Successfully Downloaded zip file from service...");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(stream);
    }
}