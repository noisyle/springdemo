package com.noisyle.crowbar.core.hibernate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * 使SprinMVC使用Jackson2序列化Hibernate4 lazyload方式的model时，能够正常返回查询结果
 * 需要启用OpenSessionInView
 * Created by wangyue on 2015/8/16.
 */
public class HibernateAwareObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 6633954021310384140L;

	public HibernateAwareObjectMapper() {
        Hibernate4Module module = new Hibernate4Module();
        module.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING, true);
        registerModule(module);
    }
}
