package com.centaline.trans.engine.core;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.alibaba.fastjson.JSONObject;
import com.centaline.trans.engine.WorkFlowConstant;
import com.centaline.trans.engine.bean.RestStatus;
import com.centaline.trans.engine.exception.WorkFlowException;
import com.centaline.trans.utils.UriUtility;

@Component
public class WorkFlowEngine {
	private String authUserName;

	@Autowired
	private UamSessionService uamSessionService;
	@Autowired
	private UamUserOrgService uamUserOrgService;

	@Value("${trade.workflow.server}")
	private String workflowServer;

	/**
	 * 
	 * @param workflow
	 * @param cl
	 * @param vars
	 * @return
	 */
	public Object RESTfulWorkFlow(String workflow, Object cl, Object vars) {
		return RESTfulWorkFlow(workflow, cl, vars, null);
	}

	/**
	 * 
	 * @param workflow
	 * @param cl
	 * @param queryParameters
	 * @return
	 */
	public Object RESTfulWorkFlow(String workflow, Object cl,
			Map<String, String> queryParameters) {
		return RESTfulWorkFlow(workflow, cl, null, queryParameters);
	}

	/**
	 * 调用 RESTful
	 * 
	 * @param workflow
	 * @param cl
	 * @param vars
	 *            需要传递的参数对象,get请求的pathValues
	 * 
	 *            返回参数
	 * @param queryParameters
	 *            查询条件
	 * @return
	 */
	public Object RESTfulWorkFlow(String workflow, Object cl, Object vars,
			Map<String, String> queryParameters) {
		String uri = WorkFlowConstant.WORK_FLOW_OPREATE.get(workflow);
		if (StringUtils.isBlank(uri)) {
			throw new WorkFlowException("RESTfulWorkFlow:未知的workflow:"
					+ workflow);
		}
		HttpResponse response = null;
		try {
			if (uri.startsWith(WorkFlowConstant.HTTP_TYPE_GET)) {
				uri = uri.substring(WorkFlowConstant.HTTP_TYPE_GET.length());
				response = executeGet(uri, vars, queryParameters);
			} else if (uri.startsWith(WorkFlowConstant.HTTP_TYPE_POST)) {
				uri = uri.substring(WorkFlowConstant.HTTP_TYPE_POST.length());
				response = executePost(uri, vars,queryParameters);
			} else if (uri.startsWith(WorkFlowConstant.HTTP_TYPE_PUT)) {
				uri = uri.substring(WorkFlowConstant.HTTP_TYPE_PUT.length());
				response = executePut(uri, vars, queryParameters);
			} else if (uri.startsWith(WorkFlowConstant.HTTP_TYPE_DELETE)) {
				uri = uri.substring(WorkFlowConstant.HTTP_TYPE_DELETE.length());
				response = executeDelete(uri, vars, queryParameters);
			} else {
				throw new WorkFlowException("只支持 POST,GET,PUT,DELETE 请求");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new WorkFlowException("执行Http请求异常", e);
		}

		RestStatus restStatus = checkResponse(response);

		if (restStatus.isSuccess()) {
			Object result;
			try {
				Class cla;
				if (!(cl instanceof Class)) {
					cla = cl.getClass();
				} else {
					cla = (Class) cl;
				}
				result = paseResponseBody(response, cla);
			} catch (ParseException | IOException e) {
				e.printStackTrace();
				throw new WorkFlowException("解析返回值异常", e);
			}
			return result;
		} else {
			HttpEntity entity = response.getEntity();
			try {
				String str = EntityUtils.toString(entity, "UTF-8");
				System.err.println(str);
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}

			throw new WorkFlowException("RESTfulWorkFlow:处理失败:statusCode["
					+ restStatus.getStatusCode() + "]restMsge["
					+ restStatus.getReturnMsg() + "]",restStatus.getStatusCode());
		}
	}

	/**
	 * 获得httpClient
	 * 
	 * @return
	 */
	private HttpClient createHttpClient() {
		// 设置Base Auth验证信息
		CredentialsProvider provider = new BasicCredentialsProvider();
		User user = null;
		if (!StringUtils.isBlank(this.authUserName)) {
			user = uamUserOrgService.getUserByUsername(this.authUserName);
		} else {
			SessionUser u = uamSessionService.getSessionUser();
			user = uamUserOrgService.getUserById(u.getId());
		}

		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				user.getUsername(), user.getPassword());
		provider.setCredentials(AuthScope.ANY, credentials);

		// 创建HttpClient
		HttpClient client = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider).build();
		return client;
	}

	/**
	 * 执行http post
	 * 
	 * @param uri
	 * @param vars
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private HttpResponse executePost(String uri, Object vars,
			Map<String, String> queryParameters)
			throws ClientProtocolException, IOException {
		// 创建HttpClient
		HttpClient client = createHttpClient();
		uri = replacePathVar(uri, vars,queryParameters);
		HttpPost post = new HttpPost(workflowServer + uri);
		String jsonStr = JSONObject.toJSONString(vars);
		HttpEntity entity = new StringEntity(jsonStr,
				ContentType.APPLICATION_JSON);
		post.setEntity(entity);
		HttpResponse response = client.execute(post);
		return response;
	}

	/**
	 * 执行http get
	 * 
	 * @param uri
	 * @param vars
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private HttpResponse executeGet(String uri, Object vars,
			Map<String, String> queryParameters)
			throws ClientProtocolException, IOException {
		// 创建HttpClient
		HttpClient client = createHttpClient();
		uri = replacePathVar(uri, vars,queryParameters);
		HttpGet get = new HttpGet(workflowServer + uri
				+ UriUtility.getQueryString(queryParameters));
		return client.execute(get);
	}

	/**
	 * 执行http get
	 * 
	 * @param uri
	 * @param vars
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private HttpResponse executeDelete(String uri, Object vars,
			Map<String, String> queryParameters)
			throws ClientProtocolException, IOException {
		// 创建HttpClient
		HttpClient client = createHttpClient();
		uri = replacePathVar(uri, vars,queryParameters);
		HttpDelete delete = new HttpDelete(workflowServer + uri
				+ UriUtility.getQueryString(queryParameters));
		return client.execute(delete);
	}

	/**
	 * 执行http put
	 * 
	 * @param uri
	 * @param vars
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private HttpResponse executePut(String uri, Object vars,
			Map<String, String> queryParameters)
			throws ClientProtocolException, IOException {
		// 创建HttpClient
		HttpClient client = createHttpClient();
		uri = replacePathVar(uri, vars,queryParameters);
		HttpPut put = new HttpPut(workflowServer + uri
				+ UriUtility.getQueryString(queryParameters));
		String jsonStr = JSONObject.toJSONString(vars);
		HttpEntity entity = new StringEntity(jsonStr,
				ContentType.APPLICATION_JSON);
		put.setEntity(entity);

		return client.execute(put);
	}

	/**
	 * 替换path value
	 * 
	 * @param url
	 * @param obj
	 * @return
	 */
	private String replacePathVar(String url, Object obj,Map<String,String>queryString) {
		if (StringUtils.isBlank(url)) {
			return url;
		}
		String str = url;
		String tempContent = url;
		String regex = "\\{\\w*\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			String temp = str.substring(matcher.start(), matcher.end());
			String maStr = temp.substring(1, temp.length() - 1);
			Object val=null;
			if(queryString!=null&&!StringUtils.isBlank(queryString.get(maStr))){
				val=queryString.get(maStr);
				queryString.remove(maStr);
			}else{
				val= getReaVal(obj, maStr);	
			}
			tempContent = tempContent.replace(temp,
					val == null ? "" : val.toString());
		}
		return tempContent;
	}

	/**
	 * 获得path value的值并置空对象的值
	 * 
	 * @param obj
	 * @param pathVal
	 * @return
	 */
	private Object getReaVal(Object obj, String pathVal) {
		if (obj instanceof Map) {
			Object val = ((Map) obj).get(pathVal);
			((Map) obj).put(pathVal, null);
			return val;
		}
		try {
			PropertyDescriptor pd;

			pd = new PropertyDescriptor(pathVal, obj.getClass());
			Method method = pd.getReadMethod();
			Object val;
			val = method.invoke(obj);
			method = pd.getWriteMethod();
			Class clas = method.getParameterTypes()[0];
			method.invoke(obj, new Object[] { null });
			return val;

		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			throw new WorkFlowException("读取属性" + pathVal + "失败", e);
		} catch (IntrospectionException e) {
			throw new WorkFlowException("不存在该属性:" + pathVal, e);
		}
	}

	/**
	 * 检查Http返回头
	 * 
	 * @param response
	 * @return
	 */
	private RestStatus checkResponse(HttpResponse response) {
		RestStatus r = new RestStatus();
		r.setSuccess(WorkFlowConstant.SCUESSFUL_STATUS_CODE.contains(response
				.getStatusLine().getStatusCode()));
		r.setReturnMsg(response.getStatusLine().getReasonPhrase());
		r.setStatusCode(response.getStatusLine().getStatusCode());
		return r;
	}

	/**
	 * 解析返回值
	 * 
	 * @param response
	 * @param cl
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	private Object paseResponseBody(HttpResponse response, Class cl)
			throws ParseException, IOException {
		HttpEntity entity = response.getEntity();
		if (entity == null)
			return null;
		String str = EntityUtils.toString(entity, "UTF-8");
		if (StringUtils.isBlank(str))
			return null;
		Object t = JSONObject.parseObject(str, cl);
		return t;
	}

	/**
	 * @return the authUserName
	 */
	public String getAuthUserName() {
		return authUserName;
	}

	/**
	 * @param authUserName
	 *            the authUserName to set
	 */
	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
	}
}
