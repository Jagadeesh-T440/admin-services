package io.mosip.kernel.masterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.mosip.kernel.masterdata.service.FileService;

import java.io.InputStream;

@RestController
@RequestMapping("/api/files")
public class DownloadBioSdkZipController {

    @Autowired
    private FileService fileService;

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadFile() throws Exception {

        InputStream inputStream = fileService.downloadZip();
        
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