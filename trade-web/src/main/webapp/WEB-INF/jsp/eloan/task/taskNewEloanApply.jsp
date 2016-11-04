<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/tbsp/common/taglibs.jspf"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>金融贷款申请提交</title>

    <link rel="stylesheet" href="${ctx}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/static/font-awesome/css/font-awesome.css">

    <link rel="stylesheet" href="${ctx}/static/css/animate.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/css/style.css" rel="stylesheet">

    <!-- stickUp fixed css -->
    <link rel="stylesheet" href="${ctx}/static/css/plugins/stickup/stickup.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/stickmenu.css">

    <!-- index_css  -->
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/input.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/table.css">
    <link rel="stylesheet" href="${ctx}/static/trans/css/common/report.css">
    <link rel="stylesheet" href="${ctx}/static/iconfont/iconfont.css" ">
</head>

<body>
            <!-- main Start -->
            <div class="row wrapper border-bottom white-bg page-heading stickup-nav-bar">
                <ul class="nav navbar-nav">
                    <li class="menuItem active"><a href="#reportOne">关联交易单</a></li>
                    <li class="menuItem"><a href="#reportTwo">贷款申请信息</a></li>
                    <li class="menuItem"><a href="#reportThree">借款人信息填写</a></li>
                    <li class="menuItem"><a href="#reportFour">担保人信息</a></li>
                    <li class="menuItem"><a href="#reportFive">申请材料</a></li>
                </ul>
                <div class="menu_btn" style="margin-left: 850px;margin-top: 7px;">
                    <button class="btn btn-save">保存</button>
                </div>
            </div>
            <div class="row">
                <div class="wrapper wrapper-content animated fadeInUp">
                    <div class="ibox-content" id="reportOne">
                        <form method="get" class="form_list">
                            <div class="title">
                                申请表单填写
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        产品选择
                                    </label>
                                    <select name="" id="goodsType" name="queryType" class="select_control sign_right_one">
                                        <option value="" selected="selected">请选择</option>
                                        <option value="">类型1</option>
                                        <option value="">类型2</option>
                                    </select>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        供应商选择
                                    </label>
                                    <select name="" id="" class="select_control sign_right_one">
                                        <option value="">请选择</option>
                                        <option value="">及时雨</option>
                                    </select>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        信贷员
                                    </label>
                                    <input type="text" class="select_control sign_right_one" value="" >
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        电话
                                    </label>
                                    <input type="text" class="select_control sign_right_one" value="" >
                                </div>
                            </div>
                        </form>
                        <div class="title">
                            关联交易单
                        </div>
                        <div class="links_box clearfix">
                            <div class="links_content">
                                <div class="no_links" id="" data-toggle="modal" data-target="#myModalone">
                                    <p class="toggle_icon goods_one">
                                        <i class="icon iconfont icon_house icon_house_blue">&#xe62e;</i><span class="type_text">出售</span>
                                    </p>
                                    <p class="toggle_icon goods_two">
                                        <i class="icon iconfont icon_house icon_house_red">&#xe62e;</i><span class="type_text">购房</span>
                                    </p>
                                    <p class="click_link">点击添加关联案件</p>
                                </div>
                                <div class="no_links" id="" data-toggle="modal" data-target="#myModalone">
                                    <p class="toggle_icon goods_one">
                                        <i class="icon iconfont icon_house icon_house_blue">&#xe62e;</i><span class="tyep_text">出售</span>
                                    </p>
                                    <p class="toggle_icon goods_two">
                                        <i class="icon iconfont icon_house icon_house_red">&#xe62e;</i><span class="type_text">购房</span>
                                    </p>
                                    <p class="click_link">点击添加关联案件</p>
                                </div>
                            </div>
                            <div class="links_content">
                                <div class="links_info" id="" style="display:block;">
                                    <div class="title">
                                        <i class="icon iconfont icon_house icon_house_red">&#xe62e;</i>购房
                                    </div>
                                    <p class="info"><span>案件编号</span>ZY-AJ-201605-0958</p>
                                    <p class="info"><span>物业地址</span><em>上海杨浦区平路街道（内环）鞍山八村29号0608室
                                    </em></p>
                                    <p class="info"><span>经办人</span>ZY-AJ-201605-0958</p>
                                    <p class="info"><span>上家姓名</span>宋宋</p>
                                    <p class="info"><span>下家姓名</span>李设计</p>
                                    <button type="button" class="btn btn-grey links_cancel
                                    ">取消关联</button>
                                </div>
                                <div class="links_info" id="" style="display:block;">
                                    <div class="title">
                                        <i class="icon iconfont icon_house icon_house_red">&#xe62e;</i>购房
                                    </div>
                                    <p class="info"><span>案件编号</span>ZY-AJ-201605-0958</p>
                                    <p class="info"><span>物业地址</span><em>上海杨浦区平路街道（内环）鞍山八村29号0608室
                                    </em></p>
                                    <p class="info"><span>经办人</span>ZY-AJ-201605-0958</p>
                                    <p class="info"><span>上家姓名</span>宋宋</p>
                                    <p class="info"><span>下家姓名</span>李设计</p>
                                    <button type="button" class="btn btn-grey links_cancel
                                    ">取消关联</button>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="ibox-content" id="reportTwo">

                        <form method="get" class="form_list">
                            <div class="title">
                                借款信息
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        借款金额
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one" />
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        借款期限
                                    </label>
                                    <select name="" id="" class="select_control" style="width: 135px;">
                                        <option value="">请选择</option>
                                        <option value="">6个月</option>
                                        <option value="">12个月</option>
                                        <option value="">18个月</option>
                                        <option value="">24个月</option>
                                    </select>
                                </div>
                            </div>
                        </form>
                        <form method="get" class="form_list">
                            <div class="title">
                                出售房屋信息
                            </div>

                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        房屋实际价格
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one" />
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        房屋合同价
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one" />
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        房屋地址
                                    </label>
                                    <input type="text" class="select_control" style="width: 255px;" value=""  />
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        房屋产权人
                                    </label>
                                    <input type="text" class="select_control sign_right_one" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        已收首付款总额
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one" />
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>

                                <div class="form_content">
                                    <label class="control-label" style="margin-right:10px;">
                                        应收按揭贷款总额
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one" />
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>

                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label" style="margin-right:10px;">
                                        按揭贷款待还金额
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one" />
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small" style="margin-right:20px;">
                                        自筹赎楼款总额
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one" />
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        买方姓名
                                    </label>
                                    <input type="text" class="select_control sign_right_one" />
                                </div>
                            </div>
                            <div class="line">

                            </div>
                        </form>
                        <form method="get" class="form_list">
                            <div class="title">
                                购买房屋信息
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        房屋合同价
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one">
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        房屋实际成交价
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one">
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        首付款总额
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one">
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        房屋地址
                                    </label>
                                    <input type="text" class="select_control" value="" style="width: 255px;">
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="ibox-content" id="reportThree">
                        <form method="get" class="form_list">
                            <div class="title">
                                借款人个人信息
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        姓名
                                    </label>
                                    <input type="text" class="select_control sign_right_one" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        手机号码
                                    </label>
                                    <input type="text" class="select_control sign_right_one" value="" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        婚姻状况
                                    </label>
                                    <select name="" id="" class="select_control sign_right_one">
                                        <option value="">已婚</option>
                                        <option value="">未婚</option>
                                        <option value="">离异</option>
                                        <option value="">丧偶</option>
                                    </select>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        公积金账号
                                    </label>
                                    <input type="text" class="select_control sign_right_one" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        个人月收入
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one" />
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        工作单位名称
                                    </label>
                                    <input type="text" class="select_control" value="" style="width: 255px;" />
                                </div>
                            </div>
                        </form>
                        <form method="get" class="form_list">
                            <div class="title">
                                借款人已有房产（选填）
                            </div>
                            <div class="line">
                                 <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        共有人
                                    </label>
                                    <input type="text" class="select_control" style="width: 105px;">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        邮政编码
                                    </label>
                                    <input type="text" class="select_control" style="width: 105px;">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        房产地址
                                    </label>
                                    <input type="text" class="select_control" value="" style="width: 255px;">
                                </div>
                            </div>
                            <div class="line">
                                 <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        共有人
                                    </label>
                                    <input type="text" class="select_control" style="width: 105px;">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        邮政编码
                                    </label>
                                    <input type="text" class="select_control" style="width: 105px;">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        房产地址
                                    </label>
                                    <input type="text" class="select_control" value="" style="width: 255px;">
                                </div>
                                <a href="javascript:void(0)" class="btn btn-default" onclick="getDell(this)">删除</a>
                            </div>
                            <div class="line" id="addLine">
                                <a href="javascript:void(0)" style="margin-left:126px;" class="btn btn-success" onclick="getAtr(this)">添加</a>
                            </div>
                        </form>
                        <form method="get" class="form_list">
                            <div class="title">
                                借款人配偶信息
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        姓名
                                    </label>
                                    <input type="text" class="select_control sign_right_one" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        手机号码
                                    </label>
                                    <input type="text" class="select_control sign_right_one" value="" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small" >
                                        婚姻状况
                                    </label>
                                    <select name="" id="" class="select_control sign_right_one">
                                        <option value="">已婚</option>
                                        <option value="">未婚</option>
                                        <option value="">离异</option>
                                        <option value="">丧偶</option>
                                    </select>
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        公积金账号
                                    </label>
                                    <input type="text" class="select_control sign_right_one" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        个人月收入
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one" />
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        工作单位名称
                                    </label>
                                    <input type="text" class="select_control" value="" style="width: 255px;" />
                                </div>
                            </div>
                        </form>
                        <form method="get" class="form_list">
                            <div class="title">
                                联系人信息
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        联系人姓名
                                    </label>
                                    <input type="text" class="select_control sign_right_one" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        关系
                                    </label>
                                    <input type="text" class="select_control sign_right_one" />
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        手机号码
                                    </label>
                                    <input type="text" class="select_control sign_right_one" />
                                </div>
                            </div>
                        </form>

                    </div>
                    <div class="ibox-content" id="reportFour">
                        <form method="get" class="form_list">
                            <div class="title">
                                担保人
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        姓名
                                    </label>
                                    <input type="text" class="select_control sign_right_one">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        手机号码
                                    </label>
                                    <input type="text" class="select_control sign_right_one" value="">
                                </div>
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        公积金账号
                                    </label>
                                    <input type="text" class="select_control sign_right_one">
                                </div>
                            </div>
                            <div class="line">
                                <div class="form_content">
                                    <label class="control-label sign_left_small">
                                        个人月收入
                                    </label>
                                    <input type="text" placeholder="人民币" class="select_control sign_right_one">
                                    <span class="date_icon">
                                       万
                                    </span>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="ibox-content" id="reportFive">


                        <div class="title">
                            申请材料
                        </div>
                        <form class="form_list table-capital">
                            <div class="table-box">
                                <table class="table table-bordered customerinfo">
                                    <thead>
                                        <tr>
                                            <th>材料名称</th>
                                            <th>附件要求</th>
                                            <th>上传附件</th>
                                        </tr>
                                    </thead>
                                    <tbody id="addTr">
                                        <tr>
                                            <td>
                                                <p>上家身份证</p>
                                            </td>
                                            <td>
                                                正反面清晰
                                            </td>
                                            <td>
                                                <input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <p>借款人征信</p>
                                            </td>
                                            <td>
                                                要求半个月以内
                                            </td>
                                            <td>
                                                <input type="file" name="file" class="webuploader-element-invisible" multiple="multiple" accept="image/*">
                                            </td>

                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </form>
                        <div class="form-btn">
                            <button type="submit" class="btn btn-success">提交申请</button>
                            <button type="btn" class="btn btn-grey" data-toggle="modal" data-target="#myModal">取消</button>
                        </div>
                    </div>
                    <div class="ibox-content" style="height:250px;background:#f3f3f4;border-top:none;">
                    </div>
                </div>
            </div>
            <!-- main End -->
        <div class="modal inmodal" id="myModal" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content animated fadeIn">
                        <small>
                        <div class="modal-body" style="background:#fff;">
                            <p class="text-center" style="font-size: 20px;">点击删除，将丢失本次填写信息！选择保存按钮可保存本次填写信息！</p>
                        </div>
                        <div class="modal-footer" style="text-align:center;">
                            <button type="button" class="btn btn-save">确认保存</button>
                            <button type="button" class="btn btn-success">我要删除</button>
                            <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                        </div>
                    </small></div><small>
                </small></div><small>
            </small></div>
        </div>
    </div>


    <div class="modal inmodal" id="myModalone" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
        <div class="modal-dialog" style="width: 1070px;">
            <div class="modal-content animated fadeIn info_box">
                <form method="get" class="form_list">
                    <div class="modal_title">
                        合约关联
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label mr10">
                                    案件编码
                            </label>
                            <input class="teamcode input_type" value="" placeholder="请输入">
                        </div>
                        <div class="form_content">
                            <label class="control-label sign_left">
                                    物业地址
                            </label>
                            <input class="input_type" value="" placeholder="请输入" style="width:435px;">
                        </div>
                    </div>
                    <div class="line">
                        <div class="form_content">
                            <label class="control-label mr10">
                                    客户信息
                            </label>
                            <input class="teamcode input_type" value="" placeholder="请输入">
                        </div>
                        <div class="form_content space">
                            <div class="add_btn">
                                <button type="button" class="btn btn-success">
                                <i class="icon iconfont"></i>
                                    查询
                                </button>
                            </div>
                        </div>
                    </div>
                <button type="button" class="close close_blue" data-dismiss="modal"><i class="iconfont icon_rong">&#xe60a;
                    </i></button>
                    <div class="apply_table table_content">
                        <table class="table table_blue table-striped table-bordered table-hover customerinfo" id="editable">
                            <thead>
                                <tr>

                                    <th>
                                        <span>案件编码</span><a href="#"><i class="fa fa-sort-desc fa_down"></i></a>
                                    </th>
                                    <th>
                                        合约类型
                                    </th>
                                    <th>
                                        产证地址
                                    </th>
                                    <th>
                                        经办人
                                    </th>
                                    <th>
                                        上家
                                    </th>
                                    <th>
                                        下家
                                    </th>
                                    <th>
                                        交易关系
                                    </th>
                                    <th>
                                        操作
                                    </th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <p class="big">
                                            <a href="javascript:;">
                                                ZY-AJ-201605-0952
                                            </a>
                                        </p>
                                        <p>
                                            <i class="tag_sign">c</i>
                                            BKS-2-451341-2154
                                        </p>
                                    </td>
                                    <td>
                                        <i class="sign_blue">交易合约</i>
                                    </td>
                                    <td>
                                        <p class="big">
                                            上海杨浦区平路街道（内环）鞍山八村29号0608室
                                        </p>
                                        <p class="tooltip-demo">
                                            <i class="salesman-icon">
                                            </i>
                                            <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title=" 张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体">
                                                张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                            </a>
                                        </p>
                                    </td>
                                    <td>
                                        <p class="name">
                                            龙一思
                                        </p>
                                    </td>
                                    <td class="center">
                                        <p class="big">
                                            张春伟
                                        </p>
                                        <p class="big">
                                            张伟伟
                                        </p>
                                    </td>
                                    <td class="center">
                                        <p class="big">
                                            陈一财
                                        </p>
                                        <p class="big">
                                            陈一财
                                        </p>
                                    </td>
                                    <td>
                                        <select name="" id="" class="form-control" style="border: none;background-color: rgba(0, 0, 0, 0);">
                                            <option value="" checked="">出售</option>
                                            <option value="" checked="">购房</option>
                                        </select>
                                    </td>
                                    <td class="text-left">
                                        <button type="button" class="btn btn-success">
                                        关联
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <p class="big">
                                            <a href="javascript:;">
                                                ZY-AJ-201605-0952
                                            </a>
                                        </p>
                                        <p>
                                            <i class="tag_sign">c</i>
                                            BKS-2-451341-2154
                                        </p>
                                    </td>
                                    <td>
                                        <i class="sign_blue">交易合约</i>
                                    </td>
                                    <td>
                                        <p class="big">
                                            上海杨浦区平路街道（内环）鞍山八村29号0608室
                                        </p>
                                        <p class="tooltip-demo">
                                            <i class="salesman-icon">
                                            </i>
                                            <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title=" 张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体">
                                                张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                            </a>
                                        </p>
                                    </td>
                                    <td>
                                        <p class="name">
                                            龙一思
                                        </p>
                                    </td>
                                    <td class="center">
                                        <p class="big">
                                            张春伟
                                        </p>
                                        <p class="big">
                                            张伟伟
                                        </p>
                                    </td>
                                    <td class="center">
                                        <p class="big">
                                            陈一财
                                        </p>
                                        <p class="big">
                                            陈一财
                                        </p>
                                    </td>
                                    <td>
                                        <select name="" id="" class="form-control" style="border: none;background-color: rgba(0, 0, 0, 0);">
                                            <option value="" checked="">出售</option>
                                            <option value="" checked="">购房</option>
                                        </select>
                                    </td>
                                    <td class="text-left">
                                        <button type="button" class="btn btn-success">
                                        关联
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <p class="big">
                                            <a href="javascript:;">
                                                ZY-AJ-201605-0952
                                            </a>
                                        </p>
                                        <p>
                                            <i class="tag_sign">c</i>
                                            BKS-2-451341-2154
                                        </p>
                                    </td>
                                    <td>
                                        <i class="sign_blue">交易合约</i>
                                    </td>
                                    <td>
                                        <p class="big">
                                            上海杨浦区平路街道（内环）鞍山八村29号0608室
                                        </p>
                                        <p class="tooltip-demo">
                                            <i class="salesman-icon">
                                            </i>
                                            <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title=" 张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体">
                                                张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                            </a>
                                        </p>
                                    </td>
                                    <td>
                                        <p class="name">
                                            龙一思
                                        </p>
                                    </td>
                                    <td class="center">
                                        <p class="big">
                                            张春伟
                                        </p>
                                        <p class="big">
                                            张伟伟
                                        </p>
                                    </td>
                                    <td class="center">
                                        <p class="big">
                                            陈一财
                                        </p>
                                        <p class="big">
                                            陈一财
                                        </p>
                                    </td>
                                    <td>
                                        <select name="" id="" class="form-control" style="border: none;background-color: rgba(0, 0, 0, 0);">
                                            <option value="" checked="">出售</option>
                                            <option value="" checked="">购房</option>
                                        </select>
                                    </td>
                                    <td class="text-left">
                                        <button type="button" class="btn btn-success">
                                        关联
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <p class="big">
                                            <a href="javascript:;">
                                                ZY-AJ-201605-0952
                                            </a>
                                        </p>
                                        <p>
                                            <i class="tag_sign">c</i>
                                            BKS-2-451341-2154
                                        </p>
                                    </td>
                                    <td>
                                        <i class="sign_blue">交易合约</i>
                                    </td>
                                    <td>
                                        <p class="big">
                                            上海杨浦区平路街道（内环）鞍山八村29号0608室
                                        </p>
                                        <p class="tooltip-demo">
                                            <i class="salesman-icon">
                                            </i>
                                            <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title=" 张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体">
                                                张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                            </a>
                                        </p>
                                    </td>
                                    <td>
                                        <p class="name">
                                            龙一思
                                        </p>
                                    </td>
                                    <td class="center">
                                        <p class="big">
                                            张春伟
                                        </p>
                                        <p class="big">
                                            张伟伟
                                        </p>
                                    </td>
                                    <td class="center">
                                        <p class="big">
                                            陈一财
                                        </p>
                                        <p class="big">
                                            陈一财
                                        </p>
                                    </td>
                                    <td>
                                        <select name="" id="" class="form-control" style="border: none;background-color: rgba(0, 0, 0, 0);">
                                            <option value="" checked="">出售</option>
                                            <option value="" checked="">购房</option>
                                        </select>
                                    </td>
                                    <td class="text-left">
                                        <button type="button" class="btn btn-success">
                                        关联
                                        </button>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <p class="big">
                                            <a href="javascript:;">
                                                ZY-AJ-201605-0952
                                            </a>
                                        </p>
                                        <p>
                                            <i class="tag_sign">c</i>
                                            BKS-2-451341-2154
                                        </p>
                                    </td>
                                    <td>
                                        <i class="sign_blue">交易合约</i>
                                    </td>
                                    <td>
                                        <p class="big">
                                            上海杨浦区平路街道（内环）鞍山八村29号0608室
                                        </p>
                                        <p class="tooltip-demo">
                                            <i class="salesman-icon">
                                            </i>
                                            <a class="salesman-info" href="javascript:;" title="" data-toggle="tooltip" data-placement="top" data-original-title=" 张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体">
                                                张春伟/15026784858/ACCDGB.东方曼哈顿分行二组全体
                                            </a>
                                        </p>
                                    </td>
                                    <td>
                                        <p class="name">
                                            龙一思
                                        </p>
                                    </td>
                                    <td class="center">
                                        <p class="big">
                                            张春伟
                                        </p>
                                        <p class="big">
                                            张伟伟
                                        </p>
                                    </td>
                                    <td class="center">
                                        <p class="big">
                                            陈一财
                                        </p>
                                        <p class="big">
                                            陈一财
                                        </p>
                                    </td>
                                    <td>
                                        <select name="" id="" class="form-control" style="border: none;background-color: rgba(0, 0, 0, 0);">
                                            <option value="" checked="">出售</option>
                                            <option value="" checked="">购房</option>
                                        </select>
                                    </td>
                                    <td class="text-left">
                                        <button type="button" class="btn btn-success">
                                        关联
                                        </button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="text-center page_box">
                            <span id="currentTotalPage">
                                <strong>
                                    1/8
                                </strong>
                            </span>
                            <span class="ml15">
                                共
                                <strong id="totalP">
                                    144
                                </strong>
                                条
                            </span>
                            &nbsp;
                            <div id="pageBar" class="pagination text-center">
                                <ul class="pagination">
                                    <li class="first disabled">
                                        <a href="#"><i class="fa fa-step-backward"></i></a>
                                    </li>
                                    <li class="prev disabled">
                                        <a href="#"><i class="fa fa-chevron-left"></i></a>
                                    </li>
                                    <li class="page active">
                                        <a href="#">
                                            1
                                        </a>
                                    </li>
                                    <li class="page">
                                        <a href="#">
                                            2
                                        </a>
                                    </li>
                                    <li class="page">
                                        <a href="#">
                                            3
                                        </a>
                                    </li>
                                    <li class="page">
                                        <a href="#">
                                            4
                                        </a>
                                    </li>
                                    <li class="page">
                                        <a href="#">
                                            5
                                        </a>
                                    </li>
                                    <li class="page">
                                        <a href="#">
                                            6
                                        </a>
                                    </li>
                                    <li class="page">
                                        <a href="#">
                                            7
                                        </a>
                                    </li>
                                    <li class="page">
                                        <a href="#">
                                            8
                                        </a>
                                    </li>
                                    <li class="next">
                                        <a href="#"><i class="fa fa-chevron-right"></i></a>
                                    </li>
                                    <li class="last">
                                        <a href="#"><i class="fa fa-step-forward"></i></a>
                                    </li>
                                </ul>
                                <div class="pagergoto">
                                    <span class="go_text">
                                        跳转至
                                    </span>
                                    <span>
                                    <input type="text" class="go_input" value="">
                                    <input type="button" class="go_btn" value="GO"></span>
                                </div>

                            </div>
                        </div>
            </div>


            </form>
            </div>
     

    <!-- Mainly scripts -->
    <script src="${ctx}/static/js/jquery-2.1.1.js"></script>
    <script src="${ctx}/static/js/bootstrap.min.js"></script>
    <script src="${ctx}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${ctx}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${ctx}/static/js/inspinia.js"></script>
    <script src="${ctx}/static/js/plugins/pace/pace.min.js"></script>

    <!-- stickup plugin -->
    <script src="${ctx}/static/js/plugins/stickup/stickUp.js"></script>
    <script src="${ctx}/static/trans/js/eloan/report.js"></script>
    <script src="${ctx}/static/trans/js/eloan/submission.js"></script>

</body>

</html>