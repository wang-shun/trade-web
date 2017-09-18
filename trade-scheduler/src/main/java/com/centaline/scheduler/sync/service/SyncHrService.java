package com.centaline.scheduler.sync.service;
/**
 * 同步组织架构 员工 岗位 员工岗位的模板service
 * 各个城市继承此类
 * @author yinchao
 *
 */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.centaline.scheduler.sync.bean.SyncEmpOrgPos;
import com.centaline.scheduler.sync.bean.SyncEmployee;
import com.centaline.scheduler.sync.bean.SyncOrgUnit;
import com.centaline.scheduler.sync.bean.SyncPosition;
import com.centaline.scheduler.sync.enums.CityType;
import com.centaline.scheduler.sync.enums.SyncResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional //增加事务防止删除和同步数据不一致
public abstract class SyncHrService {
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate tradeTemplate;
	private Logger logger = LoggerFactory.getLogger(SyncHrService.class);
	/**
	 * 同步部门信息
	 * @param city 同步的城市
	 * @param user 触发用户
	 */
	public void toSyncOrgUnit(CityType city, String user){
		long start = System.currentTimeMillis();
		SyncResult result = syncOrgUnit(getSyncOrgUnit(), city);
		Map<String,String> exec = initResult();
		if(result.getResult() == SyncResultType.SUCCESS){
			exec.put("code","0");
			exec.put("msg", "");
		}else{
			exec.put("msg", result.getMessage());
		}
		writeLog(city.getName()+"-同步部门信息到中间表",exec,user,(System.currentTimeMillis()-start));
	}
	
	/**
	 * 同步员工信息
	 * @param city 同步的城市
	 * @param user 触发用户
	 */
	public void toSyncEmployee(CityType city,String user){
		long start = System.currentTimeMillis();
		SyncResult result = syncEmployee(getSyncEmployee(),city);
		Map<String,String> exec = initResult();
		if(result.getResult() == SyncResultType.SUCCESS){
			exec.put("code","0");
			exec.put("msg", "");
		}else{
			exec.put("msg", result.getMessage());
		}
		writeLog(city.getName()+"-同步员工信息到中间表",exec,user,(System.currentTimeMillis()-start));
	}
	
	/**
	 * 同步部门岗位信息
	 * @param city 同步的城市
	 * @param user 触发用户
	 */
	public void toSyncPosition(CityType city,String user){
		long start = System.currentTimeMillis();
		SyncResult result = syncPosition(getSyncPosition(),city);
		Map<String,String> exec = initResult();
		if(result.getResult() == SyncResultType.SUCCESS){
			exec.put("code","0");
			exec.put("msg", "");
		}else{
			exec.put("msg", result.getMessage());
		}
		writeLog(city.getName()+"-同步岗位信息到中间表",exec,user,(System.currentTimeMillis()-start));
	}
	
	/**
	 * 同步员工部门岗位信息
	 * @param city 同步的城市
	 * @param user 触发用户
	 */
	public void toSyncEmpOrgPos(CityType city,String user){
		long start = System.currentTimeMillis();
		Map<String,String> exec = initResult();
		SyncResult result = syncEmpOrgPos(getSyncEmpOrgPos(),city);
		if(result.getResult() == SyncResultType.SUCCESS){
			exec.put("code","0");
			exec.put("msg", "");
		}else{
			exec.put("msg", result.getMessage());
		}
		writeLog(city.getName()+"-同步员工部门岗位信息到中间表",exec,user,(System.currentTimeMillis()-start));
	}

	/**
	 * 获取相应系统中需要同步的所有有效的机构信息
	 * @return
	 */
	protected abstract List<SyncOrgUnit> getSyncOrgUnit();
	
	/**
	 * 获取相应系统中需要同步的所有有效的员工信息
	 * @return
	 */
	protected abstract List<SyncEmployee> getSyncEmployee();
	
	/**
	 * 获取相应系统中需要同步的所有有效的部门岗位信息
	 * @return
	 */
	protected abstract List<SyncPosition> getSyncPosition();
	/**
	 * 获取相应系统中需要同步的所有有效的员工部门岗位信息
	 * @return
	 */
	protected abstract List<SyncEmpOrgPos> getSyncEmpOrgPos();
	
