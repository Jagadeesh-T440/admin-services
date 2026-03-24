package io.mosip.kernel.masterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.mosip.kernel.masterdata.service.FileDownloadService;

import java.io.InputStream;

@RestController
@RequestMapping("/download")
public class DownloadBioSdkZipController {

    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("/bio-sdk")
    public ResponseEntity<InputStreamResource> downloadFile() throws Exception {

        InputStream inputStream = fileDownloadService.downloadZip();
        
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }
        InputStreamResource resource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bio-sdk.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}