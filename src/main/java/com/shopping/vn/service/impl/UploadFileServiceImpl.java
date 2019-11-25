package com.shopping.vn.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.shopping.vn.service.UploadFileService;

@Component
public class UploadFileServiceImpl implements UploadFileService {
	@Value("${path.file.upload.stored}")
	private String parentPath;

	@Override
	public LinkedList<String> uploadImage(List<MultipartFile> listMultipartFile, String path) {
		if (!new File(parentPath + path).exists()) {
			new File(parentPath + path).mkdirs();
		}
		LinkedList<String> messages = new LinkedList<>();
		for (MultipartFile file : listMultipartFile) {
			StringBuilder builder = new StringBuilder();

			String errors = checkImageInput(file, path);
			if (!errors.isEmpty()) {
				builder.append(file.getOriginalFilename());
				builder.append(errors);
			} else {
				try {
					builder.append(
							this.saveFile(file, path, this.createKeyForUpload(file, path)) ? "success" : "false");
				} catch (IOException e) {

					builder.append("false");

				}
			}
			messages.add(builder.toString());
		}
		return messages;
	}

	public String checkImageInput(MultipartFile multipartFile, String path) {
		StringBuilder message = new StringBuilder();
		if ((multipartFile.getSize() > 3 * 1024 * 1024))
			message.append(" Size is more than 3MB.");
		if (!filterImage(multipartFile))
			message.append(" Invalid image's format.");
		return message.toString();
	}

	public boolean filterImage(MultipartFile multipartFile) {
		String[] typerImage = { "image/jpg", "image/jpeg", "image/png" };
		for (int i = 0; i < typerImage.length; i++) {
			if (typerImage[i].equals(multipartFile.getContentType()))
				return true;
		}
		return false;
	}

	private boolean saveFile(MultipartFile multipartFile, String path, String key) throws IOException {
		if (multipartFile == null) {
			return false;
		} else {
			// save file.
			if (key == null)
				key = "";
			File file = new File(parentPath + path + key + multipartFile.getOriginalFilename());
			multipartFile.transferTo(file);
			return file.exists();
		}
	}

	private String createKeyForUpload(MultipartFile file, String path) {
		StringBuilder key = new StringBuilder();
		int i = 0;
		while ((new File(parentPath + path + key + file.getOriginalFilename())).exists()) {
			i += 1;
			key.setLength(0);
			key.append(i);
		}
		return key.toString();
	}

	public boolean deleteFileUpload(String path) {
		File file = new File(parentPath + path);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	public List<FileCheckDTO> checkFilesExist(List<String> arrFileName, String path) {
		List<FileCheckDTO> listResult = new LinkedList<>();
		for (String strFileName : arrFileName) {
			FileCheckDTO file = new FileCheckDTO();
			file.setFileName(strFileName);
			file.setExist((new File(parentPath + path + strFileName).exists()) ? true : false);
			listResult.add(file);
		}
		return listResult;
	}

}
