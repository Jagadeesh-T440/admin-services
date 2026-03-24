package io.mosip.kernel.masterdata.service;

import java.io.InputStream;

public interface FileDownloadService {

    InputStream downloadZip() throws Exception;
}