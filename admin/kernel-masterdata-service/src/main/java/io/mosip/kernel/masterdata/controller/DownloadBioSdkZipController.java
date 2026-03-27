package io.mosip.kernel.masterdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.mosip.kernel.masterdata.service.FileDownloadService;
import java.io.OutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/download")
public class DownloadBioSdkZipController {

    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("/bio-sdk")
    public void downloadFile(HttpServletResponse response) throws Exception {

        InputStream inputStream = fileDownloadService.downloadZip();

        if (inputStream == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=bio-sdk.zip");

        byte[] buffer = new byte[1024 * 1024]; // 1MB buffer
        int bytesRead;

        try (InputStream in = inputStream;
             OutputStream out = response.getOutputStream()) {

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            out.flush();
        }
    }
}