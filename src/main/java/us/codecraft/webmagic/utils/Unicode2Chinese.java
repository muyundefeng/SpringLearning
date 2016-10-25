package us.codecraft.webmagic.utils;

public class Unicode2Chinese {
	/**
	 * unicode 转字符串
	 */
	public static String convertUnicode(String ori) {
		char aChar;
		int len = ori.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = ori.charAt(x++);
			if (aChar == '\\') {
				aChar = ori.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = ori.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							return null;
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);

		}
		return outBuffer.toString();
	}

	public static void main(String[] args) {
		System.out.println(convertUnicode("\u300b\u638c\u63a7\u7334\u5b50\u7684\u68a6\u60f3\uff0c\u5e76\u5e2e\u52a9\u5b83\u4eab\u53d7\u4e00\u4e2a\u5b8c\u6574\u7f8e\u4e3d\u7684\u68a6\u5883\u3002\u6536\u96c6\u6843\u5b50\u4fdd\u6301\u68a6\u7684\u6d3b\u529b\uff0c\u6536\u96c6\u77f3\u5b50\u51fb\u843d\u4efb\u4f55\u6709\u5a01\u80c1\u7684\u969c\u788d\u3002\u6e38\u620f\u8bbe\u67097\u4e2a\u4e16\u754c\uff0c\u4e0d\u95f4\u65ad\u7684\u6311\u6218\u3002\n\u3010\u5173\u4e8e\u6570\u636e\u5305\u3011\n\u6e38\u620f\u5305\u542b\u7ea620M\u6570\u636e\u5305\uff0c\u6570\u636e\u5305\u8def\u5f84"));
	}
}
