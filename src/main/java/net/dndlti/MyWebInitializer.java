package net.dndlti;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class MyWebInitializer extends 
SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder 
  configure(SpringApplicationBuilder builder) {

    //sources - Add more sources (configuration classes 
    //and components) to this application.
    /*src/main/java/net/dndlti/MyDndltiWindows10Application.java
        클래스 파일의 configuration classes and components 들을 
    build 하도록 하는 MyWebInitializer.java 클래스 생성
    - 톰캣 서버가 실행하면서 해당 클래스의 configure 메소드를 호출 */
    return builder.sources(
    MyDndltiWindows10Application.class);
  }
}
