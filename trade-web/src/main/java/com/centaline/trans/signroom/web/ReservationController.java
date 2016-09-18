package com.centaline.trans.signroom.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aist.uam.auth.remote.UamSessionService;
import com.aist.uam.auth.remote.vo.SessionUser;
import com.centaline.trans.signroom.entity.Reservation;
import com.centaline.trans.signroom.service.ReservationService;
import com.centaline.trans.signroom.vo.ReservationVo;

/**
 * 预约取号信息controller
 * 
 * @author yinjf2
 *
 */
@Controller
@RequestMapping(value = "/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private UamSessionService uamSessionService;

	@RequestMapping(value = "save")
	@ResponseBody
	public String save(ReservationVo reservationVo) {
		SessionUser currentUser = uamSessionService.getSessionUser();

		Reservation reservation = new Reservation();
		reservation.setResNo("20160918001"); // 预约编号是根据某一规则自动生成，现在是为了测试先写死
		reservation.setResType("0");
		reservation.setResPersonId(reservationVo.getResPersonId());
		reservation.setResPersonOrgId("预约人组织id"); // 预约人组织id,现在为了测试先写死,到时候根据预约人id查询出对应的组织信息
		reservation.setResOrgId("归属组织(贵宾服务部)id"); // 归属组织(贵宾服务部)id,现在为了测试先写死,到时候根据resId(预约单id)查询出对应的归属组织信息
		reservation.setResStatus("0");
		reservation.setScheduleId(reservationVo.getScheduleId());
		reservation.setCaseCode(reservationVo.getCaseCode());
		reservation.setPropertyAddress(reservationVo.getPropertyAddress());
		reservation.setSigningCenter("签约中心"); // 签约中心(交易中心)，现在为了测试先写死,到时候根据resId(预约单id)查询出对应的归属组织信息
		reservation.setNumberOfParticipants(reservationVo
				.getNumberOfParticipants());
		reservation.setTransactItemCode(reservationVo.getTransactItemCode());
		reservation
				.setSpecialRequirement(reservationVo.getSpecialRequirement());

		reservation.setCreateTime(new Date());
		reservation.setCreateBy(currentUser.getId());
		reservation.setUpdateTime(new Date());
		reservation.setUpdateBy(currentUser.getId());

		int count = 0;
		try {
			count = reservationService.saveReservation(reservation);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count > 0 ? "true" : "false";
	}
}
