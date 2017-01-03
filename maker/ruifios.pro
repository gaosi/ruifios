# Specify the input jars, output jars, and library jars. jdk8

#-injars  in.jar
-injars ../../build
-outjars ../../release/fengcore.jar

-libraryjars <java.home>/lib/rt.jar
-libraryjars <java.home>/lib/jce.jar
-libraryjars <java.home>/lib/jsse.jar
-libraryjars ../../lib
-libraryjars ../../lib_storage

# Save the obfuscation mapping to a file, so you can de-obfuscate any stack
# traces later on. Keep a fixed source file attribute and all line number
# tables to get line numbers in the stack traces.
# You can comment this out if you're not interested in stack traces.

-printmapping out.map
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-dontshrink

# You can print out the seeds that are matching the keep options below.

#-printseeds out.seeds

# Preserve all public servlets.

-keep public class * implements javax.servlet.Servlet

# Preserve all native method names and the names of their classes.

-keepclasseswithmembernames class * {
    native <methods>;
}

# Preserve the special static methods that are required in all enumeration
# classes.

-keepclassmembers class * extends java.lang.Enum {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
# You can comment this out if your library doesn't use serialization.
# If your code contains serializable classes that have to be backward
# compatible, please refer to the manual.

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Your application may contain more items that need to be preserved;
# typically classes that are dynamically created using Class.forName:

# -keep public class mypackage.MyClass
# -keep public interface mypackage.MyInterface
# -keep public class * implements mypackage.MyInterface

-keepnames class *
-keepclassmembers class com.ruifios.** {
	public <methods>;
	protected <methods>;
	public <fields>;
	protected <fields>;
}
-keepclassmembers class com.ruifios.** {
	@org.nutz.dao.entity.annotation.* <fields>;
	@org.nutz.mvc.annotation.* <fields>;
}
-keepclassmembers class com.ruifios.** extends java.lang.Throwable {
	<fields>;
}
-adaptclassstrings
-renamesourcefileattribute SourceFile
-keepattributes Exceptions, InnerClasses, Signature, *Annotation*, SourceFile, LineNumberTable, Deprecated
