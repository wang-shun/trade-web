package com.centaline.parportal.mobile.login;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.common.utils.SpringUtils;
import com.aist.common.utils.security.Md5Utils;
import com.aist.uam.auth.remote.UamCrossAppSessionService;
import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.aist.uam.basedata.remote.UamBasedataService;
import com.aist.uam.userorg.remote.UamUserOrgService;
import com.aist.uam.userorg.remote.vo.User;
import com.aist.uam.userorg.remote.vo.UserOrgJob;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centaline.parportal.mobile.aop.RefreshTokenAutoAop;
import com.centaline.parportal.mobile.login.entity.TAppEquipmentBindingDetail;
import com.centaline.parportal.mobile.login.service.MobileUserService;
import com.centaline.parportal.mobile.login.service.TAppEquipmentBindingDetailService;
import com.centaline.parportal.mobile.login.service.TokenService;
import com.centaline.trans.common.vo.MobileHolder;
import com.centaline.trans.common.vo.TokenVo;

/**
 * 
 * @author sstonehu
 * @version $Id: LoginController.java, v 0.1 2016年12月8日 下午6:43:03 sstonehu Exp $
 */
@Controller
@RequestMapping(value = "/mobile")
public class LoginController {

    private final Logger                      logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenService                      tokenService;;
    @Autowired
    private MobileUserService                 mobileUserService;
    @Autowired
    private UamUserOrgService                 uamUserOrgService;
    //    @Autowired
    //    private PropertiesGetService              propertiesGetService;

    @Autowired
    private UamBasedataService                uamBasedataService;

    @Autowired
    private TAppEquipmentBindingDetailService tAppEquipmentBindingDetailService;

    /*@Value("${app.minVesion:1.0}")
    private String minVesion;*/

    /*@Value("${app.minVesionIos:1.0}")
    private String minVesionIos;  //ios
    
    @Value("${app.minVesionAndroid:1.0}")
    private String minVesionAndroid; //android*/

    @Value("${app.superPassword:n1need}")
    private String                            superPassword;

    @Value("${app.superPassword:n0n11d}")
    private String                            ycSuperPassword;

    @Value("${img.sh.centaline.url}")
    private String                            avatarUrlPrix;

    @Autowired
    private UamSessionService                 uamSessionService;

    @Autowired
    private JdbcTemplate                      jdbcTemplate;

    @Autowired
    private UamCrossAppSessionService         uamCrossAppSessionServiceServer;

    /**
     * app用户登录老接口  兼容没有更新app老版本的用户  
     * @param request
     * @param response
     * @param source
     * @param username  用户名
     * @param password  密码
     * @param version   版本
     * @param appSys    手机系统(ios/android)
     * @param appModel  手机型号(手机绑定)
     * @param equipmentName  手机品牌
     * @param loginType  登录标示，后台特殊操作
     * @return
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response, String source,
                        @RequestParam(required = true) String username,
                        @RequestParam(required = true) String password,
                        @RequestParam(required = true) String version, String appSys,
                        String appModel, String equipmentName, String loginType) {
        String loginResult = null;
        loginResult = newLogin(username, password, version, appSys, appModel, equipmentName);

        return loginResult;
    }

    private String newLogin(String username, String password, String version, String appSys,
                            String appModel, String equipmentName) {
        String loginMsg = null; //登录提醒
        String result = null;

        //检查用户是否有效
        boolean loginResult = false; //登录成功标示
        User userVo = uamUserOrgService.getUserByUsername(username);
        if (userVo == null) {
            return loginResutl(false, 4, "用户名错误，请重新输入！", null);
        } else if (!"1".equals(userVo.getAvailable())) {
            return loginResutl(false, 4, "该用户已停用！", null);
        }

        //检查是否为超密 / 或yc超密
        SessionUser sessionUser = null;
        if (checkSuperPassword(username, password)
            || this.checkYCSuperPassword(username, password)) {
            //            sessionUser = mobileUserService.getUserInfoByUsername(username);
            loginResult = true;
        } else {
            loginResult = uamUserOrgService.checkPassword(username, password);
        }

        if (loginResult) {//认证通过后，判断是否有登录权限
            sessionUser = mobileUserService.getUserInfoByUsername(username);
            if (!checkJobCode(sessionUser)) {
                loginMsg = "该岗位没有权限";
                return loginResutl(false, 4, loginMsg, null);
            }

            //记录登录日志Log，获取token，登录。
            loggingLogonHistory(username);
            tokenService.generateToken(sessionUser);
            result = loginResutl(loginResult, 3, "登录成功", sessionUser);
        } else {
            loginMsg = "用户名和密码错误，请重新输入";
            result = loginResutl(loginResult, 1, loginMsg, sessionUser);
        }
        return result;
    }

    /**TODO：
     * 检查是否具有登录权限：
     * 1. 所属组织是否可以使用改app
     * 2. 岗位是否具备登录权限
     * @return true允许登录， false不允许登录
     */
    private boolean checkJobCode(SessionUser sessionUser) {
        //TODO
        return true;
    }

