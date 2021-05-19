package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.common.utils.OfficeTool;
import com.bjfu.fcro.common.utils.ResultTool;
import com.bjfu.fcro.dao.SamplingFoodListDao;
import com.bjfu.fcro.entity.SysSamplingFoodList;
import com.bjfu.fcro.service.SysCommonMethodService;
import com.bjfu.fcro.service.SysSamplingFoodListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SysSamplingFoodListServiceimpl implements SysSamplingFoodListService{

    @Autowired
    private SamplingFoodListDao samplingFoodListDao;

    @Autowired
    private SysCommonMethodService sysCommonMethodService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object selectAllBySspid(int pagesize_true, int pageindex_true,int ssp_id) {
        List<SysSamplingFoodList> listres = samplingFoodListDao.selectAllBySspid(pagesize_true, pageindex_true,ssp_id);
        Map<String,Object> res = new HashMap<>();
        res.put("listres",listres);
        res.put("total",samplingFoodListDao.selectCountId(ssp_id));
        return  ResultTool.success(res);
    }

    @Override
    public Object addSamplingFood(String sampling_food_type, String food_specific_name, Integer food_counts,String sampler, String spot_check_location, String sampling_time,     Integer ssp_id,
                                  Integer ssl_id,String remarks,MultipartFile[] uploadfiles) throws ParseException {
        Date date = sdf.parse(sampling_time);

        SysSamplingFoodList sysSamplingFoodList = new SysSamplingFoodList();
        sysSamplingFoodList.setSampling_food_type(sampling_food_type);
        sysSamplingFoodList.setFood_specific_name(food_specific_name);
        sysSamplingFoodList.setSampler(sampler);
        sysSamplingFoodList.setSpot_check_location(spot_check_location);
        sysSamplingFoodList.setSampling_time(date);
        sysSamplingFoodList.setSsl_id(ssl_id);
        sysSamplingFoodList.setSsp_id(ssp_id);
        sysSamplingFoodList.setSampling_status(0);
        sysSamplingFoodList.setFood_counts(food_counts);
        sysSamplingFoodList.setRemarks(remarks);
        String paths[] = new String[uploadfiles.length];
        String names[] = new String[uploadfiles.length];
        StringBuilder stringBuilder;
        for (int i = 0; i < uploadfiles.length; i++) {
            MultipartFile file = uploadfiles[i];
            stringBuilder =  new StringBuilder();
            stringBuilder.append(file.getOriginalFilename());
            stringBuilder.insert(stringBuilder.lastIndexOf("."),System.currentTimeMillis());
            OfficeTool.uploadAndReturnPath(file,String.valueOf(ssl_id),stringBuilder.toString(),true);
            names[i] = "/images/"+ssl_id+"/"+stringBuilder.toString();
        }
        stringBuilder =new StringBuilder();
        stringBuilder.append("{\"url\":[");
        for (int i=0;i<paths.length;i++){
            stringBuilder.append("\""+names[i]+"\"");
            if(i!=paths.length-1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]}");
        sysSamplingFoodList.setImage_link(stringBuilder.toString());
        samplingFoodListDao.insertNewSamplingListDao(sysSamplingFoodList);
        return ResultTool.success();
    }

    @Override
    public Object selectImages(int id) {

        return ResultTool.success(samplingFoodListDao.selectById(id).getImage_link());
    }
}
