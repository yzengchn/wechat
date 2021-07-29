# 公众号项目

> 项目结构：每一个小功能 为一个包 如提醒功能`remind
```yaml
offiaccount
 ├── builder
 ├── config
 ├── controller
 ├── error
 ├── handler
 ├── remind
 ├── utils
 └── WechatOffiaccountApplication.java
```
## 定时提醒
> 用户输入内容，“明天早上九点上班”，“下周三去健身”
> 公众号客服消息API发送定时提醒

#### 效果
![提醒功能](https://cdn.nlark.com/yuque/0/2021/png/21736685/1627549274851-0406fa4e-2f9f-48e9-a93d-49129f6b2a89.png)
![日志](https://cdn.nlark.com/yuque/0/2021/png/21736685/1627549456543-8b98f78e-4930-4660-bdbd-12adb107c104.png)

#### 开发
- 异步线程池定时任务`xyz.xcyd.wechat.offiaccount.remind.RemindTask`
- [Time-NLP 中文语句中的时间语义识别](https://github.com/shinyke/Time-NLP)
- 任务入库(进行中)
- 提醒内容增加素材图片、音视频(规划中)
- 加入天气提醒(规划中，找免费API)

