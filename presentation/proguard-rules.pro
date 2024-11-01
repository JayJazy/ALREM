# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile




-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

-dontnote kotlinx.serialization.**


# Keep generic signatures; needed for correct type resolution
-keepattributes Signature

# Keep Crashlytics deobfuscation info
-keepattributes SourceFile,LineNumberTable

# Keep custom model classes
-keep class com.jayys.alrem.** { *; }

# Keep Hilt
-keep class dagger.hilt.** { *; }
-keepclassmembers class * {
@dagger.hilt.* <fields>;
}
-keepclassmembers class * {
    @dagger.hilt.* <methods>;
}
-keep class com.jayys.alrem.di.** { *; }
-keep class hilt_aggregated_deps.** { *; }
-keep class dagger.hilt.internal.aggregatedroot.codegen.** { *; }
-keep class * extends dagger.hilt.internal.aggregatedroot.codegen._com_jayys_alrem_MyApp { *; }

# Keep Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}


# Keep Gson stuff
-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# ViewModel
-keep class * extends androidx.lifecycle.ViewModel { *; }


# Keep any class or method annotated with these
-keep @androidx.annotation.Keep class *
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}


# google Ads
-keep public class com.google.android.gms.ads.** {
    public *;
}

# Keep WorkManager-related classes
-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.ListenableWorker {
   public <init>(android.content.Context,androidx.work.WorkerParameters);
}

# Keep Hilt-related classes
-keep class androidx.hilt.work.HiltWorkerFactory
-keep class * extends androidx.hilt.work.HiltWorkerFactory

# Keep your worker classes
-keep class com.jayys.alrem.broadcastreceiver.alarmreset.AlarmResetWorker { *; }

-keep class * extends androidx.work.Worker
-keep class * extends androidx.work.ListenableWorker
-keepclassmembers class * extends androidx.work.ListenableWorker {
    public <init>(android.content.Context,androidx.work.WorkerParameters);
}

-keepattributes SourceFile,LineNumberTable
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
