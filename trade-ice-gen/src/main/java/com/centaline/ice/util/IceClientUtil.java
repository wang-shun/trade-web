package com.centaline.ice.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import Ice.ObjectPrx;

public class IceClientUtil {
	private static volatile Ice.Communicator ic = null;

	private static Map<Class, ObjectPrx> cls2PrxMap = new HashMap<Class, ObjectPrx>();

	private static volatile MonitorThread nonitorThread;

	private static volatile long lastAccessTimestamp;

	private static long idleTimeOutSeconds;

	private static String iceLocator = null;

	private static final String locatorKey = "--Ice.Default.Locator";

	private static Ice.Communicator getIceCommunicator() {
		if (ic == null) {
			synchronized (IceClientUtil.class) {
				if (ic == null) {
					if (iceLocator == null) {
						ResourceBundle rb = ResourceBundle.getBundle("ice", Locale.ENGLISH);
						iceLocator = rb.getString(locatorKey);
						idleTimeOutSeconds = Integer.parseInt(rb.getString("idleTimeOutSeconds"));
						System.out.println("Ice client locator is " + iceLocator + " proxy cache time out seconds:"
								+ idleTimeOutSeconds);
					}

					String[] initParams = new String[] { locatorKey + "=" + iceLocator };
					ic = Ice.Util.initialize(initParams);
					createMonitorThread();
				}
			}
		}
		lastAccessTimestamp = System.currentTimeMillis();
		return ic;
	}

	private static void createMonitorThread() {
		nonitorThread = new MonitorThread();
		nonitorThread.setDaemon(true);
		nonitorThread.start();
	}

	// 关闭Communicator,释放资源
	private static void closeCommunicator(Boolean removeServiceCache) {
		synchronized (IceClientUtil.class) {
			if (ic != null) {
				safeShutDown();
				nonitorThread.interrupt();
				if (removeServiceCache && !cls2PrxMap.isEmpty()) {
					try {
						cls2PrxMap.clear();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static void safeShutDown() {
		try {
			ic.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ic.destroy();
			ic = null;
		}
	}
	
	private static ObjectPrx createIceProxy(Ice.Communicator communicator,Class serviceCls){
		ObjectPrx proxy = null;
		String clsname = serviceCls.getName();
		String serviceName = serviceCls.getSimpleName();
		int pos = serviceName.lastIndexOf("Prx");
		if(pos <= 0){
			throw new java.lang.IllegalArgumentException("Invalid ObjectPrx class, class name must end with Prx!");
		}
		String realSvName = serviceName.substring(0, pos);
		try{
			Ice.ObjectPrx base = communicator.stringToProxy(realSvName);
			proxy = (ObjectPrx)Class.forName(clsname+"Helper").newInstance();
			Method m1 = proxy.getClass().getDeclaredMethod("uncheckedCast",ObjectPrx.class);
			proxy = (ObjectPrx)m1.invoke(proxy, base);
			return proxy;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public static ObjectPrx getServicePrx(Class serviceCls){
		ObjectPrx proxy = cls2PrxMap.get(serviceCls);
		if(proxy != null){
			lastAccessTimestamp = System.currentTimeMillis();
			return proxy;
		}
		proxy = createIceProxy(getIceCommunicator(), serviceCls);
		cls2PrxMap.put(serviceCls, proxy);
		lastAccessTimestamp = System.currentTimeMillis();
		return proxy;
	}

	static class MonitorThread extends Thread {

		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					Thread.sleep(5000L);
					if (lastAccessTimestamp + idleTimeOutSeconds * 1000L < System.currentTimeMillis()) {
						closeCommunicator(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
}
