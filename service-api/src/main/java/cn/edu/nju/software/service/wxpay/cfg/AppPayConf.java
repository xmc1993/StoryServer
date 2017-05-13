package cn.edu.nju.software.service.wxpay.cfg;

public class AppPayConf {
	public String appId;
	public String mchId;
	public String key;

	public boolean isReady() {
		return appId != null && mchId != null && key != null;
	}

	@Override
	public String toString() {
		return "AppPayConf [appId=" + appId + ", mchId=" + mchId + ", key="
				+ key + "]";
	}
}
