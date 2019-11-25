package com.shopping.vn.service;

import java.util.LinkedList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.shopping.vn.service.impl.FileCheckDTO;

public interface UploadFileService {
	LinkedList<String> uploadImage(List<MultipartFile> listMultipartFile, String path);

	boolean deleteFileUpload(String path);

	List<FileCheckDTO> checkFilesExist(List<String> arrFileName, String path);
}
