v1.1.23.2 20160922
    1.我的资金监管合约列表
    2. 产调来源清单及产调来源列表
    3. 案件总览导出增加是否查限购字段
    4. 产调导出列表查询问题（不包含起始时间）
v1.1.23.1 20160912
    1.修复资金监管合约申请的BUG

v1.1.23 20160909
    1、YCFT-885 E+金融产品填写（新）——助理填写金融产品选择归属人，主管审批看不到归属人，主管审批界面添加归属人信息
    2、YCFT-886 e+金融产品报表——导出EXCEL添加案件归属人信息
    3、YCFT-890 SQL——快速查询,增加只返回总数的接口
    4、YCFT-428 产调来源清单
    5、YCFT-623 个人工作台——无人跟进合约预警
    6、YCFT-624 个人工作台——无主任务预警
    7、YCFT-859 E+金融产品（贷款类）——条件：消费贷（抵押类）30004014、加上搜易贷、加上申请期数小于12时，需填写手续费和情况说明；且手续费大于2%，小于1%则必须填写情况说明 
    8、YCFT-882 E+金融产品填写——关联案件界面优化，左右两侧留间距
    9、YCFT-883 无主任务——添加查询条件：贵宾服务部（项春燕）
    10、YCFT-884 贷款信息列表——添加贷款类型查询条件及列表显示；自办贷款申请就必须标志流失
    11、YCFT-887 个人工作台首页功能提升
    12、YCFT-889 用户在登陆后，判断岗位是否有指定首页。如果有指定首页则跳转到指定页面，如果没有则跳转到工作台页面
    13、YCFT-900、YCFT-901、YCFT-902 E+金融申请（贷款类）——助理界面列表显示按照贵宾服务部权限显示

v1.1.22 20160825
    1、流失报表、贷款信息列表、
       YCFT-476   临时银行报表
       YCFT-477   所有临时银行申请
       YCFT-479   本区临时银行申请
       YCFT-847   临时银行报表——字段
    2、绩效奖金满意度
    3、可计件奖金池案件导出查询
    4、YCFT-615   评估费转化率计算正确
       YCFT-616   评估费打折系数
    5、YCFT-851   我的案件——案件总览、待分配案件、待办任务、无主任务、无主案件、案件追踪、本组待办列表、中台顾问待办列表、临时银行处理界面优化
    6、案件视图——添加“案件类型”、“案件状态”显示，已爆单或在途案件等等
    7、评估费核实——编辑过程中，可以删除评估费，但是现在删除后保存还是有显示评估费，未能正常删除
    8、YCFT-849   个人工作台——龙虎榜数据包含新老E+金融产品数据
    9、YCJYPT-623   新E+金融产品删除功能
    10、主管申请金融产品，需根据当前登录的岗位查询
    11、产品部E+贷款报表导出EXCEL添加：合作机构、所属组别、客户姓名、区董等字段，时间字段添加时分秒
    12、个人工作台——E+转换率、评估单转化率、评估费折扣加上算法信息
    13、产调4个界面样式调整
    14、工作台样式（E+贷款圆图颜色调整，贷款详情数据对齐）
    15、E+金融产品，日期、产证地址查询功能修改
    16、MORTGAGE表添加LOAN_ANGENT、LOAN_AGENT_TEAM两个字段
    17、过户案件筛选——导出EXCEL“货代专员”改为“贷款类型”，“是否到场”改为“评估费金额”（项春燕）

v1.1.21 20160812：
    1、e+金融产品流程总体改造、页面优化、多次放款新功能 (除税费卡外)
    2、交易过户主流程添加贷款需求选择环节 
    3、交易过户主流程增加过户驳回环节, 过户审批驳回相关经办人进入过户驳回修改界面,对过户信息修改后重新提交过户审批.
    4、浦东中台分单功能. 浦东合作顾问只选中台，由中台统一分单
    5、个人工作台报表优化
    6、待办任务界面优化：添加流程所属信息. 
    7、添加贷款流失预警功能. 
    8、临时银行审批流程优化, 临时银行由交易顾问选择, 主管审批.
    9、贷款流失添加流失原因、流失确认函编号、及上传附件
    10、过户数据导出添加创建时间、实际创建时间、提交时间
    BUG修复：
    1、修复案件总览导出报错
    2、修复红绿灯任务报表黄色导出按钮，导出数据是反的

v1.1.20 20160804
    1、临时银行优化；
    2、运营部7个报表优化：过户案件筛选、产调导出汇总筛选、产调详情筛选、红绿灯任务报表、红绿灯任务详情、面签金融产品、签约案件；
    3、结案归档、过户审批、案件视图、自办贷款主管审批、自办贷款总监审批界面图片查看方式变更，并且图片可以进行旋转挪动；
    4、解决附件上传为空白，必须刷新界面重新上传问题；
    5、解决在家可以上传附件的问题；
