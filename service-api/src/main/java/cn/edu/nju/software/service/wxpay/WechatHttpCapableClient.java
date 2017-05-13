package cn.edu.nju.software.service.wxpay;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

public class WechatHttpCapableClient {
	private static Logger logger = LoggerFactory
			.getLogger(WechatHttpCapableClient.class);


	protected String sendHttpPostAndReturnString(String url, String body)
			throws IOException {
		logger.trace("Sending POST to URL {}", url);

		String responseText;
		CloseableHttpResponse response = null;
		try {
			HttpPost postRequest = new HttpPost(url);
			postRequest.setHeader("Content-Type", "text/xml;charset=utf-8");
			StringEntity postEntity = new StringEntity(body,
					Charset.forName("UTF-8"));
			postRequest.setEntity(postEntity);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			response = httpClient.execute(postRequest);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new IOException("Received not 200 OK status code:"
						+ response.getStatusLine().toString());
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				responseText = EntityUtils.toString(entity, "UTF-8");
				logger.debug("Received Unified Order resposne from wechat: "
						+ responseText);
				return responseText;
			} else {
				return null;
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
}
