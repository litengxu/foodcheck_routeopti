package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.service.SysSamplingPlanService;
import org.springframework.stereotype.Repository;

@Repository
public class SysSamplingPlanServiceimpl implements SysSamplingPlanService {

    @Override
    public Object generateplan(String[] selectedsamplingaccountid, String[] typeoffoodselectedid, int numbers,String coordinate[]) {
        /**
         * selectedsamplingaccountid 抽检账号的id
         * typeoffoodselectedid 抽检食品类型的id
         * numbers 抽检数量
         * coordinate 出发点经纬度 index= 0为lng
         *selectedsamplingaccountid一定小于等于抽检数量
         *
         * 1. 找到抽检库中含有当前所有抽检食品类型的抽检点，存入list
         * 2. 如果list的大小大于numbers则随机选出numbers个，否则不随机，将结果重新存入list
         * 3. 将结果分为selectedsamplingaccountid.length份，每一份交给一个账号抽检（使用分组算法进行分组，保证分组的总的时间花费最少）
         * 4. 获得每一份中各个点之间的交通成本，并存入矩阵
         * 5. 使用算法排序各个点之间的顺序，linitial_position排在第一个和最后一个
         * 6. 将抽检计划存入数据库，修改抽检账号状态为不参与抽检，待抽检完成后改回
         * 7. 将抽检计划发送到每一个参与的抽检账号表的app端
         * 8. 将抽检计划返回给前台并展示
         *
         * */
        for (int i = 0; i <selectedsamplingaccountid.length ; i++) {
            System.out.println(selectedsamplingaccountid[i]);
        }
        for (int i = 0; i <typeoffoodselectedid.length ; i++) {
            System.out.println(typeoffoodselectedid[i]);
        }
        return null;
    }
}
