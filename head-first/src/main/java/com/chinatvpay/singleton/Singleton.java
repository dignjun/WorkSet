package com.chinatvpay.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

/**
 * 老生常当的单例模式
 * 
 * @author DINGJUN
 *
 */
public class Singleton {

	// 使用volatile关键字保证可见性。
	private static volatile Singleton singleton;

	// 私有构造
	private Singleton() {
	}

	public static Singleton getInstance() throws Exception {
		if (singleton == null) {
			Thread.sleep(1L);
			synchronized (Singleton.class) {
				Thread.sleep(1L);
				if (singleton == null) {
					singleton = new Singleton();
				}
			}
		}

		return singleton;
	}

	// 测试
	private static CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
	private static List<Thread> list = new ArrayList<Thread>();

	public static void main(String[] args) {
		// 创建十个线程
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread() {
				public void run() {
					try {
						// 没创建一个线程就栅栏数量就减少一个,为零的时候,持有的所有线程开始执行
						cyclicBarrier.await();
						Singleton instance = getInstance();
						System.out.println(instance.hashCode());
					} catch (Exception e) {
						System.out.println("异常");
					}
				}
			};
			list.add(thread);
		}
		// 启动所有的线程
		for (int j = 0; j < list.size(); j++) {
			list.get(j).start();
		}
	}
}