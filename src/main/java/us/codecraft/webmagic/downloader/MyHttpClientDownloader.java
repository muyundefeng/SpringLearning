package us.codecraft.webmagic.downloader;

import com.appCrawler.utils.PropertiesUtil;
import com.gargoylesoftware.htmlunit.html.HtmlS;
import com.google.common.collect.Sets;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.xml.serializer.utils.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.HttpConstant;
import us.codecraft.webmagic.utils.UrlUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * The http downloader based on HttpClient.
 * 
 * 根据site信息和HttpclientGenerator类产生链接请求(包括请求头，编码，以及代理信息等)， 将response信息转化成page对象
 * 
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
@ThreadSafe
public class MyHttpClientDownloader extends AbstractDownloader {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<String, CloseableHttpClient> httpClients = new HashMap<String, CloseableHttpClient>();
	// private final RequestConfig config = RequestConfig.custom().setProxy(new
	// HttpHost("proxy.buptnsrc.com", 8002, "http")).build();

	private HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

	private CloseableHttpClient getHttpClient(Site site) {
		if (site == null) {
			return httpClientGenerator.getClient(null);
		}
		String domain = site.getDomain();
		CloseableHttpClient httpClient = httpClients.get(domain);
		if (httpClient == null) {
			synchronized (this) {// 多线程的同步！！！
				httpClient = httpClients.get(domain);
				if (httpClient == null) {
					httpClient = httpClientGenerator.getClient(site);
					httpClients.put(domain, httpClient);
				}
			}
		}

		return httpClient;
	}

