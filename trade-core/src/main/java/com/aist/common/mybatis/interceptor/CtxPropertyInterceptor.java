package com.aist.common.mybatis.interceptor;

import java.sql.Connection;
import java.util.Properties;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import com.aist.common.utils.SpringUtils;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
 
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})   
public class CtxPropertyInterceptor implements Interceptor {
	
 
    private Properties properties;
 
    public Object intercept(Invocation invocation) throws Throwable {
    	// 获取sql属性
    	//MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        //SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        // 获取相关参数
        //Object parameter = invocation.getArgs()[1];
        //Field[] fields = parameter.getClass().getDeclaredFields();
        
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();  
        //通过反射获取到当前RoutingStatementHandler对象的delegate属性  
        StatementHandler delegate = (StatementHandler)ReflectHelper.getFieldValue(handler, "delegate");  
        //获取到当前StatementHandler的 boundSql，这里不管是调用handler.getBoundSql()还是直接调用delegate.getBoundSql()结果是一样的，因为之前已经说过了  
        //RoutingStatementHandler实现的所有StatementHandler接口方法里面都是调用的delegate对应的方法。  
        BoundSql boundSql = delegate.getBoundSql();  
        //MappedStatement mappedStatement = (MappedStatement)ReflectHelper.getFieldValue(delegate, "mappedStatement");
        String sql = boundSql.getSql();
        SessionUser sessionUser = SpringUtils.getBean(UamSessionService.class).getSessionUser();
        String sessionUserId = sessionUser==null?"SYSTEM":sessionUser.getId();
        String baseSql = sql.replaceAll("@\\{SESSION_USER_ID\\}", "'"+sessionUserId+"'");
        //利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句  
        ReflectHelper.setFieldValue(boundSql, "sql", baseSql);  
        
        return invocation.proceed();
    }
    
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
        	 return Plugin.wrap(target,this);
        } else {
            return target;
        }
    }

    public void setProperties(Properties properties) {

    }
}