v1.1.19 20160725
    1. 魏燕7个报表：过户案件筛选、产调导出汇总筛选、产调详情筛选、红绿灯任务报表、红绿灯任务详情、面签金融产品、签约案件；
    2. 临时银行主管、高级主管、总监三级审批功能；
    3. 前台组个人工作台, 半年案件完成进度图
    4. 新的CTM派单接口；
    5. 贷款流程改造；
    6. 手机产调已完成页面显示执行人；
    7. 手机微信端新增我的产调页面. 业务员可以从手机端查询发起过的产调, 查看完成状态及结果. 
    8. 金融产品填写页面的案件归属人助理可选择前后台组人员；
    9. 签约环节对上下家姓名进行约束, 不能输入先生、小姐、爸爸、妈妈等字段；
    10. 备注字段改为案件跟进内容（非必填）；
    11. 过户审批加贷款流失类型（收单或流失）。
    12. 同一个案子可以填写多个税费卡
    13. 页面UI优化
v1.1.18 20160716
    1、过户审批添加CTM附件信息
    2、案件视图页面样式调整
    3、流程引擎优化
    4、手机申请产调时不允许选择战区和区董，如果申请人没有战区和区董，可以选择战区
    5、案件流程每个环节添加备注，案件详情也添加备注列表
    6、助理处理产调时选择归属人，可选择所有人
    7.手机微信端新增我的产调界面，查看自己申请的产调

v1.1.16 20160707
    1、个人工作台原过户数变更为贷款申请数，可查询所有已完成商贷（委托中原）环节的案件；（新功能）
    2、已受理产调——处理页面优化，添加战区、区董、执行人；（功能改进）
    3、结案归档——附件信息页面查看所有上传附件；（BUG修复）
    4、页面UI调整及BUG修复。（BUG修复）
v1.1.15 20160706
    1、YCJYPT-172 金融产品信息填写——案件绑定后，也需绑定案件上的客户姓名及客户电话（可修改） 
    2、中台分单功能（权限分配给主管）,  主管可以通过本组待办列表进行中台任务的分派
    3、手机产调申请添加战区、区董
    4、过户审批添加推荐函编号显示
    5、过户审批——审批未通过13种原因
    6、个人工作台优化 
v1.1.14 20160630
    1、商贷环节备选银行限制不可选择临时银行操作
    2、修复状态超期或变更列表界面异常
    3、修复区域报表界面异常
    4、结案归档不可修改的地方置灰
    5、修复临时银行处理界面查询未处理、已处理问题
    6、待处理、已受理、已完成产调：界面优化
v1.1.13 20160629
    1. 临时银行审批——誉萃管理员可以对需要添加临时银行的案件进行临时银行处理. 临时银行处理后,通过站内信通知主办. 
    2. 个人工作平台——E+金融产品——申请、面签、放款、转化率BUG修改.
    3. 经理无法收到组员红灯提醒BUG.
v1.1.12 20160628
    1、签约——输入的姓名后有空格，在保存或提交时，提示：上家姓名只能输入中文或英文，已改为自动去除空格
    2、签约——限制成交价、合同价不能为0或空
    3、流失案件查询功能：我的案件-->流失案件
    4、主管账号查询我的基础绩效奖金：佣金管理-->我的基础绩效奖
    5、主管账号查询计件奖金报表：佣金管理-->计件奖金报表
    6、商贷办理环节——“贷款签约”添加“是否临时银行”勾选项，勾选后，必须填写临时银行原因. 可不选择贷款银行、贷款支行，可以不输入推荐函编号，可以不上传推荐函附件. 
    7、商贷办理环节——“贷款签约”添加必填项“推荐函编号”
    8、待处理、已受理、已完成产调——添加“所属分行”、“区董”列表显示
    9、案件总览——导出EXCEL添加“推荐函编号”、“所属大区”字段显示
    10、已完成产调——增加修改功能. 
v1.1.11 20160517
    1. 个人基础计件奖金
    2. 案件总览导出案件重复问题
