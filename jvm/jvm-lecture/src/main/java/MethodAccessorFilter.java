import sun.jvm.hotspot.oops.InstanceKlass;
import sun.jvm.hotspot.tools.jcore.ClassFilter;

/**
 * javac -classpath ".:$JAVA_HOME/lib/sa-jdi.jar" MethodAccessorFilter.java
 *
 * java -classpath ".:$JAVA_HOME/lib/sa-jdi.jar" -Dsun.jvm.hotspot.tools.jcore.filter=MethodAccessorFilter sun.jvm.hotspot.tools.jcore.ClassDump 37
 * find ./ -name "GeneratedMethodAccessor" | xargs javap -verbose | grep `` | grep "" -c
 *
 * sudo java -classpath "$JAVA_HOME/lib/sa-jdi.jar" -Dsun.jvm.hotspot.tools.jcore.filter=sun.jvm.hotspot.tools.jcore.PackageNameFilter -Dsun.jvm.hotspot.tools.jcore.PackageNameFilter.pkgList=com.test  sun.jvm.hotspot.tools.jcore.ClassDump
 */
public class MethodAccessorFilter implements ClassFilter {
    @Override
    public boolean canInclude(InstanceKlass instanceKlass) {
        String name = instanceKlass.getName().asString();
        return name.startsWith("sun/reflect/GeneratedMethodAccessor");
    }
}
