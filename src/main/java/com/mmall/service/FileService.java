package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by  bin
 * Time： 2017/7/23.
 */
public interface FileService {
    String upload(MultipartFile file, String path);
}
