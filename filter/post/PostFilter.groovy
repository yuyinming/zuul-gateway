import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class PreFilter extends ZuulFilter {

    Logger log = LoggerFactory.getLogger(PreFilter.class)

    @Override
    String filterType() {
        return "post"
    }

    @Override
    int filterOrder() {
        return 2000
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() {
        log.info("this is a post filter: Receive response")
        HttpServletResponse response = RequestContext.getCurrentContext().getResponse()
        response.getOutputStream().print(", i am George!")
        response.flushBuffer()
    }
}