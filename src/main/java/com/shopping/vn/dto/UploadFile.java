package com.shopping.vn.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class UploadFile {
  private MultipartFile[] listMultipartFile;
  private String path;
  private String key;
  private MultipartFile multipartFile;
  public UploadFile(String key, MultipartFile multipartFile) {
    super();
    this.key = key;
    this.multipartFile = multipartFile;
  }
  public UploadFile(MultipartFile multipartFile,String path, String key ) {
    super();
    this.path = path;
    this.key = key;
    this.multipartFile = multipartFile;
  }
  
}
