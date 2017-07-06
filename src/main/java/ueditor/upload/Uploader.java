package ueditor.upload;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import ueditor.define.State;
import ueditor.define.UploadType;

public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;
	private int uploadType;

	public Uploader(HttpServletRequest request, Map<String, Object> conf, int uploadType) {
		this.request = request;
		this.conf = conf;
		this.uploadType = uploadType;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
		State state = null;
		
		switch (uploadType) {
		case UploadType.LOCAL_SERVER:
			if ("true".equals(this.conf.get("isBase64"))) {
				System.out.println("isBase64");
				state = Base64Uploader.save(this.request.getParameter(filedName),
						this.conf);
			} else {
				System.out.println("is not Base64");
				state = BinaryUploader.save(this.request, this.conf);
			}
			break;
		case UploadType.ALIYUN_OSS:
			state = AliyunUploader.uploadBinary(request, conf);
			break;
		default:
			throw new RuntimeException("not allow upload type, 不允许的上传类型");
		}

		return state;
	}
}
