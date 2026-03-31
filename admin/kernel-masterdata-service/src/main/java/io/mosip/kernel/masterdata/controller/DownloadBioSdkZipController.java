package io.mosip.kernel.masterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.mosip.kernel.masterdata.service.impl.FileDownloadServiceImpl;

import java.io.InputStream;

@RestController
@RequestMapping("/download")
public class DownloadBioSdkZipController {

    @Autowired
    private FileDownloadServiceImpl fileDownloadService;

    @GetMapping("/bio-sdk")
    public ResponseEntity<InputStreamResource> downloadFile() throws Exception {
    	InputStream inputStream = fileDownloadService.downloadZip();
    	 
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }
}
