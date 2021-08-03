package xyz.xcyd.wechat.offiaccount.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Bean工具类
 */
@Slf4j
public class BeanUtil extends org.springframework.beans.BeanUtils {

    /**
     * 对象属性复制
     **/
    public static <T> T copyField(Object source, Class<T> clazz){
        if(null == source){
            return null;
        }
        try {
            T t = instantiateClass(clazz);
            copyProperties(source, t);
            return t;
        }catch (Exception e){
            log.error("复制Bean异常{}->{}", source, clazz.getName(), e);
        }
        return null;
    }
}
