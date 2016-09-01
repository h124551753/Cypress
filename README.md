# Cypress（柏树, 持续更新中...）
## Adnroid 常用工具类

## Android 资源总结

### 1. gradle构建
[如何通过Android Studio发布library到jCenter和Maven Central](http://www.jianshu.com/p/3c63ae866e52)

### 2. Android框架分析
[Google Android框架蓝图，包含Android官方MVP架构示例项目解析](https://github.com/googlesamples/android-architecture)，[对该框架分析文章博客](http://mp.weixin.qq.com/s?__biz=MzA3ODg4MDk0Ng==&mid=403539764&idx=1&sn=d30d89e6848a8e13d4da0f5639100e5f#rd)

 [建立企业内部maven服务器并使用Android Studio发布公共项目](http://blog.csdn.net/qinxiandiqi/article/details/44458707)

[（Basic框架）Android中实现mvp模式的新思路](http://www.jianshu.com/p/31a202c0c264)


### 3. Android调试打包签名
**生成签名文件指令：**
```shell
#生成aliases为android名称为android.keystore的签名文件(-validity 20000代表有效期天数)。
keytool -genkey -alias android -keyalg RSA -validity 20000 -keystore android.keystore
```
**查看签名文件信息指令：**
```shell
# 需要输入签名文件密码
keytool -list -keystore "android.keystore"
```
**adb用法大全：**
[awesome-adb](https://github.com/mzlogin/awesome-adb)

**代码混淆**
[Android分享：代码混淆那些事](https://segmentfault.com/a/1190000004461614)

**代码补丁更新**
[Android 热修复其实很简单](http://blog.csdn.net/qq_31530015/article/details/51785228)

**内存泄露检查**
[leakcanary](https://github.com/square/leakcanary)
[Android内存泄漏检测利器：LeakCanary](http://droidyue.com/blog/2016/03/28/android-leakcanary/)

**代码测试**
[Android单元测试框架Robolectric3.0介绍](http://www.open-open.com/lib/view/open1454502431730.html)

**APK打包**
[APK瘦身记，如何实现高达53%的压缩效果](https://yq.aliyun.com/articles/57284?&utm_source=qq#)

### 4.Android开发博客集锦
[Android-collect-blogs,非常有用的集锦](https://github.com/ZQiang94/Andriod-collect-blogs)

[掘金，各种android开发动态，每天都可一看](http://gold.xitu.io/explore/android)

[Android数据加密之Rsa加密](http://www.cnblogs.com/whoislcj/p/5470095.html)

[Android开源类库栈，UI模板代码](http://www.androidblog.cn/index.php/Source)

[彻底弄懂Activity四大启动模式](http://blog.csdn.net/mynameishuangshuai/article/details/51491074)

[给 Android 开发者的 RxJava 详解](http://gank.io/post/560e15be2dca930e00da1083#toc_1)

[Android客户端性能优化（魅族资深工程师毫无保留奉献）](http://blog.tingyun.com/web/article/detail/155)

[程序员福利：各大平台免费接口，非常实用](http://mp.weixin.qq.com/s?__biz=MzA5NDIzNzY1OQ==&mid=2735610010&idx=1&sn=67e3f0833f1097a279d850e2098956ff&scene=0#wechat_redirect)

[从Android代码中来记忆23种设计模式](http://blog.csdn.net/huachao1001/article/details/51536074)

[微信 Android 热补丁实践演进之路](http://mp.weixin.qq.com/s?__biz=MzA3NTYzODYzMg==&mid=2653577297&idx=2&sn=f7dab65e2696aa9f9fda7102e91e7fcb&scene=0#wechat_redirect)

[Android探索之AIDL实现进程间通信](http://www.cnblogs.com/whoislcj/p/5509868.html)

[Android图片缓存之Glide进阶（四）](http://www.cnblogs.com/whoislcj/p/5565012.html)

[Android开发工具](http://mobdevgroup.com/tools/android#gradle)




