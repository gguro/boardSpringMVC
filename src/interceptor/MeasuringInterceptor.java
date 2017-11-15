package interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MeasuringInterceptor extends HandlerInterceptorAdapter {
	// 로그객체 생성
	// MeasuringInterceptor.class : 로그 남길 대상
	static Log log = LogFactory.getLog(MeasuringInterceptor.class);

	// 뷰페이지 실행이후 자동 호출(callback)
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 24hh:mm:ss");
		String log_time = df.format(now).toString();
		System.out.println("[" + log_time + "] : " + request.getRequestURI());
		// 로그 메세지
		// 다수의 Controller가 관심을 가지는 공통코드
		// 로그수준 : 정보(info) (로그수준 종류 : error, debug, info)
		log.info("[" + log_time + "] : " + request.getRequestURI());
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("1. AC 실행전에 preHandle() 호출 >>>>");
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("2. AC 실행후에 postHandle() 호출 <<<<");
		super.postHandle(request, response, handler, modelAndView);
	}

}
