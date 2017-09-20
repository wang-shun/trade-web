package com.centaline.scheduler.sysconfig;

import com.aist.scheduler.execution.remote.Job;
import com.aist.scheduler.execution.remote.vo.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 每月1号 0:0:0 触发
 * 进行案件编号重置
 *
 *
 *
 * @author yinchao
 * @date 2017/9/20
 */
@Component
public class SequencesResetJob implements Job{
	private Logger logger = LoggerFactory.getLogger(SequencesResetJob.class);
	@Autowired
	@Qualifier("sctransJdbcTemplate")
	private JdbcTemplate sctransTemplate;
	
	@Override
	public void execute(JobExecutionContext context) {
		logger.info("reset sequences Job  start..............");
		try {
			logger.info(LocalDateTime.now().toString()+"old sequences used in :"+getSequence());
			//重置案件编号
			sctransTemplate.update("alter sequence [sctrans].[seq_case_code] restart with 1");
			logger.info(LocalDateTime.now().toString()+"new sequences used in :"+getSequence());
		}catch (Exception e){
			e.printStackTrace();
		}
		logger.info("reset sequences Job  end..............");
	}

	private String getSequence(){
		return sctransTemplate.queryForObject("select next value for [sctrans].[seq_case_code]",new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				int next = rs.getInt(1);
				return Integer.toString(next - 1);
			}
		});
	}

}