v1.1.10 20160615
    1. 后台跨区合作功能（在首次跟进、贷款需求选择公积金两个选择合作对象的下拉列表添加“跨区合作”，可以选择别区域的后台组）
    2. 新流程，主办人可以在案件视图“贷款需求选择”，变更贷款流程，无需流程重启（仅限于新流程并且是案件的主办人才可以变更，之前的案件还是要流程重启）
    3. 案件总览导出显示是否贷款, 贷款类型(增加一次性付款)
    4. YCJYPT-461  变更交易计划——变更时提示：交易计划不能为空 
    5. YCJYPT-462  产调转组——不应该显示前台后台组，只显示贵宾服务部即可 
    6. YCJYPT-463  无效案件——提交时，提示必须选择合作顾问，应该不需要选择合作顾问，直接提交
    7. YCJYPT-465  变更合作对象——变更后未成功
    8. YCJYPT-464  跨区合作——没有区域、组别、人员选择框跳出 
    9. YCJYPT-439 朱春已经离职，过户时无法提交，提示：操作异常，null
    10. YCJYPT-440 案件总览, 选择过户审批日期, 过户日期后导出数据与查询不一致.
    11. YCJYPT-161 分单提醒——是否确认分配给该业务员，后面加上业务员联系电话

v1.1.9  20160607
    1、YCJYPT-161   分单提醒——是否确认分配给该业务员，后面加上业务员联系电话
    2、YCJYPT-176   手机提交产调——如果未选择区域时，系统提示：该区域不能进行产调录入！（需改提示信息）
    3、YCJYPT-421   案件视图——附件信息，直接单击图片，打开原图链接报错
    4、YCJYPT-420   流程重启申请、贷款服务项变更审批——添加物业信息、买卖双方、经纪人信息、经办人信息
v1.1.8  20160606
    1、YCJYPT-409  归档确认和结案审核——提交后，未能刷新待办任务界面（刘烨、傅峻青） 
    2、YCJYPT-357  手机产调——增加时间筛选的功能
    3、YCJYPT-378  首次跟进——产调地址如果有特殊字符不允许提交保存操作 
    4、YCJYPT-246  评估费明细——主管、助理可以按照月份查询他们所负责的成员业绩，并可导出EXCEL
v1.1.7  20160603
    1、YCJYPT-389  手机产调处理界面——增加“产调项目”查看
    2、YCJYPT-410、YCJYPT-411、YCJYPT-412、YCJYPT-413  在每个流程环节添加物业信息、买卖双方信息、经纪人信息、经办人信息
v1.1.6  20160602
    fix:  案件总览根据服务项查询BUG
    feat: 金融产品, 评估费, 案件工作量报表增加排序功能
    feat: 待办任务列表增加任务创建时间,并可以根据创建时间进行排序
    fix:  案件变更责任人的BUG
    fix:  考勤管理查询不出来数据BUG
    fix:  从工作台点击E+申请金额无法打开金融产品报表
    fix:  结案主管审批退回时,流程图消失问题
v1.1.5  20160601
    feat: YCJYPT-394  金融产品管理——导出数据只有税费卡，因为合作机构默认为居易贷，现在默认全部，可导出全部数据
    feat: YCJYPT-390  案件工作量详情——添加根据“日期”、“案件编号”排序功能 
    feat: 个人工作台“交易顾问工作数据”点击相关数字链接到报表明细 
    task: 临时银行设置
v1.1.4  20150531
    YCJYPT-364 案件工作量报表, 各级经理可以通过统计报表菜单进入,根据组织, 类型, 时间范围查询 接单, 签约, 过户, 结案等数量信息.
    YCJYPT-373 金融产品报表, 各级经理可以通过申请时间, 面签时间,放款时间, 经办人组织, 经办人, 案件编号 产证地址等查询具体的金融产品信息. 
    YCJYPT-374 可通过评估费报表,根据评估费确认时间, 人员, 组织 , 产证地址, 案件编号 查询评估费明细
    YCJYPT-320 浦东贵宾服务部组别负责区域划分, 6月1日起王凯韵直带区域属于B组，其他王凯韵管辖区域属于B组
    YCJYPT-387 产调转组无法选择相应的贵宾服务部
v1.1.3  20160530
    1. 案件总览页面,默认不查询结案案件.  需要查询结案案件时, 指定案件的类型为结案案件即可.
    2. 产调转组功能, 可以由秘书将产调转给其他贵宾服务部进行办理. 
    3, PC端产调处理页面调整, 统一由处理按钮为入口进行产调是否有效, 上传附件等操作. 
    4. 其他BUG修改. 
v1.1.2  20160526
    feat: 待办任务办理过程中点击“保存”或“提交”按钮,自动刷新主页面（保留原查询条件）并自动关闭待办页面
    feat: “填写交易计划”流程优化：
        1）在“贷款需求选择”界面添加“预计放款时间”
	2）签约后先操作“填写交易计划”，则不显示“预计放款时间”
	3）签约后先操作“贷款需求选择”，再“填写交易计划”则需显示预计放款时间
    fix:  解决过户环节上传附件，图片其中一张异常显示是空白，提交后提示：还有附件未上传成功，请稍后重试，但是该案件已经提交，并且过户审批环节，附件没有显示
    fix: 交易过户（除签约外）这个合作顾问未加载完成时，可提交到下一个环节 
    fix:  解决提交后，显示本案件未完成待办列表仍显示已提交待办问题。
