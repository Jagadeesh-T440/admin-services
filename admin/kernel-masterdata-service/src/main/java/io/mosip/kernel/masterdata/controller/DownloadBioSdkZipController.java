package io.mosip.kernel.masterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.mosip.kernel.masterdata.service.impl.FileDownloadServiceImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;

@RestController
@RequestMapping("/download")
public class DownloadBioSdkZipController {

    @Autowired
    private FileDownloadServiceImpl fileDownloadService;

    @GetMapping("/bio-sdk")
    public ResponseEntity<?> downloadFile() {
        try {
            URLConnection connection = fileDownloadService.getFileConnection();
            InputStream inputStream = connection.getInputStream();
            if (inputStream == null) {
                return ResponseEntity.notFound().build();
            }

            long fileSize = connection.getContentLengthLong();
            InputStreamResource resource = new InputStreamResource(inputStream);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bio-sdk.zip")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(fileSize > 0 ? fileSize : -1)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            String errorMessage = "Download failed: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)	
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(new InputStreamResource(
                            new ByteArrayInputStream(errorMessage.getBytes())
                    ));
        }
    }
}
