package com.stu.drools.util;

import com.stu.drools.common.FieldName;
import com.stu.drools.entity.Claim;
import com.stu.drools.vo.PropertyVo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FiledUtil {
    /**
     * 获取实体类 @Column 的其中一个属性名称
     *
     * @param clazz
     * @return
     */
    public static List<PropertyVo> getChName(Class<?> clazz) {
        List<PropertyVo> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FieldName.class)) {
                /**
                 * 获取字段名
                 */
                FieldName declaredAnnotation = field.getDeclaredAnnotation(FieldName.class);
                String cnName = declaredAnnotation.name();
                System.out.println(cnName);
                PropertyVo vo = new PropertyVo();
                vo.setFiledName(field.getName());
                vo.setChName(cnName);
                list.add(vo);
            }
        }
        return list;
    }

}
