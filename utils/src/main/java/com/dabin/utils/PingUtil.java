package com.dabin.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ping网络的工具
 * 
 * @author Dabin
 * 
 */
public class PingUtil {

	/**
	 * ping一下ip
	 * 
	 * @param pingIP
	 * @return 成功返回true，反之为false
	 */
	public static boolean ping(String pingIP) {

		String result = null;

		try {

			String ip = pingIP;// www.baidu.com

			Process p = Runtime.getRuntime().exec("ping -c 2 -w 100 " + ip);// ping2次

			// 读取ping的内容，可不加。
			InputStream input = p.getInputStream();

			BufferedReader in = new BufferedReader(new InputStreamReader(input));

			StringBuffer stringBuffer = new StringBuffer();

			String content = "";

			while ((content = in.readLine()) != null) {

				stringBuffer.append(content);

			}

			Log.i("TTT", "result content : " + stringBuffer.toString());

			// PING的状态

			int status = p.waitFor();

			if (status == 0) {

				result = "successful~";

				return true;

			} else {

				result = "failed~ cannot reach the IP address";

			}

		} catch (IOException e) {

			result = "failed~ IOException";

		} catch (InterruptedException e) {

			result = "failed~ InterruptedException";

		} finally {

			Log.i("TTT", "result = " + result);

		}

		return false;

	}

}
