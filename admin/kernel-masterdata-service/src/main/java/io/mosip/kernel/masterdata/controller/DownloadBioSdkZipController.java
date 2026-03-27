package io.mosip.kernel.masterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import io.mosip.kernel.masterdata.service.FileDownloadService;

import java.io.InputStream;

@RestController
@RequestMapping("/download")
public class DownloadBioSdkZipController {

    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("/bio-sdk")
    public ResponseEntity<StreamingResponseBody> downloadFile() throws Exception {

        InputStream inputStream = fileDownloadService.downloadZip();
        if (inputStream == null) {
            return ResponseEntity.notFound().build();
        }

        StreamingResponseBody stream = outputStream -> {
            byte[] buffer = new byte[1024 * 1024];
            int bytesRead;

            try (inputStream) {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                    outputStream.flush();
                }
            }
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bio-sdk.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(stream);
    }
}