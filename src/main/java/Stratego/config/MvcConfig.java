package Stratego.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/replay").setViewName("replay");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/board").setViewName("board");
        registry.addViewController("/greet").setViewName("greet");
        registry.addViewController("/make_move").setViewName("board");
        registry.addViewController("/swap_piece").setViewName("board");
        registry.addViewController("/replay").setViewName("replay");
        registry.addViewController("/error").setViewName("404");
        registry.addViewController("/to_replay").setViewName("replay");
        registry.addViewController("/get_Movelist").setViewName("replay");



    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/static/**")
//                .addResourceLocations("/static/")
//                .setCachePeriod(3600)   ;
//    }

}