package com.atech.utils.encode;

/**
 * Created by andy on 27.05.17.
 */
public interface ChecksumInterface
{

    String getChecksum(ChecksumSettings source, String data) throws Exception;

}
