package com.bjfu.fcro;

import com.bjfu.fcro.dao.SamplingAccountDao;
import com.bjfu.fcro.dao.SamplingInspectorInformationDao;
import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class FoodcheckRouteoptiApplicationTests {

	@Autowired
	private  SysRoleService sysRoleService;

	@Autowired
	private SamplingAccountDao samplingAccountDao;
	@Test
	void contextLoads() {

		System.out.println("======"+samplingAccountDao.selectAll().getCreate_time());
	}

}
