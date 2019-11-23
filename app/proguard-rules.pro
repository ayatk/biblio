# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/opt/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# ----------------------------------------
# Support Library
# ----------------------------------------
-dontwarn android.databinding.**

# http://stackoverflow.com/questions/40176244/how-to-disable-bottomnavigationview-shift-mode
-keepclassmembers class android.support.design.internal.BottomNavigationMenuView {
    boolean mShiftingMode;
}

# ----------------------------------------
# RxJava
# ----------------------------------------
-dontwarn io.reactivex.internal.util.unsafe.**
-keep class io.reactivex.schedulers.Schedulers {
    public static <methods>;
}
-keep class io.reactivex.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.TestScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.Schedulers {
    public static ** test();
}

# ----------------------------------------
# Retrofit
# ----------------------------------------
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn retrofit2.**

# ----------------------------------------
# OkHttp
# ----------------------------------------
-dontwarn okhttp3.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# ----------------------------------------
# Jsoup
# ----------------------------------------
-keep public class org.jsoup.** {
    public *;
}

# ----------------------------------------
# EventBus
# ----------------------------------------
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# ----------------------------------------
# Dagger2
# ----------------------------------------
-dontwarn com.google.errorprone.annotations.*

-keep public class * extends androidx.versionedparcelable.VersionedParcelable
