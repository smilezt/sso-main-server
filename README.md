# sso-main-server
单点登录的主站点，负责统一管理登录登出
##实现方式
使用jwt生成token来记录用户的登录状态，其他网站均需要从本网站获取token以及验证token的正确性。