    /**
     * 登录更新和绑定结果
     * @param flag 是否成功
     * @param code 标示   0升级； 1用户密码错误 ; 2绑定 ; 3登录成功; 4没权限; 5刷新token失败
     * @param msg  提醒消息
     * @param mobileUserVo 登录成功用户对象
     * @return
     */
    private String loginResutl(boolean loginResult, int code, String msg, SessionUser sessionUser) {
        JSONObject result = new JSONObject();
        JSONObject content = new JSONObject();
        TokenVo token = null;
        String avatar = null;
        int avatarJob = 1;
        if (loginResult && sessionUser != null) {
            token = tokenService.generateToken(sessionUser);
            //            avatarUrl = uamBasedataService.getParam("APP_MOBILE", "LOGINPERSON_IMG_URL")
            //                        + sessionUser.getEmployeeCode() + ".jpg";
            avatar = sessionUser.getAvatar();
            //多岗  
            List<UserOrgJob> userOrgJOb = uamUserOrgService
                .getUserOrgJobByUserId(sessionUser.getId());
            if (null != userOrgJOb && userOrgJOb.size() > 1) {
                avatarJob = userOrgJOb.size();
            } else { //唯一岗
                avatarJob = 1;
            }
        }

        //登录成功，且avatar不为空时，补充头像路径
        if (loginResult && !StringUtils.isEmpty(avatar))
            content.put("avatarUrl", avatarUrlPrix + avatar);
        else
            content.put("avatarUrl", "");
        content.put("ismultiJob", loginResult ? avatarJob : "");
        if (loginResult) {
            content.put("sessionUser", sessionUser);
        }

        result.put("content", content);
        result.put("isSuccess", loginResult);
        result.put("code", loginResult ? null : code);
        result.put("msg", msg);
        result.put("empId", loginResult ? sessionUser.getId() : "");
        result.put("token", loginResult ? token.getToken() : "");
        result.put("tokenExpDate", loginResult ? token.getExpDate() : 0);

        return result.toJSONString();
    }

    /**
    * 根据用户ID，获取用户岗位
    * @return
    */
    @RequestMapping(value = "listUserOrgJob")
    @ResponseBody
    public String listUserOrgJob(@RequestParam(required = true) String empId) {
        if (null == uamUserOrgService) {
            uamUserOrgService = SpringUtils.getBean(UamUserOrgService.class);
        }
        List<UserOrgJob> userOrgJOb = uamUserOrgService.getUserOrgJobByUserId(empId);
        JSONArray array = new JSONArray();
        if (null != userOrgJOb && userOrgJOb.size() > 1) {
            array.addAll(userOrgJOb);
        } else { //唯一岗

        }
        return array.toJSONString();
    }

    /**
     * 根据orgId,jobId切换岗位
     * @param orgId 组织ID
     * @param jobId 岗位ID
     * @return
     */
    @RequestMapping(value = "changSessionOrgAndJob")
    @ResponseBody
    public String changSessionOrgAndJob(HttpServletRequest request,
                                        @RequestParam(required = true) String orgId,
                                        @RequestParam(required = true) String jobId) {

        TokenVo tokenVo = tokenService.getToken(request.getParameter("token"));
        SessionUser sessionUser = uamSessionService.changSessionOrgAndJob(tokenVo.getSessionUser(),
            orgId, jobId);
        JSONObject result = new JSONObject();
        if (!checkJobCode(sessionUser)) {
            //String loginMsg = "APP此岗位不能切换，请至PC端操作";
            result.put("ismultiJob", -1); //APP此岗位不能切换，请至PC端操作
            result.put("avatarUrl", "");
            result.put("code", null);
            return result.toJSONString();
        }

        uamCrossAppSessionServiceServer.putObjectInto(sessionUser.getSsoSessionId(),
            SessionUser.SESSION_USER, sessionUser);//更新DB
        MobileHolder.setMobileUserHolder(sessionUser);//更新切岗后的MobileUserHolder

        //登录人头像
        String loginImgUrl = uamBasedataService.getParam("APP_MOBILE", "LOGINPERSON_IMG_URL");
        loginImgUrl = loginImgUrl + sessionUser.getEmployeeCode() + ".jpg";

        result.put("ismultiJob", 2); //多岗位
        result.put("avatarUrl", loginImgUrl);
        result.put("code", null);
        result.put("sessionUser", sessionUser);
        return result.toJSONString();
    }

