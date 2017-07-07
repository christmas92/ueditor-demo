package ueditor.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import aliyun.oss.AliyunOSSManager;
import commons.utils.SpringUtils;
import ueditor.PathFormat;
import ueditor.define.AppInfo;
import ueditor.define.BaseState;
import ueditor.define.FileType;
import ueditor.define.State;

/**
 * @author chenyifei
 * @date 2017-07-06
 */
public class AliyunUploader {

	public static final State uploadBinary(HttpServletRequest request, Map<String, Object> conf) {
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);
			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
					
			InputStream is = fileStream.openStream();
			long maxSize = ((Long) conf.get("maxSize")).longValue();
			String fileName = createFileName(suffix);
			State storageState = uploadFile(is, fileName, maxSize);
			
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", originFileName);
			return storageState;
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} catch (IOException e) {
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}
	
	private static State uploadFile(InputStream is, String fileName, long maxSize) throws IOException{
		
		File tmpFile = new File(fileName);
		FileOutputStream fos = new FileOutputStream(tmpFile);
		IOUtils.copy(is, fos);
		long fileSize = tmpFile.length();
		if (fileSize > maxSize) {
			tmpFile.delete();
			return new BaseState(false, AppInfo.MAX_SIZE);
		}
		
		AliyunOSSManager ossManager = (AliyunOSSManager) SpringUtils.getBean("aliyunOSSManager"); //注入AliyunOSSManager
		ossManager.putObject(tmpFile);
		tmpFile.delete();
		
		State storageState = new BaseState(true);
		storageState.putInfo("size", fileSize);
		storageState.putInfo("title", fileName);
		storageState.putInfo("url", ossManager.getBucketUrl() + fileName);

		return storageState;
	}

	private static String createFileName(String suffix) {
		String prefix = PathFormat.parse("{time}{rand:6}");
		return prefix + suffix;
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}

}
