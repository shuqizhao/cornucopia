package cornucopia.util;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import cornucopia.entity.MenuEntity;
import cornucopia.entity.UserEntity;
import cornucopia.service.MenuService;
import cornucopia.service.WhiteListService;

public class AuthInterceptor implements HandlerInterceptor {
	/**
	 * controller 执行之前调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		if(url == "/error") {
			return true;
		}
		boolean isAllowPass = WhiteListService.getInstance().allowPassNotNeedLogin(url);
//		 Log4jHelper.LOGGER.info(url);
		if (isAllowPass) {
			return true;
		}
		
		UserEntity userEntity = (UserEntity)request.getSession().getAttribute("user");
		if(userEntity == null) {
			CookieUtil.set(response, "adAuthCookie", "", 3600 * 24);
			return false;
		}
		
		Cookie cookie = CookieUtil.get(request, "adAuthCookie");
		if (cookie != null) {
			String adAuthCookie = cookie.getValue();
			if (adAuthCookie.equals("true")) {
				
				isAllowPass = WhiteListService.getInstance().allowPassNeedLogin(url);
				if (isAllowPass) {
					return true;
				}
				
				int userId = userEntity.getId();
				List<MenuEntity> menus = MenuService.getInstance().getAllMenus(userId);
				List<MenuEntity> curMenus = menus.stream().filter((MenuEntity r) -> r.getApi()!=null && r.getApi().toLowerCase().equals(url.toLowerCase()))
						.collect(Collectors.toList());
				if(curMenus.size()>0) {
					return true;
				}
			}
		}
		response.setStatus(403);
		response.setHeader("content-type", "text/html;charset=UTF-8");
		String data = "Unauthorized authority!";
		OutputStream outputStream = response.getOutputStream();
		byte[] dataByteArr = data.getBytes("UTF-8");
		outputStream.write(dataByteArr);
		return false;
	}
}