    /**
     * app检查token是否快过期，快过期提前5分钟oldToken换newToken
     * @param oldToken
     * @param random
     * @param securityCode
     * @return
     */
    @RequestMapping(value = "refreshToken")
    @ResponseBody
    @RefreshTokenAutoAop
    public String refreshToken(@RequestParam(required = true) String oldToken,
                               @RequestParam(required = true) int random,
                               @RequestParam(required = true) String securityCode) {

        TokenVo oldTokenVo = tokenService.getToken(oldToken);
        //server产生安全码
        String serverSecurityCode = Md5Utils
            .hash(oldToken + oldTokenVo.getSessionUser().getUsername() + random);
        try {
            TokenVo newTokenVo = tokenService.refreshToken(oldToken, securityCode,
                serverSecurityCode);
            MobileHolder.setToken(newTokenVo);
            MobileHolder.setMobileUserHolder(newTokenVo.getSessionUser());

            return loginResutl(true, 3, "刷新token成功", newTokenVo.getSessionUser());

        } catch (Exception e) {
            //e.getMessage();
            return loginResutl(false, 5, e.getMessage(), null);
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Boolean authenticateUsernamePasswordByAD(String username, String password) {
        Boolean result = true;
        try {
            String urlStr = "http://10.4.99.4:8083/ecomservice/CommunicateService.svc/ADLogin";
            URL url = new URL(urlStr);
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();

            urlCon.setDoInput(true);
            urlCon.setDoOutput(true);
            urlCon.setRequestMethod("POST");
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("Content-Type", "application/json");

            DataOutputStream out = new DataOutputStream(urlCon.getOutputStream());
            String content = "{\"UserID\":\"" + username + "\",\"Password\":\"" + password + "\"}";
            // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面
            out.write(content.getBytes("UTF-8"));
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(urlCon.getInputStream()));
            String res = reader.readLine();
            if (!"\"\"".equals(res)) {
                result = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "serverTime")
    @ResponseBody
    public String serverTime() {
        JSONObject result = new JSONObject();
        result.put("serverTime", System.currentTimeMillis());
        return result.toJSONString();
    }

    //超级密码构成
    private Boolean checkSuperPassword(String username, String password) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        String firstCap = username.substring(0, 1).toUpperCase();
        String timeStamp = sdf.format(new Date());
        if (StringUtils.equals(password, firstCap + superPassword + timeStamp)) {
            return true;
        } else {
            return false;
        }
    }

    //誉萃超级密码构成
    private Boolean checkYCSuperPassword(String username, String password) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        String firstCap = username.substring(0, 1).toUpperCase();
        String timeStamp = sdf.format(new Date());
        if (StringUtils.equals(password, firstCap + ycSuperPassword + timeStamp)) {
            return true;
        } else {
            return false;
        }
    }

    /**
    * 记录用户登陆日志
    * @param username
    */
    private void loggingLogonHistory(String username) {
        final String LOGGING_SQL = "INSERT INTO  " + "sctrans.SYS_USER_LOGIN_HISTORY  "
                                   + "(ID, USER_ID, REALNAME, USERNAME, LOGIN_TYPE, LOGIN_NAME, OPER_TYPE, OPER_HOST, "
                                   + "OPER_TIME, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, VERSION, IS_DELETED) "
                                   + "select replace(newid(),'-',''),u.id, u.real_name, u.username, 'yc-par-app',u.username, '','',"
                                   + "getdate(),getdate(),'SYSTEM',getdate(),'SYSTEM', 0, '0' from sctrans.SYS_USER u  "
                                   + "WHERE u.IS_DELETED ='0' and u.AVAILABLE='1' and u.username= ? ";
        try {
            jdbcTemplate.update(LOGGING_SQL, username);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("error in logging logon history");
        }
    }

    /**
     * 用户名密码校验成功,绑定设备
     * 规则:一个账号只能绑定一个uuid，一个uuid可以绑定多个不同账号
     * @param username  用户名
     * @param appModel  设备标示uuid
     * @param appSys    设备系统 ios/android
     * @param equipmentName  设备品牌
     * @param user      登录用户
     * @param loginType 登录类型
     * @return
     */
    private String loginAppBinding(String username, String appModel, String appSys,
                                   String equipmentName, User user) {
        String result = null;
        //判断账号是否绑定，未绑定自动绑定账号和设备，已绑定提示
        TAppEquipmentBindingDetail tAppBindingDetail = tAppEquipmentBindingDetailService
            .selectByUserName(username);
        if (null != tAppBindingDetail) {
            if (StringUtils.isEmpty(appModel) || StringUtils.isBlank(appModel)) {//设备标示为空
                result = "bindingError";
            }
            //规则:一个账号只能绑定一个uuid，一个uuid可以绑定多个不同账号
            if (!appModel.equals(tAppBindingDetail.getEquipmentId())) {//账号已绑定设备
                result = "bingdingAlready";
            }
        } else {
            //绑定
            TAppEquipmentBindingDetail appBinding = new TAppEquipmentBindingDetail();
            appBinding.setAppName("yc-par-app");
            appBinding.setUserId(user.getId());
            appBinding.setUserName(username);
            appBinding.setEmpCode(user.getEmployeeCode()); //员工编号
            appBinding.setEquipmentId(appModel);
            appBinding.setEquipmentSystem(appSys);
            appBinding.setEquipmentName(equipmentName);
            appBinding.setStatus(1); //状态(1 有效、0 无效)
            appBinding.setCreateTime(new Date());
            appBinding.setUpdateTime(new Date());
            tAppEquipmentBindingDetailService.insert(appBinding);
            //result = "bindingSuccess";
        }
        return result;
    }

}
