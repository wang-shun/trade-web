package com.centaline.trans.engine.handle;

import com.centaline.trans.engine.annotation.TaskOperate;;

public interface TaskOperateHandle {
	
	void handle(Object target, Object[] args, String[] method, TaskOperate taskOperate);
}
