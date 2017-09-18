package com.centaline.scheduler.sync.tj.service;

import com.centaline.scheduler.sync.bean.SyncEmpOrgPos;
import com.centaline.scheduler.sync.bean.SyncEmployee;
import com.centaline.scheduler.sync.bean.SyncOrgUnit;
import com.centaline.scheduler.sync.bean.SyncPosition;
import com.centaline.scheduler.sync.enums.CityType;
import com.centaline.scheduler.sync.service.SyncHrService;
import com.centaline.scheduler.sync.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service("tJSyncService")
public class TJSyncService extends SyncHrService {
	@Autowired
	@Qualifier("TJHrTemplate")
	JdbcTemplate template;
	//部门查询的sql
	private String orgQuerySql;
	//员工查询sql
	private String employeeQuerySql;
	//岗位查询sql
	private String positionQuerySql;
	//员工 部门 岗位 查询sql
	private String empOrgPosQuerySql;
	private static CityType city = CityType.TIANJIN;
	private Logger logger = LoggerFactory.getLogger(TJSyncService.class);
	
	@Override
	protected List<SyncOrgUnit> getSyncOrgUnit() {
		if(StringUtils.isBlank(orgQuerySql)){
			ClassLoader classLoader = getClass().getClassLoader();
			URL url = classLoader.getResource("syncQuery/TJ/org.sql");
			logger.debug("org sql path:"+url.getFile());
			orgQuerySql = FileUtil.getFileInfo(url.getFile());
			logger.debug(orgQuerySql);
		}
		if(StringUtils.isBlank(orgQuerySql)) return null;
		return template.query(orgQuerySql, new OrgUnitRowMapper());
	}

	@Override
	protected List<SyncEmployee> getSyncEmployee() {
		if(StringUtils.isBlank(employeeQuerySql)){
			ClassLoader classLoader = getClass().getClassLoader();
			URL url = classLoader.getResource("syncQuery/TJ/employee.sql");
			logger.debug("employee sql path:"+url.getFile());
			employeeQuerySql = FileUtil.getFileInfo(url.getFile());
			logger.debug(employeeQuerySql);
		}
		if(StringUtils.isBlank(employeeQuerySql)) return null;
		return template.query(employeeQuerySql, new EmployeeRowMapper());
	}

	@Override
	protected List<SyncPosition> getSyncPosition() {
		if(StringUtils.isBlank(positionQuerySql)){
			ClassLoader classLoader = getClass().getClassLoader();
			URL url = classLoader.getResource("syncQuery/TJ/position.sql");
			logger.debug("position sql path:"+url.getFile());
			positionQuerySql = FileUtil.getFileInfo(url.getFile());
			logger.debug(positionQuerySql);
		}
		if(StringUtils.isBlank(positionQuerySql)) return null;
		return template.query(positionQuerySql, new PositionRowMapper());
	}

	@Override
	protected List<SyncEmpOrgPos> getSyncEmpOrgPos() {
		if(StringUtils.isBlank(empOrgPosQuerySql)){
			ClassLoader classLoader = getClass().getClassLoader();
			URL url = classLoader.getResource("syncQuery/TJ/emp_org_pos.sql");
			logger.debug("emp_org_pos sql path:"+url.getFile());
			empOrgPosQuerySql = FileUtil.getFileInfo(url.getFile());
			logger.debug(empOrgPosQuerySql);
		}
		if(StringUtils.isBlank(empOrgPosQuerySql)) return null;
		return template.query(empOrgPosQuerySql, new EmpOrgPosRowMapper());
	}
	private class OrgUnitRowMapper implements RowMapper<SyncOrgUnit> {
		public SyncOrgUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
			SyncOrgUnit unit = new SyncOrgUnit(city);
			int index = 1;
			unit.setId(rs.getString(index++));
			unit.setName(rs.getString(index++));
			unit.setCode(rs.getString(index++));
			unit.setParentId(rs.getString(index++));
			unit.setType(rs.getString(index++));
			unit.setCreateTime(rs.getDate(index++));
			unit.setEndTime(rs.getDate(index++));
			unit.setOrderCode(rs.getString(index++));
			unit.setFullName(rs.getString(index++));
			unit.setTel(rs.getString(index++));
			unit.setCcaiDepId(rs.getString(index++));
			unit.setHroc(rs.getString(index));
			return unit;
		}
	}
	
	private class EmployeeRowMapper implements RowMapper<SyncEmployee>{
		public SyncEmployee mapRow(ResultSet rs, int rowNum) throws SQLException {
			SyncEmployee e = new SyncEmployee(city);
			int index = 1;
			e.setId(rs.getString(index++));
			e.setName(rs.getString(index++));
			e.setSex(rs.getInt(index++));
			e.setBirthday(rs.getDate(index++));
			e.setEmail(rs.getString(index++));
			e.setAccount(rs.getString(index++));
			e.setCode(rs.getString(index++));
			e.setMobile(rs.getString(index++));
			e.setDeptId(rs.getString(index++));
			e.setDeptCode(rs.getString(index++));
			e.setDeptName(rs.getString(index++));
			e.setPositionId(rs.getString(index++));
			e.setPositionName(rs.getString(index));
			return e;
		}
	}
	
	private class PositionRowMapper implements RowMapper<SyncPosition>{
		public SyncPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
			SyncPosition p = new SyncPosition(city);
			int index = 1;
			p.setDeptId(rs.getString(index++));
			p.setDeptCode(rs.getString(index++));
			p.setDeptName(rs.getString(index++));
			p.setId(rs.getString(index++));
			p.setName(rs.getString(index));
			return p;
		}
	}
	
	private class EmpOrgPosRowMapper implements RowMapper<SyncEmpOrgPos>{
		public SyncEmpOrgPos mapRow(ResultSet rs, int rowNum) throws SQLException {
			SyncEmpOrgPos eop = new SyncEmpOrgPos(city);
			int index = 1;
			eop.setEmpId(rs.getString(index++));
			eop.setEmpName(rs.getString(index++));
			eop.setDeptId(rs.getString(index++));
			eop.setDeptName(rs.getString(index++));
			eop.setPositionId(rs.getString(index++));
			eop.setPositionName(rs.getString(index++));
			eop.setPrimary(rs.getInt(index++));
			eop.setLeader(rs.getInt(index));
			return eop;
		}
	}
}
