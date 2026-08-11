# Keep Data Models (DTO / Response Models)
-keep class com.fdhasna21.flashhideline.data.** { *; }
-keep class com.fdhasna21.flashhideline.domain.** { *; }

# Jackson Rules
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
-keep class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.**

# Retrofit Rules
-keepattributes Signature, InnerClasses, EnclosingMethod
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Room Rules
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.**

# Hilt Rules
-keep class * extends javax.inject.Provider

# Preserve generic signatures for Jackson TypeReference
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod

# Keep Jackson TypeReference and Core
-keep class com.fasterxml.jackson.core.type.TypeReference { *; }
-keepclassmembers class * extends com.fasterxml.jackson.core.type.TypeReference { *; }
-keep class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.jackson.**

# Preserve InnerClasses attribute & Signatures
-keepattributes Signature, InnerClasses, EnclosingMethod, *Annotation*

# Don't obfuscate any anonymous subclass of TypeReference
-keep class * extends com.fasterxml.jackson.core.type.TypeReference { *; }
-keepclassmembers class * extends com.fasterxml.jackson.core.type.TypeReference { *; }

# Keep Jackson Core TypeReference
-keep class com.fasterxml.jackson.core.type.TypeReference { *; }