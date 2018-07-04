package com.github.euonmyoji.epicbanitem.configuration;

import com.github.euonmyoji.epicbanitem.EpicBanItem;
import com.github.euonmyoji.epicbanitem.check.CheckRule;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class BanConfig {
    public static final TypeToken<CheckRule> RULE_TOKEN = TypeToken.of(CheckRule.class);
    private boolean editable;
    private Path path;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode node;

    private Map<String, List<CheckRule>> rules;

    public BanConfig(Path path) {
        this.path = path;
        this.loader = HoconConfigurationLoader.builder().setPath(path).build();
    }

    public BanConfig(Path path, boolean editable) {
        this(path);
        this.editable = editable;
    }

    public boolean isEditable() {
        return editable;
    }

    public Map<String, List<CheckRule>> getRules() {
        return rules;
    }

    //todo:何时加载
    //todo:出现错误暂时捕获 加载完全部之后再抛出? 或者返回一个布尔值表示十分出错?
    public void reload() throws ObjectMappingException, IOException {
        this.node = loader.load();
        rules = new LinkedHashMap<>();
        for (Map.Entry<Object, ? extends CommentedConfigurationNode> entry : node.getNode("epicbanitem").getChildrenMap().entrySet()) {
            rules.put(entry.getKey().toString(), entry.getValue().getList(RULE_TOKEN));
        }
        if (editable) {
            save();
        } else {
            rules = Collections.unmodifiableMap(rules);
        }
    }

    //todo:先备份再保存?
    public void save() throws IOException, ObjectMappingException {
        if (editable) {
            node.getNode("epicbanitem").setValue(new TypeToken<Map<String, List<CheckRule>>>() {
            }, rules);
            loader.save(node);
        }
    }

    public static Map<ItemType, List<CheckRule>> findType(Map<String, List<CheckRule>> rules) {
        Map<ItemType, List<CheckRule>> map = new HashMap<>();
        for (Map.Entry<String, List<CheckRule>> entry : rules.entrySet()) {
            Optional<ItemType> optionalItemType = Sponge.getRegistry().getType(ItemType.class, entry.getKey());
            if (optionalItemType.isPresent()) {
                map.put(optionalItemType.get(), entry.getValue());
            } else {
                EpicBanItem.logger.error("Cannot find item type :" + entry.getKey(), ",rules for it won't load.");
            }
        }
        return map;
    }
}