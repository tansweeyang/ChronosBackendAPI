package dev.eislyn.chronos.api.annotation;

import java.lang.annotation.*;

/**
 * @author <a herf="mailto:wuqi@terminus.io">xunyard</a>
 * @date 2019-05-13
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MicroService {
    /**
     * @return name of the Sentinel resource
     */
    String value() default "";

    /**
     * @return the entry type (inbound or outbound), outbound by default
     */

    /**
     * @return name of the block exception function, empty by default
     */
    String blockHandler() default "";

    /**
     * The {@code blockHandler} is located in the same class with the original methodName by default.
     * However, if some methods share the same signature and intend to set the same block handler,
     * then users can set the class where the block handler exists. Note that the block handler methodName
     * must be static.
     *
     * @return the class where the block handler exists, should not provide more than one classes
     */
    Class<?>[] blockHandlerClass() default {};

    /**
     * @return name of the fallback function, empty by default
     */
    String fallback() default "";

    /**
     * @return the list of exception classes to trace, {@link Throwable} by default
     * @since 1.5.1
     */
    Class<? extends Throwable>[] exceptionsToTrace() default {Throwable.class};
}
