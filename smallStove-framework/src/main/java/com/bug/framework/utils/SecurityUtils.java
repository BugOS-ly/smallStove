package com.bug.framework.utils;

import com.bug.framework.security.models.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 安全服务工具类
 */
public class SecurityUtils {

    private static final Long ANONYMOUS_USER_ID = -1L;
    private static final String ANONYMOUS_USER_NAME = "anonymousUser";

    /**
     * 用户ID
     **/
    public static Long getUserId() {
        return getLoginUser().getId();
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        Object principal = getAuthentication().getPrincipal();

        if (ANONYMOUS_USER_NAME.equals(principal)) {
            return new LoginUser(ANONYMOUS_USER_ID, ANONYMOUS_USER_NAME);
        }

        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


}
