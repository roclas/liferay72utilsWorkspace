package loggingServletFilter;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
                "servlet-context-name=",
                "servlet-filter-name=LifeDev Document Filter",
                "url-pattern=/*"
        },
        service = Filter.class
)
public class LoggingServletFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            
            String uri= (String)servletRequest.getAttribute(WebKeys.INVOKER_FILTER_URI);
            _log.error(String.format("==>accessing %s",uri));
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            _log.warn("Failed to check document access.");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }

    @Reference
    private UserLocalService _userLocalService;

    private static final Log _log = LogFactoryUtil.getLog(LoggingServletFilter.class);
}
