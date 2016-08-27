# Cypress（柏树, 持续更新中...）
## Adnroid 常用工具类

## Android 资源总结

### 1. gradle构建
[如何通过Android Studio发布library到jCenter和Maven Central](http://www.jianshu.com/p/3c63ae866e52)

### 2. Android框架分析
[Google Android框架蓝图，包含Android官方MVP架构示例项目解析](https://github.com/googlesamples/android-architecture)，[对该框架分析文章博客](http://mp.weixin.qq.com/s?__biz=MzA3ODg4MDk0Ng==&mid=403539764&idx=1&sn=d30d89e6848a8e13d4da0f5639100e5f#rd)

 [建立企业内部maven服务器并使用Android Studio发布公共项目](http://blog.csdn.net/qinxiandiqi/article/details/44458707)

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

### 4.Android开发博客集锦
[Android-collect-blogs,非常有用的集锦](https://github.com/ZQiang94/Andriod-collect-blogs)

[掘金，各种android开发动态，每天都可一看](http://gold.xitu.io/explore/android)