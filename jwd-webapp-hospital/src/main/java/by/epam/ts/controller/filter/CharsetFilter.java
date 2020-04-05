package by.epam.ts.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet Filter implementation class CharsetFilter
 */
public class CharsetFilter implements Filter {
	
	private static final String ENCODING = "characterEncoding";
	private String defaultEncoding = "utf-8";
	
	private static final Logger log = LogManager.getLogger(CharsetFilter.class);
	
    public CharsetFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(defaultEncoding);
		response.setCharacterEncoding(defaultEncoding);
		log.info("Charset was set successfully.");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		String encoding = fConfig.getInitParameter(ENCODING);
		if (encoding != null) {
            defaultEncoding = encoding;
        }
 
	}

}
