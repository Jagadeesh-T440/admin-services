package io.mosip.kernel.masterdata.service;

import java.io.InputStream;

public interface FileService {

    InputStream downloadZip() throws Exception;
}