package io.mosip.kernel.masterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<InputStreamResource> downloadFile() throws Exception {
    	logger.info("Received request to download Bio SDK zip file");
    	
    	InputStream inputStream = fileDownloadService.downloadZip();
    	logger.info("Successfully Downloaded zip file from service");
    	
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }
}