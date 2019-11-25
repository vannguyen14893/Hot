package com.shopping.vn.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shopping.vn.service.UploadFileService;
import com.shopping.vn.service.impl.FileCheckDTO;
import com.shopping.vn.utils.ServiceStatus;

@RestController
public class UploadFileController {
	@Autowired
	UploadFileService uploadFileService;

	@PostMapping(value = "/upload-image")
	public ResponseEntity<?> upImage(@RequestParam(value = "file", required = false) List<MultipartFile> file) {
		if (file.isEmpty()) {
			return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
		}
		LinkedList<String> listResult = uploadFileService.uploadImage(file, "");
		return (!listResult.isEmpty()) ? new ResponseEntity<>(listResult, HttpStatus.OK)
				: new ResponseEntity<>(ServiceStatus.UPLOAD_FALSE, HttpStatus.BAD_REQUEST);
	}

	@PostMapping(value = "/delete-image")
	public ResponseEntity<?> deleteFileUpload(@RequestParam String fileNameToDelete) {
		if (uploadFileService.deleteFileUpload("/" + fileNameToDelete)) {
			return new ResponseEntity<>(ServiceStatus.DELETE_SUCCESS, HttpStatus.OK);
		}

		return new ResponseEntity<>(ServiceStatus.VALUE_DONOT_EXIST, HttpStatus.OK);

	}

	@PostMapping(value = "/check-file")
	public ResponseEntity<?> checkFileExist(@RequestBody List<String> arrFileName) {
		if (arrFileName.isEmpty())
			return new ResponseEntity<>(ServiceStatus.VALUE_DONOT_EXIST, HttpStatus.BAD_REQUEST);
		List<FileCheckDTO> listResult = uploadFileService.checkFilesExist(arrFileName, "/");
		return (!listResult.isEmpty()) ? new ResponseEntity<>(listResult, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
