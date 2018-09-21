package ex032.pyrmont.startup;

import ex032.pyrmont.connector.HttpConnector;

/**
 * 启动类
 * 
 * @author DINGJUN
 *
 */
public class Bootstrap {

	public static void main(String[] args) {
		// 创建连接器等待请求
		HttpConnector connector = new HttpConnector();
		// 一次请求一个线程处理
		connector.start();
	}
}
