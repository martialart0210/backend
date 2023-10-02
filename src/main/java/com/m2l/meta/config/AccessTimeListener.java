package com.m2l.meta.config;

import com.m2l.meta.repository.UserRepository;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
public class AccessTimeListener implements HttpSessionListener, ApplicationListener<SessionDestroyedEvent>, Serializable, HttpSessionBindingListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Autowired
    UserRepository userRepository;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(1 * 60);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    /*HttpSession session = event.getSession();
    LoginDetailsBean loginDetailsBean = (LoginDetailsBean) session.getAttribute("loginUser");
    String loginTimeStamp = DateHelper.dateToString(loginDetailsBean.getLoginTimestamp());
    String logoutTimeStamp = DateHelper.dateToString(new Date());
    String loginId = loginDetailsBean.getLoginUserid();*/
        // updateLogoutTime(logoutTimeStamp,loginId,loginTimeStamp);
        System.out.printf("Session ID %s destroyed at %s%n", event.getSession().getId(), new Date());
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {

        HttpSession session = (HttpSession) event.getSource();
//        LoginDetailsBean loginDetailsBean = (LoginDetailsBean) session.getAttribute("loginUser");
//        String loginTimeStamp = DateHelper.dateToString(loginDetailsBean.getLoginTimestamp());
//        String logoutTimeStamp = DateHelper.dateToString(new Date());
//        String loginId = loginDetailsBean.getLoginUserid();
//        updateLogoutTime(logoutTimeStamp,loginId,loginTimeStamp);
    }

//    public void updateLogoutTime(String logoutTimeStamp, String loginId, String loginTimeStemp) {
//        loginLogoutLogService.updateLogoutTime(logoutTimeStamp, loginId, loginTimeStemp);
//        System.out.println("Database update successfully...");
//    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        System.out.println("valueBound:" + event.getName() + " session:" + event.getSession().getId() );

    }

    public void registerSession() {
        // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put( "sessionBindingListener", this  );
        System.out.println( "registered sessionBindingListener"  );
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("valueUnBound:" + event.getName() + " session:" + event.getSession().getId() );
        // add you unlock code here:
        // clearLocksForSession( event.getSession().getId() );
    }
}
