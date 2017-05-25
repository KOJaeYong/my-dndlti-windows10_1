package net.dndlti.web;
import javax.servlet.http.HttpSession;
import net.dndlti.domain.User;

public class HttpSessionUtils {
  //상수로 세션 키를 생성
  public static final String USER_SESSION_KEY="sessionUser";
  
  //세션로그인 여부를 확인할 수 있는 메소드
  public static boolean isLoginUser(HttpSession session){
    User sessionedUser = 
    (User) session.getAttribute(USER_SESSION_KEY);
    if (sessionedUser == null) {
      return false;
    }
    return true;
  }
  
  //세션로그인을 한 User 객체인지를 확인하는 메소드
  public static User getUserFromSession(
  HttpSession session) {
    /*return (User) session.getAttribute(USER_SESSION_KEY);*/
    User sessionedUser = 
    (User) session.getAttribute(USER_SESSION_KEY);
    return sessionedUser;
  }
}
