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
# Retrofit and OkHttp
# ----------------------------------------
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn retrofit2.**

# ----------------------------------------
# Jsoup
# ----------------------------------------
-keep public class org.jsoup.** {
    public *;
}

# ----------------------------------------
# Parcel
# ----------------------------------------
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }
