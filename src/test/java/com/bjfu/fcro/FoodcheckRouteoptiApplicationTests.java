package com.bjfu.fcro;

import com.bjfu.fcro.dao.SamplingAccountDao;
import com.bjfu.fcro.dao.SamplingInspectorInformationDao;
import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.service.SysRoleService;
import com.bjfu.fcro.service.SysSamplingFoodListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class FoodcheckRouteoptiApplicationTests {

	@Autowired
	private  SysRoleService sysRoleService;

	@Autowired
	private SamplingAccountDao samplingAccountDao;

	@Autowired
	private SysSamplingFoodListService sysSamplingFoodListService;
	@Test
	void contextLoads() {

//		Map<String, List<String>> map = new HashMap<String, List<String>>();
//
//		List<String> list = new ArrayList<>();
//		for(int i=0;i<10;i++){
//			list.add("1");
//		}
//		List<String> list2 = new ArrayList<>();
//		for(int i=0;i<10;i++){
//			list2.add("2");
//		}
//		map.put("1",list);
//		map.put("2",list2);
//		System.out.println(map.values());
//		System.out.println(new ArrayList<List<String>>(map.values()));
//		char s[] = {'a','b'};
//		System.out.println(s.toString());
//		System.out.println(new String(s));

	}

}
