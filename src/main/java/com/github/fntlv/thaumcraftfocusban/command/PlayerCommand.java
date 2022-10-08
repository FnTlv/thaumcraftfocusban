package com.github.fntlv.thaumcraftfocusban.command;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlayerCommand {
    String cmd();
    String msg() default "";
    SenderType type();
}
