package com.centaline.ice.spring;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;

import Ice.ObjectPrx;

public class ICEBeanFactory implements SmartLifecycle{
	
	private final Logger LOGGER        = LoggerFactory.getLogger(this.getClass());
    
	private static volatile Ice.Communicator ic = null;

	private String iceLocator = null;

	public void setIceLocator(String iceLocator) {
		this.iceLocator = iceLocator;
	}

	private static final String locatorKey = "--Ice.Default.Locator";

	private Ice.Communicator getIceCommunicator() {
		if (ic == null) {
			synchronized (ICEBeanFactory.class) {
				if (ic == null) {
//					if (iceLocator == null) {
//						ResourceBundle rb = ResourceBundle.getBundle("ice", Locale.ENGLISH);
//						iceLocator = rb.getString(locatorKey);
//					}

					LOGGER.info("Ice client locator is " + iceLocator);
					String[] initParams = new String[] { locatorKey + "=" + iceLocator };
					ic = Ice.Util.initialize(initParams);
				}
			}
		}
		return ic;
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
	
	public ObjectPrx createIceProxy(Class serviceCls){
		ObjectPrx proxy = null;
		String clsname = serviceCls.getName();
		String serviceName = serviceCls.getSimpleName();
		int pos = serviceName.lastIndexOf("Prx");
		if(pos <= 0){
			throw new java.lang.IllegalArgumentException("Invalid ObjectPrx class, class name must end with Prx!");
		}
		String realSvName = serviceName.substring(0, pos);
		try{
			Ice.ObjectPrx base = ic.stringToProxy(realSvName);
			proxy = (ObjectPrx)Class.forName(clsname+"Helper").newInstance();
			Method m1 = proxy.getClass().getDeclaredMethod("uncheckedCast",ObjectPrx.class);
			proxy = (ObjectPrx)m1.invoke(proxy, base);
			return proxy;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public void start() {
		getIceCommunicator();	
	}

	@Override
	public void stop() {
	}

	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		safeShutDown();
	}

}
