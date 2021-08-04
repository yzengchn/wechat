package xyz.xcyd.wechat.offiaccount.proxy;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.mitre.dsmiley.httpproxy.ProxyServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义业务代理实现
 */
public class BusinessProxyServlet extends ProxyServlet {

    @Override
    protected HttpResponse doExecute(HttpServletRequest servletRequest, HttpServletResponse servletResponse, HttpRequest proxyRequest) throws IOException {


        // TODO 业务处理之后 flag设为true就放行
        boolean flag = true;

        HttpResponse httpResponse;
        if (flag) {
            httpResponse = super.doExecute(servletRequest, servletResponse, proxyRequest);
        } else {
            httpResponse = new BasicHttpResponse(new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 403, "Forbidden"));
        }
        return httpResponse;
    }
}
