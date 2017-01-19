package com.centaline.trans.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * SpringUtils拓展
 * 对空值不作复制
 * 
 * @author jjm
 *
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

	public static void copyProperties(Object source, Object target, boolean ignoreNullValue) throws BeansException {
		copyProperties(source, target, null, ignoreNullValue, (String[]) null);
	}

	public static void copyProperties(Object source, Object target) throws BeansException {
		copyProperties(source, target, null, true, (String[]) null);
	}

	public static void copyProperties(Object source, Object target, Class<?> editable, boolean ignoreNullValue)
			throws BeansException {
		copyProperties(source, target, editable, ignoreNullValue, (String[]) null);
	}

	public static void copyProperties(Object source, Object target, Class<?> editable) throws BeansException {
		copyProperties(source, target, editable, true, (String[]) null);
	}

	public static void copyProperties(Object source, Object target, boolean ignoreNullValue, String... ignoreProperties)
			throws BeansException {
		copyProperties(source, target, null, ignoreNullValue, ignoreProperties);
	}

	public static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
		copyProperties(source, target, null, true, ignoreProperties);
	}

	private static void copyProperties(Object source, Object target, Class<?> editable, boolean ignoreNullValue,
			String... ignoreProperties) throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName()
						+ "] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0],
							readMethod.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source);
							if (value == null && ignoreNullValue)
								continue;
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						} catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}
}
