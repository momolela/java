package com.momolela.test;

import com.momolela.bean.Person;

import java.util.logging.Logger;

public class ModuleTest {

    private static final Logger LOGGER = Logger.getLogger("ModuleTest");

    public static void main(String[] args) {
        // module-info.java 文件中包含了：requires java9Module;
        Person person = new Person("sunzj", 26);
        System.out.println(person.toString());

        // module-info.java 文件中包含了：requires java.logging;
        LOGGER.info("Logger 是导入的类库包");
    }

}
