package inaugural.soliloquy.common;

public class Check {
    public static <T> T ifNull(T obj, String className, String methodName, String paramName) {
        if (obj == null) {
            throwException(className, methodName, paramName, "null");
        }
        return obj;
    }

    public static String ifNullOrEmpty(String str, String className, String methodName,
                                       String paramName) {
        if (ifNull(str, className, methodName, paramName).equals("")) {
            throwException(className, methodName, paramName, "empty");
        }
        return str;
    }

    public static <T> T ifNullOrEmptyIfString(T obj, String className, String methodName,
                                              String paramName) {
        if (obj instanceof String) {
            //noinspection unchecked
            return (T) ifNullOrEmpty((String) obj, className, methodName, paramName);
        }
        else {
            return ifNull(obj, className, methodName, paramName);
        }
    }

    public static int ifNonNegative(int i, String className, String methodName, String paramName) {
        if (i < 0) {
            throwException(className, methodName, paramName, "negative");
        }
        return i;
    }

    private static void throwException(String className, String methodName, String paramName,
                                           String violationType) {
        throw new IllegalArgumentException(className +
                (methodName != null && !methodName.equals("") ? "." + methodName
                        : "") + ": " + paramName + " cannot be " + violationType);
    }
}