	/**
	 * 清除上次同步的对应城市数据
	 * 将本次有效的部门数据插入到中间表中
	 * @param list 待同步的组织架构列表
	 * @param city 同步数据对应的城市
	 * @return
	 */
	protected SyncResult syncOrgUnit(List<SyncOrgUnit> list,CityType city){
		SyncResult result;
		try {
			if(list!=null && list.size()>0){
				//先对应城市的数据再同步
				tradeTemplate.update("delete from sync.SYNC_TMPORG where city=?",new Object[]{city.getCode()},new int[]{Types.VARCHAR});
				StringBuilder batchinsert = new StringBuilder();
				List<Object[]> args = new ArrayList<>();
				batchinsert.append("insert into sync.SYNC_TMPORG(ID,ORG_NAME,ORG_TYPE");
				batchinsert.append(",ORG_CODE,PARENT_ID,FULL_NAME,ORDERBY,CREATE_DATE,END_DATE,TEL,CITY,HROC,CCAI_DEPID)");
				batchinsert.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				for(SyncOrgUnit o : list){
					Object[] obj = {o.getId(),o.getName(),o.getType(),o.getCode(),
							o.getParentId(),o.getFullName(),o.getOrderCode(),
							o.getCreateTime(),o.getEndTime(),o.getTel(),o.getCity(),o.getHroc(),o.getCcaiDepId()};
					args.add(obj);
				}
				tradeTemplate.batchUpdate(batchinsert.toString(),args,
						new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR
								,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR
								,Types.DATE,Types.DATE,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR});
				result = new SyncResult(SyncResultType.SUCCESS, null);
			}else{
				result = new SyncResult(SyncResultType.FAILURE, "未获取到同步数据");
			}
		} catch (Exception e) {
			result = new SyncResult(SyncResultType.FAILURE, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 清除上次同步的对应城市数据
	 * 将本次有效的员工数据插入到中间表中
	 * @param list 待同步的员工列表
	 * @param city 同步数据对应的城市
	 * @return
	 */
	protected SyncResult syncEmployee(List<SyncEmployee> list,CityType city){
		SyncResult result;
		try {
			//同步
			if(list!=null && list.size()>0){
				//先清除对应城市的数据再同步
				tradeTemplate.update("delete from sync.SYNC_TMPEMPLOYEE where city=?",new Object[]{city.getCode()},new int[]{Types.VARCHAR});
				StringBuilder batchinsert = new StringBuilder();
				List<Object[]> args = new ArrayList<>();
				batchinsert.append("insert into sync.SYNC_TMPEMPLOYEE(ID,NAME,SEX,BIRTHDAY,");
				batchinsert.append("EMAIL,ACCOUNT,CODE,MOBILE,DEPT_ID,DEPT_NAME,DEPT_CODE,");
				batchinsert.append("POSITION_ID,POSITION_NAME,CITY)");
				batchinsert.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				for(SyncEmployee e : list){
					Object[] obj = {e.getId(),e.getName(),e.getSex(),e.getBirthday(),e.getEmail(),
							e.getAccount(),e.getCode(),e.getMobile(),e.getDeptId(),e.getDeptName(),
							e.getDeptCode(),e.getPositionId(),e.getPositionName(),e.getCity()};
					args.add(obj);
				}
				tradeTemplate.batchUpdate(batchinsert.toString(),args,
						new int[]{Types.VARCHAR,Types.VARCHAR,Types.TINYINT
								,Types.DATE,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR
								,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR
								,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR});
				result = new SyncResult(SyncResultType.SUCCESS, null);
			}else{
				result = new SyncResult(SyncResultType.FAILURE, "未获取到同步数据");
			}
		} catch (Exception e) {
			result = new SyncResult(SyncResultType.FAILURE, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 清除上次同步的对应城市数据
	 * 将本次有效的部门岗位数据插入到中间表中
	 * @param list 待同步的部门岗位信息
	 * @param city 同步数据对应的城市
	 * @return
	 */
	protected SyncResult syncPosition(List<SyncPosition> list,CityType city){
		SyncResult result;
		try {
			//同步
			if(list!=null && list.size()>0){
				//先清除对应城市的数据再同步
				tradeTemplate.update("delete from sync.SYNC_TMPPOSITION where city=?",new Object[]{city.getCode()},new int[]{Types.VARCHAR});
				StringBuilder batchinsert = new StringBuilder();
				List<Object[]> args = new ArrayList<>();
				batchinsert.append("insert into sync.SYNC_TMPPOSITION(ID,NAME,DEPT_ID,DEPT_CODE,DEPT_NAME,CITY)");
				batchinsert.append(" values(?,?,?,?,?,?)");
				for(SyncPosition p : list){
					Object[] obj = {p.getId(),p.getName(),p.getDeptId(),
							p.getDeptCode(),p.getDeptName(),p.getCity()};
					args.add(obj);
				}
				tradeTemplate.batchUpdate(batchinsert.toString(),args,
						new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR
								,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR});
				result = new SyncResult(SyncResultType.SUCCESS, null);
			}else{
				result = new SyncResult(SyncResultType.FAILURE, "未获取到同步数据");
			}
		} catch (Exception e) {
			result = new SyncResult(SyncResultType.FAILURE, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 清除上次同步的对应城市数据
	 * 将本次有效的员工部门岗位数据插入到中间表中
	 * @param list 待同步的员工部门岗位信息
	 * @param city 同步数据对应的城市
	 * @return
	 */
	protected SyncResult syncEmpOrgPos(List<SyncEmpOrgPos> list,CityType city){
		SyncResult result;
		try {
			//同步
			if(list!=null && list.size()>0){
				//先清除对应城市的数据再同步
				tradeTemplate.update("delete from sync.SYNC_TMPEMP_ORG_POS where city=?",new Object[]{city.getCode()},new int[]{Types.VARCHAR});
				StringBuilder batchinsert = new StringBuilder();
				List<Object[]> args = new ArrayList<>();
				batchinsert.append("insert into sync.SYNC_TMPEMP_ORG_POS(ID,EMP_ID,EMP_NAME,DEPT_ID,DEPT_NAME");
				batchinsert.append(",POS_ID,POS_NAME,ISPRIMARY,CITY,LEADER)");
				batchinsert.append(" values(NEWID(),?,?,?,?,?,?,?,?,?)");
				for(SyncEmpOrgPos eop : list){
					Object[] obj = {eop.getEmpId(),eop.getEmpName(),eop.getDeptId(),
							eop.getDeptName(),eop.getPositionId(),eop.getPositionName()
							,eop.getPrimary(),eop.getCity(),eop.getLeader()};
					args.add(obj);
				}
				tradeTemplate.batchUpdate(batchinsert.toString(),args,
						new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR
								,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.TINYINT,Types.VARCHAR,Types.VARCHAR});
				result = new SyncResult(SyncResultType.SUCCESS, null);
			}else{
				result = new SyncResult(SyncResultType.FAILURE, "未获取到同步数据");
			}
		} catch (Exception e) {
			result = new SyncResult(SyncResultType.FAILURE, e.getMessage());
		}
		return result;
	}
	/**
	 * 获取初始化的Map
	 * @return
	 */
	private Map<String,String> initResult(){
		Map<String,String> map = new HashMap<>();
		map.put("code", Integer.toString(-1));
		map.put("msg", "初始化默认失败");
		return map;
	}
	/**
	 * 执行存储过程
	 * 不能定义为final 否则tradetemplate无法注入
	 * @param cmd 存储过程命令
	 * @return
	 */
	public Map<String,String> doProcExec(final String cmd){
		//TODO 添加cmd的校验 未校验 因为存储过程的入参 和 出参 是动态 该处只注册了2个出参
		long start = System.currentTimeMillis();
		Map<String,String> map = tradeTemplate.execute(new CallableStatementCreator() {
			/**
			 * 定义执行存储过程的名称 注册 输入 输出参数
			 * 并返回定义
			 */
			@Override
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
//				String doCmd = "{call proc_sync_dept(?,?)}";//调用的存储过程名称 以及需要的参数 input output 都需要占位和定义
				CallableStatement cs = con.prepareCall(cmd);
//				cs.setString(1, "param"); //注册输入参数 索引 内容
				cs.registerOutParameter(1, Types.INTEGER);//注册输出参数 结果代码 0成功 -1失败
				cs.registerOutParameter(2, Types.VARCHAR);//当结果代码为-1时，返回的错误信息
				return cs;
			}
		},new CallableStatementCallback<Map<String,String>>() {
			/**
			 * 执行存储过程 并返回结果
			 * cs 为上面定义的返回值
			 */
			@Override
			public Map<String, String> doInCallableStatement(CallableStatement cs)
					throws SQLException, DataAccessException {
				cs.execute();
				int code = cs.getInt(1);//索引为上面注册对应的out索引 
				String msg = cs.getString(2);
				Map<String,String> result = new HashMap<>();
				result.put("code", Integer.toString(code));
				result.put("msg", msg);
				writeLog(cmd,result,"",(System.currentTimeMillis()-start));
				return result;
			}
		});
		return map;
	}
	
	/**
	 * 记录同步日志
	 * @param name 同步内容名称 城市-同步对象
	 * @param result 同步的结果
	 * @param user 调用者	
	 * @param usetime 用时
	 */
	private void writeLog(String name,Map<String,String> result,String user,long usetime){
		StringBuffer buffer = new StringBuffer();
		buffer.append("insert into sync.SYNC_LOG(ID,Name,[Result],ErrorMsg,[User],UseTime,SyncTime) ");
		buffer.append(" values(newid(),?,?,?,?,?,getdate())");
		String code = result.get("code");
		String msg = result.get("msg");
		//写入数据库表日志表中
		tradeTemplate.update(buffer.toString(),new Object[]{name,code,msg,user,usetime},
				new int[]{Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.BIGINT});
		logger.debug(String.format("%s 触发同步 %s 结果：%s 用时:%d ms\r\n", user,name,result,usetime));
		
	}
	
}
