package com.aguo.blogapi.config;

import com.aguo.blogapi.handle.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: aguo
 * @DateTime: 2022/4/24 17:32
 * @Description: TODO
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
//    /**
//     * 解决跨域请求问题
//     * @param registry
//     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
//    }

    private CorsConfiguration corsConfiguration(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
        .addPathPatterns("/**/admin")
        .addPathPatterns("/comments/create/change")
        .addPathPatterns("/articles/publish") //插入和修改文章
        .addPathPatterns("/**/delete/**");//删除文章

    }

    /**
     * 这里配置成，先解决跨域问题，之后再拦截处理。
     * 如果先拦截并且拦截成功的话，那么就没办法处理跨域问题，那么前端就会报错跨域异常，不会提示未登录的提示
     * @return
     */
/*    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration());
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }*/
}
