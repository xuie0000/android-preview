# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/yf/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

## 位于module下的proguard-rules.pro
#####################################
######### 主程序不能混淆的代码 #########
#####################################
#-dontwarn com.xuie.androiddemo.ui.**
-keep class com.xuie.androiddemo.ui.** { *; }

-keep class com.xuie.androiddemo.ui.fragment.** { *; }
-keep class com.xuie.androiddemo.view.adapter.** { *; }
-keep class com.xuie.androiddemo.view.EnergyColumn

-dontwarn butterknife.**
-keep class butterknife.** { *; }

-dontwarn com.dd.**
-keep class com.dd.** { *; }

-dontwarn android.**
-keep class android.** { *; }

-dontwarn com.android.**
-keep class com.android.** { *; }

-dontwarn com.squareup.**
-keep class com.squareup.** { *; }

-dontwarn jp.wasabeef.**
-keep class jp.wasabeef.** { *; }

-dontwarn rx.**
-keep class rx.** { *; }

# 友盟更新
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}
-keep public class * extends com.umeng.**
-dontwarn com.umeng.**
-keep class com.umeng.** { *; }

#####################################
########### 不优化泛型和反射 ##########
#####################################
-keepattributes Signature

