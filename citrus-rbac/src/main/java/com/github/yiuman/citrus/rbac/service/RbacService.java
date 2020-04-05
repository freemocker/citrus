package com.github.yiuman.citrus.rbac.service;

import com.github.yiuman.citrus.rbac.entity.Resource;
import com.github.yiuman.citrus.rbac.entity.User;
import com.github.yiuman.citrus.security.authorize.AuthorizeServiceHook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 鉴权服务类
 *
 * @author yiuman
 * @date 2020/3/23
 */
@Service
@Slf4j
public class RbacService implements AuthorizeServiceHook {

    private final UserService userService;

    private final RoleService roleService;

    private final ResourceService resourceService;

    public RbacService(UserService userService, RoleService roleService, ResourceService resourceService) {
        this.userService = userService;
        this.roleService = roleService;
        this.resourceService = resourceService;
    }

    @Override
    public boolean hasPermission(HttpServletRequest httpServletRequest, Authentication authentication) {
        try {
            //没有配置资源的情况下都可以访问
            Resource resource = resourceService.selectByUri(httpServletRequest.getRequestURI(), httpServletRequest.getMethod());
            if (resource == null) {
                return true;
            }

            Optional<User> user = userService.getUser(authentication);
            return user.filter(value -> roleService.hasPermission(value.getUserId(), resource.getResourceId())).isPresent();
        } catch (Exception e) {
            log.error("RbacService Exception", e);
            return false;
        }


    }
}