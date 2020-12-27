package com.bjfu.fcro.service.serviceimpl;

import com.bjfu.fcro.service.SysCommonMethodService;
import com.bjfu.fcro.service.SysCommonService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class SysCommonServiceimpl implements SysCommonMethodService {
    @Override
    public String findadminaccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails  = (UserDetails)principal;
            return userDetails.getUsername();
        }else{
            return null;
        }
    }


}
