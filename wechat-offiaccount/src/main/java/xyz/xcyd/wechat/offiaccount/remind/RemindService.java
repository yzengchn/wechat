package xyz.xcyd.wechat.offiaccount.remind;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Service;
import xyz.xcyd.wechat.offiaccount.builder.TextBuilder;
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

        }else if(timeUnits.length > 1){

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
