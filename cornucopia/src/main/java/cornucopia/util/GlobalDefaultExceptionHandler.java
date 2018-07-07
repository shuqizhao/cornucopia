package cornucopia.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Map<String, Object> defaultExceptionHandler(HttpServletRequest req, Exception e) {
		Log4jHelper.LOGGER.error(e.getMessage(), e);
		Map<String, Object> result = new HashMap<>();
		result.put("code", 500);
		result.put("data", 0);
		result.put("message", e.getMessage());
		return result;
	}
}
