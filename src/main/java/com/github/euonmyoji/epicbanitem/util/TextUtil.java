package com.github.euonmyoji.epicbanitem.util;

import com.sun.istack.internal.Nullable;
import org.spongepowered.api.text.Text;

import static org.spongepowered.api.text.Text.builder;
import static org.spongepowered.api.text.Text.of;
import static org.spongepowered.api.text.action.TextActions.runCommand;
import static org.spongepowered.api.text.action.TextActions.showText;

public class TextUtil {

    /**
     * @param command  指令#点击后执行的 带/
     * @param describe 指令描述
     * @param hoverStr 覆盖时的text 如果没有就用默认的
     * @return 带gui的指令提示text
     */
    public Text commandGui(String command, String describe, @Nullable String hoverStr) {
        return builder().append(of(command + " " + describe))
                .onClick(runCommand(command))
                .onHover(showText(of((hoverStr = hoverStr != null ? hoverStr : "点击执行" + command))))
                .build();
    }
}