package com.centaline.ice;

import com.centaline.ice.gen.book.Message;
import com.centaline.ice.gen.book._OnlineBookDisp;

import Ice.Communicator;
import Ice.Current;
import Ice.ObjectAdapter;
import IceBox.Service;

public class OnlineBookService extends _OnlineBookDisp implements Service{
	private ObjectAdapter _adapter;
	/**  */
	private static final long serialVersionUID = 1L;

	@Override
	public Message bookTick(Message msg, Current __current) {
		System.out.println(msg);
		msg.name = "张三";
		return msg;
	}

	@Override
	public void start(String name, Communicator communicator, String[] arg2) {
		_adapter = communicator.createObjectAdapter(name);
		Ice.Object object = this;
		_adapter.add(object, communicator.stringToIdentity(name));
		_adapter.activate();
		System.out.println("name:"+name);
	}

	@Override
	public void stop() {
		_adapter.destroy();
	}

}
