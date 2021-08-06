package xyz.xcyd.wechat.offiaccount.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2配置
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean
    public Docket restApi() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("微信开发-接口文档")
                        .description("#由 https://doc.xiaominfo.com/knife4j 构建的文档")
                        .termsOfServiceUrl("https://blog.xcyd.xyz")
                        .contact(new Contact("下次一定", "https://blog.xcyd.xyz", "yzengchn@gmail.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("1.0版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("xyz.xcyd.wechat.offiaccount"))
                .paths(PathSelectors.any())
                .build()
                //设置请求头默认参数
                .globalOperationParameters(setHeadParamter())
                ;
        return docket;
    }

    /**
     * 添加head参数
     * @return
     */
    private List<Parameter> setHeadParamter(){
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name(HttpHeaders.AUTHORIZATION)
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        pars.add(tokenPar.build());

        tokenPar.name(HttpHeaders.USER_AGENT)
                .description("请求来源标识")
                .defaultValue("PostmanRuntime/7.28.2")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();
        pars.add(tokenPar.build());

        return pars;
    }
}
