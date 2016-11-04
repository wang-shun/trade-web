package com.centaline.ice.service;

import Ice.Communicator;
import Ice.ObjectAdapter;
import IceBox.Service;

public class SpringService implements Service{
	private ObjectAdapter _adapter;
	@Override
	public void start(String name, Communicator communicator, String[] arg2) {
		SpringUtil.startSpringContex();
		System.out.println("Spring 启动！");
	}

	@Override
	public void stop() {
		_adapter.destroy();
	}

}
