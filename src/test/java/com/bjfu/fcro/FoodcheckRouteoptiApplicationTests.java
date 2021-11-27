package com.bjfu.fcro;

import com.bjfu.fcro.algorithm.DistInDifTime;
import com.bjfu.fcro.dao.*;
import com.bjfu.fcro.entity.SysSamplingInspectorInformation;
import com.bjfu.fcro.entity.temporary.Temp_SamplePlanInfoTable;
import com.bjfu.fcro.service.*;
import com.bjfu.fcro.service.serviceimpl.SysSamplingLibraryServiceimpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@SpringBootTest
class FoodcheckRouteoptiApplicationTests {

	@Autowired
	private  SysRoleService sysRoleService;

	@Autowired
	private SamplingAccountDao samplingAccountDao;

	@Autowired
	private SysSamplingFoodListService sysSamplingFoodListService;

	@Autowired
	private SysSpendBetweenInPointsService sysSpendBetweenInPointsService;

	@Autowired
	private SysSpendBetweenInPointsFlowService sysSpendBetweenInPointsFlowService;
	@Autowired
	private SpendBetweenInPointsDao spendBetweenInPointsDao;
	@Autowired
	private SpendBetweenInPointsFlowDao spendBetweenInPointsFlowDao;
	@Autowired
	private SysSamplingLibraryService sysSamplingLibraryService;
	@Autowired
	private SysSamplingPlanService sysSamplingPlanService;
	@Autowired
	@Test
	void contextLoads() {

//		spendBetweenInPointsFlowDao.insertNewData(2,559,"1",1,"2",1
//		,"1","1","2","2","1","1"
//		,"1","1","1");
//		sysSamplingLibraryService.dosome();
		List<Temp_SamplePlanInfoTable> samplingPoints = new ArrayList<>();
		Temp_SamplePlanInfoTable temp_samplePlanInfoTable1 = new Temp_SamplePlanInfoTable(4186,"1","未完成","1",12.0,21.0,new ArrayList<>());
		Temp_SamplePlanInfoTable temp_samplePlanInfoTable2 = new Temp_SamplePlanInfoTable(4187,"1","未完成","1",12.0,21.0,new ArrayList<>());
		Temp_SamplePlanInfoTable temp_samplePlanInfoTable3 = new Temp_SamplePlanInfoTable(4188,"1","未完成","1",12.0,21.0,new ArrayList<>());
		samplingPoints.add(temp_samplePlanInfoTable1);
		samplingPoints.add(temp_samplePlanInfoTable2);
		samplingPoints.add(temp_samplePlanInfoTable3);
		List<DistInDifTime> Dlist = sysSamplingPlanService.getDlist(samplingPoints,spendBetweenInPointsDao.selectAll());
		for (int i = 0; i < Dlist.size(); i++) {
			DistInDifTime distInDifTime = Dlist.get(i);
			double dists[][] = distInDifTime.getDists();
			double times[][] = distInDifTime.getTimes();
			double speeds[][] = distInDifTime.getSpeeds();
			System.out.println("dists = ");
			for (int j = 0; j < dists.length; j++) {
				System.out.println(Arrays.toString(dists[j]));
			}
			System.out.println("times = ");
			for (int j = 0; j < times.length; j++) {
				System.out.println(Arrays.toString(times[j]));
			}
			System.out.println("speeds = ");
			for (int j = 0; j < speeds.length; j++) {
				System.out.println(Arrays.toString(speeds[j]));
			}
		}
	}



}
