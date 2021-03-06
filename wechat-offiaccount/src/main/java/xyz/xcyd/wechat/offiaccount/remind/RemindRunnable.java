package xyz.xcyd.wechat.offiaccount.remind;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

/**
 * 定时提醒
 */
@Slf4j
@AllArgsConstructor
public class RemindRunnable implements Runnable {

    private String appid;
    private WxMpService wxMpService;
    private WxMpXmlMessage wxMessage;

    @Override
    public void run() {

        // 异步需要指定appid
        try {
            wxMpService.switchoverTo(appid).getKefuService().sendKefuMessage(WxMpKefuMessage.TEXT().toUser(wxMessage.getFromUser()).content("提醒：" + wxMessage.getContent()).build());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

    }
}
