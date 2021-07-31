package xyz.xcyd.wechat.offiaccount.remind;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.stereotype.Service;
import xyz.xcyd.wechat.offiaccount.config.ProjectProperties;
import xyz.xcyd.wechat.offiaccount.remind.timenlp.nlp.TimeNormalizer;
import xyz.xcyd.wechat.offiaccount.remind.timenlp.nlp.TimeUnit;

/**
 * 提醒服务
 */
@Slf4j
@Service
@AllArgsConstructor
public class RemindService {

    private final RemindTask remindTask;
    private final ProjectProperties p;

    private static TimeNormalizer timeNormalizer;

    static {
        String path = TimeNormalizer.class.getResource("").getPath();
        String classPath = path.substring(0, path.indexOf("/xyz/xcyd/wechat/offiaccount"));
        System.out.println(classPath+"/TimeExp.m");
        timeNormalizer = new TimeNormalizer(classPath+"/TimeExp.m");
    }

//    @Async("asyncServicePool")
    public String apply(WxMpService wxMpService, WxMpXmlMessage wxMessage){

        TimeUnit[] timeUnits = timeNormalizer.parse(wxMessage.getContent());
        if(timeUnits.length < 1){
            log.error("内容解析失败 {}", wxMessage.getContent());
            return "o(╥﹏╥)o 暂不支持！请试试换个说法吧\n如：明天早上八点叫我收蚂蚁能量";
        }else if(timeUnits.length > 1){
            //处理带多个时间的内容
            /**
             * 1. 取出数组中的时间文字，分割事件
             * 2. 判断连续周期事件，还是单独多个一次性事件
             * 3. 单独事件处理
             * 4. 周期事件判断
             */
        }else {
            String cron = DateUtil.date(timeUnits[0].getTime()).toString("ss mm HH dd MM ?");
            remindTask.addScheduledTask(new RemindRunnable(p.getPurpose().getRemind(), wxMpService, wxMessage), cron, IdUtil.simpleUUID());
        }

        return String.format("将在%s提醒您：\n日期：%s\n内容：%s\n博客：https://blog.xcyd.xyz",
                timeUnits[0].Time_Expression,
                DateUtil.formatDateTime(timeUnits[0].getTime()),
                wxMessage.getContent());
    }


}
