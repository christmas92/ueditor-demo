package aliyun.oss;

import java.io.File;

import com.aliyun.oss.OSSClient;

/**
 * @author chenyifei
 * @date 2017-07-06
 */
public class AliyunOSSManager {

	private static final String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
	private static final String accessKeyId = "<yourAccessKeyId>";
	private static final String accessKeySecret = "<yourAccessKeySecret>";
	private static final String bucketName = "lb-test-bucket";
	public static final String bucketUrl = "http://lb-test-bucket.oss-cn-shanghai.aliyuncs.com/";

	public static void putObject(File file) {
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		// 上传文件
		ossClient.putObject(bucketName, file.getName(), file);
		// 关闭client
		ossClient.shutdown();
	}

}
