package io.mosip.kernel.masterdata.service;

import java.io.InputStream;
import java.net.URLConnection;

public interface FileDownloadService {

    URLConnection getFileConnection() throws Exception;
}