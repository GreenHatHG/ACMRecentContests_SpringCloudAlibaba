package team.huoguo.acm.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import team.huoguo.acm.commons.utils.JWTUtil;
import team.huoguo.acm.gateway.service.feign.UserInfoService;

import java.util.Arrays;
import java.util.Map;

/**
 * 鉴权过滤器
 * GreenHatHG
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Value("${auth.skip.urls}")
    private String[] skipAuthUrls;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        //跳过不需要验证的路径
        if(Arrays.asList(skipAuthUrls).contains(url)){
            return chain.filter(exchange);
        }
        //从请求头中取出token
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token == null || token.isEmpty() ||
                !JWTUtil.verify(token, JWTUtil.getId(token),
                        userInfoService.getPassword(JWTUtil.getId(token)))) {
            ServerHttpResponse response = exchange.getResponse();
            // 封装错误信息
            Map<String, Object> responseData = Maps.newHashMap();
            responseData.put("code", 401);
            responseData.put("message", "非法请求");

            try {
                // 将信息转换为 JSON
                ObjectMapper objectMapper = new ObjectMapper();
                byte[] data = objectMapper.writeValueAsBytes(responseData);

                // 输出错误信息到页面
                DataBuffer buffer = response.bufferFactory().wrap(data);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return response.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return chain.filter(exchange);
    }

    /**
     * 设置过滤器的执行顺序
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }


}