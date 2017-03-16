package com.siti.clientvisit.login;

/**
 * Created by chenji on 17/3/14.
 */
public interface ILoginView {
    /**
     * 获取用户输入的用户名
     * @return
     */
    String getUserName();

    /**
     * 获取用户输入的密码
     * @return
     */
    String getPassword();

    /**
     * 获取是否勾选保存密码
     * @return
     */
    boolean getIsRememberPassword();

    /**
     * 获取是否勾选自动登录
     * @return
     */
    boolean getIsAutoLogin();

    /**
     * 清空用户名
     */
    void clearUserName();

    /**
     * 清空密码
     */
    void clearPassword();

    /**
     * 设置是否记住密码
     * @param is
     */
    void setRememberPassword(boolean is);

    /**
     * 设置是否自动登录
     * @param is
     */
    void setAutoLogin(boolean is);

    /**
     * 开启进度条
     */
    void startProgress();

    /**
     * 关闭进度条
     */
    void stopProgress();
}
