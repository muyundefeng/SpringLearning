package us.codecraft.webmagic;

/**
 * @author lisheng
 *	自定义apk相关信息
 */
public class myApks {

	public String appName;
	public String appDownloadUrl;
	
	public myApks(String appName, String appDownloadUrl) {
		super();
		this.appName = appName;
		this.appDownloadUrl = appDownloadUrl;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppDownloadUrl() {
		return appDownloadUrl;
	}
	public void setAppDownloadUrl(String appDownloadUrl) {
		this.appDownloadUrl = appDownloadUrl;
	}
	
}
