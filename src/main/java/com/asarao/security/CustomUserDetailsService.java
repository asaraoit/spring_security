package com.asarao.security;

import com.asarao.model.SysRole;
import com.asarao.model.SysUser;
import com.asarao.model.SysUserRole;
import com.asarao.service.SysRoleService;
import com.asarao.service.SysUserRoleService;
import com.asarao.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        //从数据库取出用户信息
        SysUser user = userService.selectByName(s);
        if(user == null){
            throw new UsernameNotFoundException("当前用户不存在");
        }
        //添加权限
        List<SysUserRole> userRoles = userRoleService.listByUserId(user.getId());
        userRoles.forEach(userRole->{
            SysRole role = roleService.selectById(userRole.getRoleId());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(user.getName(),user.getPassword(),authorities);
    }
}
