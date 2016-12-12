package refer.util;

import java.lang.reflect.Method;

public class ReflectUtil {

    /** get属性
     * @param obj
     *            操作的对象
     * @param att
     *            操作的属性
     * */
    public static Object getter(Object obj, String att) {
        try {
            Method method = obj.getClass().getMethod("get" + StringUtil.UpperFirst(att));
            return method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /** set属性
     * @param obj
     *            操作的对象
     * @param att
     *            操作的属性
     * @param value
     *            设置的值
     * @param type
     *            参数的属性
     * */
    public static void setter(Object obj, String att, Object value,
            Class<?> type) {
        try {
            Method method = obj.getClass().getMethod("set" + StringUtil.UpperFirst(att), type);
            method.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
