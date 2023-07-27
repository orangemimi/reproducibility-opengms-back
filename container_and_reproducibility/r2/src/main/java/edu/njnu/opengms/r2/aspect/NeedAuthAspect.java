package edu.njnu.opengms.r2.aspect;


import edu.njnu.opengms.common.enums.ResultEnum;
import edu.njnu.opengms.common.exception.MyException;
import edu.njnu.opengms.common.utils.JwtUtils;
import edu.njnu.opengms.r2.domain.creator.Creator;
import edu.njnu.opengms.r2.domain.creator.CreatorService;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName NeedAuthAspect
 * @Description todo
 * @Author sun_liber
 * @Date 2018/11/28
 * @Version 1.0.0
 */
@Aspect
@Component
@Order (2)
public class NeedAuthAspect {
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";

    @Autowired
    CreatorService creatorService;

    @Pointcut ("@annotation(edu.njnu.opengms.r2.annotation.NeedAuth)")
    public void point() {
    }

    @Before ("point()")
    public void doBeforePoint() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        String auth = JwtUtils.getTokenFromRequest(request);
        System.out.println(auth);
        if (null == auth || !auth.startsWith(TOKEN_PREFIX)) {
            response.setStatus(401);
            throw new MyException(ResultEnum.NO_TOKEN);
        } else {
            Claims claims = JwtUtils.parseJWT(auth);
            Creator creator = creatorService.findByName((String) claims.get(NAME));
            if (creator == null) {
                response.setStatus(401);
                throw new MyException(ResultEnum.NO_OBJECT);
            } else if (!creator.getPassword().equals(claims.get(PASSWORD))) {
                response.setStatus(401);
                throw new MyException(ResultEnum.USER_PASSWORD_NOT_MATCH);
            }
        }
    }
}
