/**
 * AISThink.com Inc.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.centaline.trans.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 
 * @author ouyq
 * @version $Id: MyBatisGenerator.java, v 0.1 2015年4月13日 上午11:55:17 ouyq Exp $
 */
public class MyBatisGeneratorTest {
    public static void main(String[] args) {
        List<String> warnings = new ArrayList<String>();
        final boolean overwrite = true;
        File configFile = new File("src/main/resources/generatorConfig.xml");
        //System.out.println("config fiel is in : " + configFile.getAbsoluteFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration configuration;
        try {
            configuration = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator mybatisGenerator = new MyBatisGenerator(configuration, callback,
                warnings);
            mybatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
