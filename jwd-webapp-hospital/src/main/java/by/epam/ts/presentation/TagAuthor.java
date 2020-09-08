package by.epam.ts.presentation;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The class provides the user's tag without body with author's signature;
 */

public class TagAuthor extends TagSupport {
	private static final long serialVersionUID = 1L;

	public static final String AUTHOR = "Pakhomchik Nadezhda";
	public static final String ITLICS_START = "<i>";
	public static final String ITLICS_END = "</i>";

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			out.write(ITLICS_START + AUTHOR + ITLICS_END);
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY;
	}
}