	@Override
	public Page download(Request request, Task task) {
		Site site = null;
		if (task != null) {
			site = task.getSite();
		}
		Set<Integer> acceptStatCode;// 记录请求状态码
		String charset = null;
		Map<String, String> headers = null;
		if (site != null) {
			acceptStatCode = site.getAcceptStatCode();
			charset = site.getCharset();
			headers = site.getHeaders();
		} else {
			acceptStatCode = Sets.newHashSet(200);// 利用guava简化集合操作，官方教程http://ifeve.com/google-guava/
		}
		logger.info("downloading page {}", request.getUrl());
		CloseableHttpResponse httpResponse = null;
		int statusCode = 0;
		try {
			
			HttpUriRequest httpUriRequest = getHttpUriRequest(request, site,
					headers);
			if(request.getUrl().contains("http://search.downkr.com/"))
			{
				httpUriRequest.addHeader("Referer", "http://www.downkr.com/");
			}
			// 防止反爬虫
			String[] UserAgent = {
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
					"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
					"Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
					"Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36"};
			
			Random random = new Random();
			int rand = random.nextInt(UserAgent.length);
			httpUriRequest
					.addHeader(
							"User-Agent",
							UserAgent[rand]);
			//httpUriRequest.addHeader("Accept-Encoding", "gzip");
			httpUriRequest.addHeader("Content-Encoding", "identity");
			httpUriRequest.addHeader("Content-Type", "text/html");
			httpUriRequest.addHeader("Accept-Charset", "UTF-8");

			// httpUriRequest.addHeader("Transfer-Encoding", "chunked");
			// httpUriRequest.addHeader("X-Forwarded-For", "123.116.140.178");
			// Transfer-Encoding

			// httpUriRequest.addHeader("Accept",
			// "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			// httpUriRequest.addHeader("Accept-Encoding",
			// "gzip, deflate, sdch");
			// httpUriRequest.addHeader("Accept-Language",
			// "zh-CN,zh;q=0.8,en;q=0.6");
			// httpUriRequest.addHeader("Connection", "keep-alive");
			// httpUriRequest.addHeader("Cookie",
			// "muuid=1431567819898_2635; JSESSIONID=aaaalvlol75FwdXWMJn_u; __utma=127562001.333111101.1431569978.1445935469.1445946086.6; __utmb=127562001.3.10.1445946086; __utmc=127562001; __utmz=127562001.1445946086.6.3.utmcsr=10.3.8.211|utmccn=(referral)|utmcmd=referral|utmcct=/");
			// httpUriRequest.addHeader("DNT", "1");
			// httpUriRequest.addHeader("Host", "app.suning.com");
			// httpUriRequest.addHeader("Upgrade-Insecure-Requests", "1");
			// httpUriRequest.addHeader("User-Agent",
			// "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");
			// //httpUriRequest.addHeader("Referer",
			// "http://app.suning.com/android");
			// CloseableHttpClient httpClient = getHttpClient(site);

			// System.out.println("httpClient="+httpClient.toString());
			// RequestBuilder requestBuilder =
			// RequestBuilder.get().setUri("http://app.suning.com/android");
			// HttpUriRequest httpUriRequest2 = requestBuilder.build();
			// CloseableHttpClient httpClient = HttpClients.createDefault();
			// httpResponse = httpClient.execute(httpUriRequest2);

			httpResponse = getHttpClient(site).execute(httpUriRequest);

			// HttpEntity httpEntity = httpResponse.getEntity();
			// // httpResponse.setEntity(httpEntity);
			//
			// BufferedInputStream instream = new
			// BufferedInputStream(httpEntity.getContent());
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(instream,"utf-8"));
			// String tempString = "";
			// while((tempString=reader.readLine()) !=null)
			// System.out.println(tempString);

			// Header[] headers2 = httpResponse.getAllHeaders();
			// for(int i=0;i<headers2.length;i++) {
			// System.out.println(headers2[i].getName() +"=="+
			// headers2[i].getValue());
			// }

			// System.out.println("Headers: "+httpResponse.getAllHeaders());
			statusCode = httpResponse.getStatusLine().getStatusCode();
			request.putExtra(Request.STATUS_CODE, statusCode);
			// request.putExtra("ResponseHeader", httpResponse.getAllHeaders());

			if (statusAccept(acceptStatCode, statusCode)) {
				// sysHtml(httpResponse);
				Page page = handleResponse(request, charset, httpResponse, task);
				onSuccess(request);
				return page;
			} else {
				logger.warn("code error " + statusCode + "\t"
						+ request.getUrl());
				return null;
			}
		} catch (IOException e) {
			logger.warn("download page " + request.getUrl() + " error", e);
			if (site.getCycleRetryTimes() > 0) {
				return addToCycleRetry(request, site);
			}
			onError(request);
			return null;
		} finally {
			request.putExtra(Request.STATUS_CODE, statusCode);
			try {
				if (httpResponse != null) {
					// ensure the connection is released back to pool
					EntityUtils.consume(httpResponse.getEntity());
				}
			} catch (IOException e) {
				logger.warn("close response fail", e);
			}
		}
	}

	/*
	 * //为苏宁这个网站单独使用的函数，这个网站的一些网页，比如：http://app.suning.com/android
	 * 使用httpclient无法获取到完整的页面信息，需要忽略这些异常，使爬虫能够继续
	 */
	private String sysHtml(HttpResponse httpResponse) {
		String tempString = "";
		HttpEntity httpEntity = httpResponse.getEntity();
		// httpResponse.setEntity(httpEntity);
		BufferedInputStream instream;
		String htmlString = "";
		try {
			instream = new BufferedInputStream(httpEntity.getContent());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					instream, "utf-8"));
			while ((tempString = reader.readLine()) != null)
				htmlString += tempString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// System.out.println(htmlString);
			return htmlString;
		}
		// System.out.println(htmlString);
		return htmlString;
	}

	@Override
	public void setThread(int thread) {
		httpClientGenerator.setPoolSize(thread);
	}

	protected boolean statusAccept(Set<Integer> acceptStatCode, int statusCode) {
		return acceptStatCode.contains(statusCode);
	}

	protected HttpUriRequest getHttpUriRequest(Request request, Site site,
			Map<String, String> headers) {
		RequestBuilder requestBuilder = selectRequestMethod(request).setUri(
				request.getUrl());
		// requestBuilder.setConfig(config);
		if (headers != null) {
			for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
				requestBuilder.addHeader(headerEntry.getKey(),
						headerEntry.getValue());
			}
		}
		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom()
				.setConnectionRequestTimeout(site.getTimeOut())
				.setSocketTimeout(site.getTimeOut())
				.setConnectTimeout(site.getTimeOut())
				.setCookieSpec(CookieSpecs.BEST_MATCH);
		// get proxy info from property file
		if (PropertiesUtil.getCrawlerProxyEnable()) {

			// HttpHost host = site.getHttpProxyFromPool();
			String[] hostAndPost = PropertiesUtil.getCrawlerProxyHostAndPort();
			String[] weight = PropertiesUtil.getCrawlerProxyHostPortWeight();
			// 获取随机数
			Random random = new Random();
			int rand = random.nextInt(hostAndPost.length);
			// if(rand >=2) rand=2;
			// 取出数组中的域名和端口号
			String proxyHost = hostAndPost[rand].split(":")[0];
			int proxyPort = Integer.parseInt(hostAndPost[rand].split(":")[1]);
			logger.info("host=" + proxyHost + " proxyPort=" + proxyPort);

			HttpHost host = new HttpHost(proxyHost, proxyPort, "http");
			// HttpHost host = new
			// HttpHost(PropertiesUtil.getCrawlerProxyHost(),
			// PropertiesUtil.getCrawlerProxyPort(), "http");
			requestConfigBuilder.setProxy(host);
			request.putExtra(Request.PROXY, host);

		}
		requestBuilder.setConfig(requestConfigBuilder.build());
		// requestBuilder.setConfig(config);
		return requestBuilder.build();
	}

	protected RequestBuilder selectRequestMethod(Request request) {
		String method = request.getMethod();
		if (method == null || method.equalsIgnoreCase(HttpConstant.Method.GET)) {
			// default get
			return RequestBuilder.get();
		} else if (method.equalsIgnoreCase(HttpConstant.Method.POST)) {
			RequestBuilder requestBuilder = RequestBuilder.post();
			NameValuePair[] nameValuePair = (NameValuePair[]) request
					.getExtra("keyword");
			// HeaderElement nameValuePair = new HeaderElement("keyword" ,
			// (String)request.getExtra("keyword"));
			if (nameValuePair.length > 0) {
				requestBuilder.addParameters(nameValuePair);
			}

			return requestBuilder;
		} else if (method.equalsIgnoreCase(HttpConstant.Method.HEAD)) {
			return RequestBuilder.head();
		} else if (method.equalsIgnoreCase(HttpConstant.Method.PUT)) {
			return RequestBuilder.put();
		} else if (method.equalsIgnoreCase(HttpConstant.Method.DELETE)) {
			return RequestBuilder.delete();
		} else if (method.equalsIgnoreCase(HttpConstant.Method.TRACE)) {
			return RequestBuilder.trace();
		}
		throw new IllegalArgumentException("Illegal HTTP Method " + method);
	}

	protected Page handleResponse(Request request, String charset,
			HttpResponse httpResponse, Task task) throws IOException {
		String content = getContent(charset, httpResponse, request);
		//System.out.println("content:"+content);
		
		Page page = new Page();
		page.setRawText(content);
		page.setUrl(new PlainText(request.getUrl()));
		page.setRequest(request);
		page.setStatusCode(httpResponse.getStatusLine().getStatusCode());

		return page;
	}

	protected String getContent(String charset, HttpResponse httpResponse,
			Request request) throws IOException {

		// 如果是苏宁这个网站，那么调用sysHtml函数获取页面
		if (request.getUrl().toString().contains("suning.com"))
			return sysHtml(httpResponse);
		if (request.getUrl().toString().contains("cncrk.com"))
			return sysHtml(httpResponse);

		// System.out.println("charset="+charset);
		if (charset == null) {
			byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity()
					.getContent());
			String htmlCharset = getHtmlCharset(httpResponse, contentBytes);
			if (htmlCharset != null) {
				return new String(contentBytes, htmlCharset);
			} else {
				logger.warn(
						"Charset autodetect failed, use {} as charset. Please specify charset in Site.setCharset()",
						Charset.defaultCharset());
				return new String(contentBytes);
			}
		} else {

			// if(httpResponse.getEntity().toString().contains("GzipDecompressingEntity")){
			// Header[] headers = httpResponse.getAllHeaders();
			// for(int i=0;i<headers.length;i++){
			// System.out.println(headers[i].getName()+" "+headers[i].getValue());
			// }
			//
			//
			// System.out.println(httpResponse.getEntity().getContent());
			//
			//
			// httpResponse.setEntity(new
			// GzipDecompressingEntity(httpResponse.getEntity()));
			// System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(),
			// charset));
			// GzipDecompressingEntity decompressingEntity =
			// (GzipDecompressingEntity)httpResponse.getEntity();
			// System.out.println(decompressingEntity.getContentType());
			// return IOUtils.toString(decompressingEntity.getContent(),
			// charset);
			// }
			// if(httpResponse.getEntity().toString().contains("GzipDecompressingEntity")){
			// System.out.println("It's a GzipcompressingEntity");
			// return SinglePageDownloader.getHtml(request.getUrl().toString());
			// }
			// if(httpResponse.getEntity().toString().contains("GzipDecompressingEntity")){
			// Header[] headers = httpResponse.getAllHeaders();
			// for(int i=0;i<headers.length;i++){
			// System.out.println(headers[i].getName()+"="+headers[i].getValue());
			// }
			// }
			// HttpEntity httpEntity = httpResponse.getEntity();
			// GzipDecompressingEntity entity=new
			// GzipDecompressingEntity(httpEntity);
			// InputStream in = entity.getContent();
			// String str = IOUtils.toString(in);
			// System.out.println(str);
			// System.out.println(httpResponse.getEntity().getContentLength());
			//System.out.println(httpResponse.getEntity().getContent());
			return IOUtils.toString(httpResponse.getEntity().getContent(),
					charset);
		}
	}

	protected String getHtmlCharset(HttpResponse httpResponse,
			byte[] contentBytes) throws IOException {
		String charset;
		// charset
		// 1、encoding in http header Content-Type
		String value = httpResponse.getEntity().getContentType().getValue();
		charset = UrlUtils.getCharset(value);
		if (StringUtils.isNotBlank(charset)) {
			logger.debug("Auto get charset: {}", charset);
			return charset;
		}
		// use default charset to decode first time
		Charset defaultCharset = Charset.defaultCharset();
		String content = new String(contentBytes, defaultCharset.name());
		// 2、charset in meta
		if (StringUtils.isNotEmpty(content)) {
			Document document = Jsoup.parse(content);
			Elements links = document.select("meta");
			for (Element link : links) {
				// 2.1、html4.01 <meta http-equiv="Content-Type"
				// content="text/html; charset=UTF-8" />
				String metaContent = link.attr("content");
				String metaCharset = link.attr("charset");
				if (metaContent.indexOf("charset") != -1) {
					metaContent = metaContent.substring(
							metaContent.indexOf("charset"),
							metaContent.length());
					charset = metaContent.split("=")[1];
					break;
				}
				// 2.2、html5 <meta charset="UTF-8" />
				else if (StringUtils.isNotEmpty(metaCharset)) {
					charset = metaCharset;
					break;
				}
			}
		}
		logger.debug("Auto get charset: {}", charset);
		// 3、todo use tools as cpdetector for content decode
		return charset;
	}
}
