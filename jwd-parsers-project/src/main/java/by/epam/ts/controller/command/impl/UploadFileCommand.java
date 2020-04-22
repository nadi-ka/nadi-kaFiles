package by.epam.ts.controller.command.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.ts.controller.command.ActionCommand;

public final class UploadFileCommand implements ActionCommand {
	private static final int fileSizeThreshold = 1024 * 1024;
	private static final int fileSizeMax = 1024 * 1024 * 5;
	private static final int maxRequestSize = 1024 * 1024 * 5 * 5;
	private static final String UPLOAD_DIRECTORY = "upload";

	private static final Logger log = LogManager.getLogger(UploadFileCommand.class);

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(fileSizeThreshold);
			factory.setRepository(new File("java.io.tmpdir"));

			// ServletFileUpload represents uploaded file instance;
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(fileSizeMax);
			upload.setSizeMax(maxRequestSize);
			String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				List<FileItem> formItems = upload.parseRequest(request);
				String fileName = null;
				String fieldvalue = null;
				if (formItems != null && formItems.size() > 0) {
					for (FileItem item : formItems) {
						// means that item doesn't represent a simple form field;
						if (!item.isFormField()) {
							fileName = new File(item.getName()).getName();
							String filePath = uploadPath + File.separator + fileName;
							File storeFile = new File(filePath);
							item.write(storeFile);
						} else {
							fieldvalue = item.getString();
						}
					}
				}
				response.sendRedirect(request.getContextPath() + "/controller?command=parse_document&file_name="
						+ fileName + "&parser=" + fieldvalue);
			} catch (FileUploadException e) {
				log.log(Level.ERROR, "Error during calling upload.parseRequest(request);", e);
				response.sendRedirect(
						request.getContextPath() + "/controller?command=get_error_page&message=technical_error");
			} catch (Exception e) {
				log.log(Level.ERROR, "Error during calling item.write(storeFile) from UploadFileCommand.", e);
				response.sendRedirect(
						request.getContextPath() + "/controller?command=get_index_page&message=need_upload_file");
			}

		}

	}

}
