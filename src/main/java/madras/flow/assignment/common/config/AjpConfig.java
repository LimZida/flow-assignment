package madras.flow.assignment.common.config;

import lombok.extern.slf4j.Slf4j;
import madras.flow.assignment.common.enums.CodeEnum;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * title : AjpConnection
 *
 * description : apache - tomcat ajp config
 *
 * reference : 개발 참고 https://jang8584.tistory.com/252 , https://eastpost95.blogspot.com/2020/03/apache-tomcat-ajp-cve-2020-1938-apache.html
 *                      https://domdom.tistory.com/488
 *
 *
 * author : 임현영
 * date : 2024.05.30
 **/

@Slf4j
@Configuration
public class AjpConfig {
     @Value("${server.tomcat.ajp.protocol}")
     private String ajpProtocol;
     @Value("${server.tomcat.ajp.port}")
     private int ajpPort;
     @Value("${spring.profiles.active}")
     private String active;
     @Value("${server.tomcat.ajp.address}")
     private String address;
     @Value("${server.tomcat.ajp.allowedRequestAttributesPattern}")
     private String allowedRequestAttributesPattern;
     @Value("${ajp.key}")
     private String ajpKey;

     @Bean
     public ServletWebServerFactory servletContainer() {
       TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
       tomcat.addAdditionalTomcatConnectors(createAjpConnector());
       return tomcat;
     }

     private Connector createAjpConnector() {
       Connector ajpConnector = new Connector(ajpProtocol);
       ajpConnector.setPort(ajpPort);
       ajpConnector.setAllowTrace(false);

       log.info("###### spring profiles active : {}",active);

       ajpConnector.setSecure(false);
       ajpConnector.setScheme(CodeEnum.HTTP.getValue());
       ajpConnector.setProperty("address",address);
       ajpConnector.setProperty("allowedRequestAttributesPattern", allowedRequestAttributesPattern);
       ((AbstractAjpProtocol<?>) ajpConnector.getProtocolHandler()).setSecret(ajpKey);
       ((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(true);
       return ajpConnector;
     }
}
