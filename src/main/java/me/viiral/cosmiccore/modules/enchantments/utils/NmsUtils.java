package me.viiral.cosmiccore.modules.enchantments.utils;

import java.lang.reflect.Field;

public final class NmsUtils {

    private NmsUtils() {

    }

    public static Object getPrivateField(String fieldName, Class<?> clazz, Object object) {
        Field field;
        Object o = null;

        try {
            field = clazz.getDeclaredField(fieldName);

            field.setAccessible(true);

            o = field.get(object);
        }
        catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return o;
    }

}
