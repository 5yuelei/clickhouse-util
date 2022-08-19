package com.lontologie.conf;


import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.lontologie.collect.LongList;
import com.lontologie.collect.StringList;
import com.lontologie.handlers.ClickHouseArrayToLongListTypeHandler;
import com.lontologie.handlers.ClickHouseArrayToStringListTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return configuration -> {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            typeHandlerRegistry.register(StringList.class, JdbcType.ARRAY,new ClickHouseArrayToStringListTypeHandler());
            typeHandlerRegistry.register(LongList.class,JdbcType.ARRAY,new ClickHouseArrayToLongListTypeHandler());
        };
    }
}
