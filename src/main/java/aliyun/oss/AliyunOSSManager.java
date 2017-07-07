package aliyun.oss;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;

/**
 * @author chenyifei
 * @date 2017-07-06
 */
@Component(value="aliyunOSSManager")
public class AliyunOSSManager {

	@Value("${aliyun.accessKeyId}")
	private String accessKeyId;
	@Value("${aliyun.accessKeySecret}")
	private String accessKeySecret;
	@Value("${aliyun.oss.endpoint}")
	private String endpoint;
	@Value("${aliyun.oss.bucketName}")
	private String bucketName;
	@Value("${aliyun.oss.bucketUrl}")
	private String bucketUrl;

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getBucketUrl() {
		return bucketUrl;
	}

	public void setBucketUrl(String bucketUrl) {
		this.bucketUrl = bucketUrl;
	}

	public void putObject(File file) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 上传文件
		ossClient.putObject(bucketName, file.getName(), file);
		// 关闭client
		ossClient.shutdown();
	}

}
