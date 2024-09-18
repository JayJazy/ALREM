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

# Java 8 stuff
-dontwarn java.lang.invoke.**

# data models
-keep class com.jayys.alrem.model.** { *; }

# DAOs
-keep interface com.jayys.alrem.dao.** { *; }

# database
-keep class com.jayys.alrem.database.** { *; }

# converters
-keep class com.jayys.alrem.converter.** { *; }

# mappers
-keep class com.jayys.alrem.mapper.** { *; }

#  repositoryImpl
-keep class com.jayys.alrem.repositoryImpl.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *

-keepclassmembers class * {
    @androidx.room.* <fields>;
}


# Hilt
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

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Gson
-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# DataStore
-keep class androidx.datastore.** { *; }

# Keep any class or method annotated with these
-keep @androidx.annotation.Keep class *
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Keep specific methods
-keepclassmembers class * {
    @androidx.room.* <methods>;
}
