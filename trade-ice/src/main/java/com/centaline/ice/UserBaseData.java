package com.centaline.ice;

import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aist.uam.basedata.remote.UamBasedataService;
import com.centaline.ice.gen.baseData._UserBaseDataDisp;
import com.centaline.ice.service.SpringUtil;

import Ice.Communicator;
import Ice.Current;
import Ice.ObjectAdapter;
import IceBox.Service;

public class UserBaseData extends _UserBaseDataDisp implements Service{
	
	private Logger logger = LoggerFactory.getLogger(UserBaseData.class);
	private ObjectAdapter _adapter;
	/**  */
	private static final long serialVersionUID = 1790167146113389412L;

	@Override
	public void start(String name, Communicator communicator, String[] arg2) {
		_adapter = communicator.createObjectAdapter(name);
		Ice.Object object = this;
		_adapter.add(object, communicator.stringToIdentity(name));
		_adapter.activate();
		SpringUtil.startSpringContex();
	}

	@Override
	public void stop() {
		_adapter.destroy();
	}

	@Override
	public String getDictValue(String dictType, String dictCode, Current __current) {
		UamBasedataService basedataService = SpringUtil.getBean(UamBasedataService.class);
		String dictValue = basedataService.getDictValue(dictType, dictCode);
		System.out.println("dictValue"+dictValue);
		return dictValue;
	}
	
}
