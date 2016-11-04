package com.centaline.ice;

import com.centaline.ice.gen.message._SMSServiceDisp;

import Ice.Communicator;
import Ice.Current;
import Ice.ObjectAdapter;
import IceBox.Service;

public class SMSService extends _SMSServiceDisp implements Service{
	private ObjectAdapter _adapter;
	/**  */
	private static final long serialVersionUID = 1790167146113389412L;

	@Override
	public void sendSMs(String msg, Current __current) {
		System.out.println("Send msg:" + msg);
	}

	@Override
	public void start(String name, Communicator communicator, String[] arg2) {
		_adapter = communicator.createObjectAdapter(name);
		Ice.Object object = this;
		_adapter.add(object, communicator.stringToIdentity(name));
//		_adapter.add(object, communicator.stringToProxy("SMSServie@SMSServiceAdapter"));
		_adapter.activate();
		System.out.println("start adapter name:"+name );
	}

	@Override
	public void stop() {
		_adapter.destroy();
	}
	
}
