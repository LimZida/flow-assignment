package madras.flow.assignment.common.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
/**
 * title : SecurityConfig
 *
 * description : SecurityConfig 커스텀, Spring security 요청 접근 제어
 *
 * reference : Spring security 아키텍처 구조 및 흐름 : https://twer.tistory.com/entry/Security-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0%EC%9D%98-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EA%B5%AC%EC%A1%B0-%EB%B0%8F-%ED%9D%90%EB%A6%84
 *                                                : https://velog.io/@younghoondoodoom/Spring-Security%EC%97%90-%EB%8C%80%ED%95%B4%EC%84%9C-%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%9D%B8%EC%A6%9D-%EA%B5%AC%EC%A1%B0
 *
 *             Srping security Configure 부분 : https://gksdudrb922.tistory.com/217
 *
 *
 * author : 임현영
 * date : 2024.05.30
 **/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    private final CorsFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 서버에 인증정보를 저장하지 않기 때문에(stateless, rest api) csrf를 추가할 필요가 없다.
                .httpBasic().disable() // 기본 인증 로그인 사용하지 않음. (rest api)
                .formLogin().disable()
                // session 설정 -> stateless(사용하지 않음)
                .addFilter( corsFilter )//cors 관련 설정 security에 추가, 특정 컨트롤러에 cors 관련 설정(@CrossOrigin)을 추가하는 것은 인증X 경우에만 O
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // request permission
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll() // 모든 경로
                .antMatchers("/").permitAll() // index.html
                .antMatchers(HttpMethod.OPTIONS,"/**/**").permitAll() // corsfilter에 대한 모든 경로

                // exception handling
                .and()
                .exceptionHandling();

        return http.build();
    }
}
