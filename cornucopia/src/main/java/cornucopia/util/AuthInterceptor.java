package cornucopia.util;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
	/**
	 * controller 执行之前调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
//		System.out.println(url);
		if (url.equals("/auth/login")) {
			return true;
		} else {
			response.setStatus(403);
			response.setHeader("content-type", "text/html;charset=UTF-8");
			String data = "Unauthorized authority!";
			OutputStream outputStream = response.getOutputStream();
			byte[] dataByteArr = data.getBytes("UTF-8");
			outputStream.write(dataByteArr);
			return false;
		}
	}
}
