package com.lc.spider.modules.hosjoy.service;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

/**
 * @author liucan
 * @date 2020/4/20 15:33
 */
@Service
public class LoginService {

    private static final String LOGIN_URL = "https://testb2b.hosjoy.com:4832/login";

    public void login() {

        try {
            Connection connect = Jsoup.connect(LOGIN_URL);
            // 伪造请求头
            connect.header("Accept", "application/json, text/javascript, */*; q=0.01").header("Accept-Encoding",
                    "gzip, deflate");
            connect.header("Accept-Language", "zh-CN,zh;q=0.9").header("Connection", "keep-alive");
            connect.header("Content-Length", "72").header("Content-Type",
                    "application/x-www-form-urlencoded; charset=UTF-8");
            connect.header("Host", "qiaoliqiang.cn").header("Referer", "http://qiaoliqiang.cn/Exam/");
            connect.header("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36")
                    .header("X-Requested-With", "XMLHttpRequest");

            // 携带登陆信息
            connect.data("username", "362501197407067215").data("password", "123456");

            //请求url获取响应信息
            Connection.Response res = connect.ignoreContentType(true).method(Connection.Method.GET).execute();// 执行请求

            System.out.println(res.body());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
