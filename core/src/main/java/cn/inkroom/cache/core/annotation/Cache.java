package cn.inkroom.cache.core.annotation;


import java.lang.annotation.*;

/**
 * 脚本的语法和解析引擎相关
 * <p>默认使用SpEl语法</p>
 *
 * @author 墨盒
 * @date 2019/10/26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    /**
     * 当有效期少于指定值时，主动更新缓存
     * <p>默认不启用</p>
     *
     * @return
     */
    long expire() default -1;

    /**
     * 获取key的脚本，具体语法和解析引擎相关
     * <br/>
     * 使用 rv 访问返回值结果——注意rv有可能不存在
     * 参数可直接访问
     *
     * @return
     */
    String key();

    /**
     * 决定要缓存的对象的脚本；
     * <br/>
     * 仅在被@Caches包裹的情况下生效
     *
     * @return
     */
    String data() default "";

    /**
     * 是否将结果缓存，返回boolean的脚本
     * 返回true则缓存数据，默认为true
     * <p>脚本，参数统一放在params下，返回结果为rv</p>
     * <p>假设需要根据参数page来判断是否缓存，则可以写成 params.page==3 意为当参数page为3时，缓存数据</p>
     * <p>假设需要根据返回结果来判断，可以写成 rv.age==31 </p>
     * <p>如果返回结果是一个数组，可以通过size()获取大小</p>
     *
     * @return
     */
    String condition() default "";

    /**
     * ttl，单位毫秒
     *
     * @return
     */
    long ttl() default 5 * 60 * 1000;

    /**
     * 是否缓存空值
     * <p>当数据不为null时，是否缓存将由condition决定</p>
     *
     * @return
     */
    boolean nullable() default false;

    /**
     * 是否启用锁
     * <p>如果不上锁的情况下，在高并发时不能保证重复请求会从缓存中获取</p>
     * <p>锁使用双重判断模式，尽可能保证减少db请求，并减少锁带来的开销</p>
     *
     * @return
     */
    boolean sync() default false;


    /**
     * 是否在有效期添加一个随机值，避免缓存雪崩
     * 指定随机数的范围
     * <p>长度为2 </p>
     *
     * @return 单位秒
     */
    int[] random() default {};

    /**
     * 决定是查询缓存还是删除缓存
     * 默认为查询缓存
     *
     * @return
     */
    boolean del() default false;

    /**
     * 决定是否忽略以前缓存的脚本
     * <br/>
     * 当脚本返回true时，忽略以前的缓存，执行实际的方法，并将返回结果缓存
     *
     * @return
     */
    String ignore() default "";
}
