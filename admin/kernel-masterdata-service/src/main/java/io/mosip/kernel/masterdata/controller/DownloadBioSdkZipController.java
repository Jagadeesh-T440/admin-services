package io.mosip.kernel.masterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.mosip.kernel.masterdata.service.FileDownloadService;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/download")
public class DownloadBioSdkZipController {

    private static final Logger logger = LoggerFactory.getLogger(DownloadBioSdkZipController.class);

    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("/bio-sdk")
    public void downloadFile(HttpServletResponse response) throws Exception {

        logger.info("Received request to download Bio SDK zip file");

        try (InputStream inputStream = fileDownloadService.downloadZip();
             OutputStream out = response.getOutputStream()) {
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=Bio_SDK.zip");
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();

            logger.info("File successfully streamed to client");
        }
    }
}
