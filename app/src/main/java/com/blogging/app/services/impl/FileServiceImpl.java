package com.blogging.app.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogging.app.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// File name
		String name = file.getOriginalFilename();
		// abc.png

		// random name generated file
		String randomId = UUID.randomUUID().toString();

		// it will extract the extension from the original file name and add that
		// extendion to the randomId generated
		// ex "550e8400-e29b-41d4-a716-446655440000.jpg"
		String filename1 = randomId.concat(name.substring(name.lastIndexOf(".")));

		// Full Path
		// File.separator is '\' in windows
		// path + File.separator = Returns "/uploads/"
		// path + File.separator + filename1 = Returns
		// "/uploads/550e8400-e29b-41d4-a716-446655440000.jpg"
		String filepath = path + File.separator + filename1;

		// create folder if not created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// file copy
		/*
		 * file.getInputStream() - Obtain InputStream:
		 * 
		 * Assuming file is an instance of MultipartFile (commonly used in Spring for
		 * handling file uploads), the method getInputStream() is called on it.
		 * getInputStream() returns an InputStream that allows you to read the content
		 * of the file. Paths.get(filepath) - Create Path Object:
		 * 
		 * Paths.get(filepath) is a method from the java.nio.file.Paths class. It
		 * creates a Path object from the provided filepath string. A Path represents
		 * the location of a file or directory on the file system. Files.copy(...) -
		 * Copy Content to a New File:
		 * 
		 * Files.copy() is a method from the java.nio.file.Files class. This method is
		 * used to copy data from a source to a target. In this case, it copies the
		 * content from the InputStream obtained from the file to a new file specified
		 * by the Path object. The method signature is copy(Path source, OutputStream
		 * out), where the source is the InputStream obtained from the file, and the
		 * target is the file specified by the Path object.
		 */
		Files.copy(file.getInputStream(), Paths.get(filepath));
		return filename1;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
		String fullpath = path + File.separator + filename;
		
		//InputStream is like a tool that helps read data, such as an image file. 
		InputStream is = new FileInputStream(fullpath);
		return is;
	}

}