v1.1.1  20160525
    fix: 解决金融产品管理界面导出EXCEL及查询问题
    fix: 待办任务办理过程中点击“保存”按钮,自动刷新主页面并自动关闭待办页面
    fix: 解决已完成产调界面根据完成时间4月14号到4月14号的导出EXCEL问题
    fix: 解决月度KPI、满意度KPI，列表显示不全，无法调节长度
v1.1.0  20160524
    feat: 商贷环节放开询价必须确认的要求. 可以直接进入下一步. 
    fix:  待办点击后,自动刷新主页面. 如果办理完成,自动关闭待办页面.
    impr: 产调数据模型优化更新. 
v1.0.13 20160523
    fix: 案件列表查询权限限制问题.
    fix: QuickQueryUOJByOrgCodeCustomDictServiceImpl NPE异常处理
    fix: 贷款流失BUG修改, 贷款流失原因记录到贷款表上. 不再记录到审批记录表.
v1.0.12 20160520
    feat:  案件总览列表页面UI调整.
v1.0.11 20160519
    feat: 待办任务列表UI调整.  展示更清楚, 产证地址完全显示. 处理按钮更明显, 可以通过点击任务名进入任务办理. 所有字段都可以直接复制. 
    feat: 案件追踪列表增加根据CTM编号进行搜索
    feat: 手机端产调页面UI调整. 
    fix:  案件总览列表根据经办人搜索功能修复. 
v1.0.10 20160518
    feat:  手机端支持查询待处理, 已受理, 已完成状态的产调. 待处理的产调可以在手机端点击受理转成已受理状态. 已受理产调可以在手机端上传产调图片完成产调处理. 已完成产调可以在手机端查看产调处理结果.  
    feat:  PC端,已受理列表支持导出. 
    other: 手机端与PC端产调处理页面的UI调整持续进行中. 
v1.0.9 20160516
    feat: 待办任务根据“任务”进行查询
    fix:  是否自办显示问题：视图、过户、结案归档
    fix:  首次跟进——没有合作对象，则不显示选择列表，可直接下一步操作
    fix:  结案归档显示承办银行、支行名称显示不正确(无贷款, 公积金,自办贷款)
    fix:  根据经纪人查询问题.
          API调用日志增加到一年
v1.0.8 20160513
    feat: 金融产品——取消产品服务项标记，填写金融产品后并勾选案件服务项 
          填写金融产品时,不需要主办先行勾选案件的服务项. 
    feat: 案件总览、代办任务列表查询条件优化
          a. 查询条件“物业地址”置顶 
          b. 查询条件中的日期从XXX到XXX, 为包含关系,
          方便顾问根据物业地址查询.
    feat: 案件总览、待办任务列表UI改进
          a. 点击案件编号打开案件详情视图
          b. 可以选择列表上的任意字符进行复制,方便顾问复制案件地址等. 
          c. 列表字段增加CTM编号
v1.0.7 20160512
    feat: 贷款流失页面增加主贷人及主贷人单位信息
    feat: 已完成产调页面增加查看产调信息功能
    feat. 红绿灯列表页面可以跳转至案件详情视图及其他功能
    fix:  金融产品新建的时候也需要进行确认
    feat: 过户后,不可以重启流程
    fix:  主管进行分单后,同时修改主办及主办的所有组织信息
v1.0.6 20160427
    feat: 案件追踪
    feat: 隐藏成交价
    feat: 计件奖金
v1.0.5 20160407
    feat:  YCJYPT-102	过户审批页面增加案件地址信息
    feat:  YCJYPT-101	前端AB级银行显示为入围银行
    feat:  YCJYPT-100	供应商表增加Tag字段, 前端页面可以根据Tag进行筛选
    feat:  YCJYPT-98	商贷环节保存提示
    feat:  YCJYPT-97	案件信息修改权限判断从创建人改为负责人
    feat:  YCJYPT-104	金融产品页面列表增加所属贵宾服务中心列
    feat:  YCJYPT-95	产品研发部人员按权限管理金融产品
    fix:   YCJYPT-105	金融产品导出,时间列数据不出来
    fix:   YCJYPT-103	生产环境过户待办进入空指针异常
v1.0.4 20160401
    fix 金融产品无法导出, QueryID not found
