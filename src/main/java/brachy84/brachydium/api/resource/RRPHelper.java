package brachy84.brachydium.api.resource;

import brachy84.brachydium.Brachydium;
import brachy84.brachydium.api.block.OreVariant;
import brachy84.brachydium.api.item.MaterialItem;
import brachy84.brachydium.api.unification.material.Material;
import brachy84.brachydium.api.unification.material.info.MaterialIconSet;
import brachy84.brachydium.api.unification.ore.TagDictionary;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.blockstate.JWhen;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.tags.JTag;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Map;

import static brachy84.brachydium.Brachydium.RESOURCE_PACK;
import static net.devtech.arrp.json.blockstate.JState.*;
import static net.devtech.arrp.json.loot.JLootTable.*;
import static net.devtech.arrp.json.models.JModel.model;
import static net.devtech.arrp.json.models.JModel.*;
import static net.devtech.arrp.json.tags.JTag.tag;

public class RRPHelper {
    private static Identifier mtId(String string) {
        return Brachydium.id(string);
    }
    // the key is the file name
    // client

    public static final Map<String, byte[]> otherResources = new HashMap<>();

    public static void initOtherResources() {
        for (Map.Entry<String, byte[]> entry : otherResources.entrySet()) {
            //System.out.println("Loading other resources: " + entry.getKey());
            RESOURCE_PACK.addResource(ResourceType.SERVER_DATA, Brachydium.id(entry.getKey()), entry.getValue());
        }
    }

    public static void addBasicMaterialItemModel(Material material, TagDictionary.Entry tag, boolean withOverlay) {
        Identifier path = Brachydium.id("item/material/" + tag.lowerCaseName + "." + material);
        String texture = MaterialItem.getTexturePath(material, tag);
        JTextures textures = new JTextures().layer0(texture);
        if (withOverlay)
            textures.layer1(texture + "_overlay");
        RESOURCE_PACK.addModel(model().parent("item/generated").textures(textures), path);
    }

    public static void addBasicMaterialBlockState(Material material, TagDictionary.Entry tag) {
        Identifier path = Brachydium.id("material/" + tag.lowerCaseName + "." + material);
        JState state = state(variant(JState.model("brachydium:block/material_sets/" + material.getMaterialIconSet().name + "/" + tag.lowerCaseName)));
        RESOURCE_PACK.addBlockState(state, path);
    }

    public static void addOreBlockState(Material material) {
        Identifier path = Brachydium.id("material/" + TagDictionary.ore.lowerCaseName + "." + material);
        JVariant jVariant = variant();
        for (OreVariant.Variant variant : OreVariant.getAll()) {
            jVariant.put("variant", variant.getName(), JState.model(Brachydium.id("block/material_sets/" + material.getMaterialIconSet().name + "/ore_type/" + variant.getName())));
        }
        RESOURCE_PACK.addBlockState(state(jVariant), path);
    }

    public static void generateOreModels() {
        for (MaterialIconSet iconSet : MaterialIconSet.ICON_SETS.values()) {
            for (OreVariant.Variant variant : OreVariant.getAll()) {
                JModel model = model("brachydium:block/ore_template")
                        .textures(textures().var("base", variant.getTexturePath())
                                .var("overlay", "brachydium:block/material_sets/" + iconSet.name + "/" + TagDictionary.ore.lowerCaseName));
                RESOURCE_PACK.addModel(model, Brachydium.id("block/material_sets/" + iconSet.name + "/ore_type/" + variant.getName()));
            }
        }
    }

    public static void generateMaterialBlockModel(TagDictionary.Entry tag) {
        for (MaterialIconSet iconSet : MaterialIconSet.ICON_SETS.values()) {
            Identifier path = Brachydium.id("block/material_sets/" + iconSet.name + "/" + tag.lowerCaseName);
            String texture = "brachydium:block/material_sets/" + iconSet.name + "/" + tag.lowerCaseName;
            JModel model = model().parent("brachydium:block/all_side_template").textures(textures().var("all", texture));
            RESOURCE_PACK.addModel(model, path);
        }
    }

    public static void addBasicMaterialBlockItemModel(Material material, TagDictionary.Entry tag) {
        Identifier path = Brachydium.id("item/material/" + tag.lowerCaseName + "." + material);
        JModel model = model().parent("brachydium:block/material_sets/" + material.getMaterialIconSet().name + "/" + tag.lowerCaseName);
        RESOURCE_PACK.addModel(model, path);
    }

    public static void addOreBlockItemModel(Material material) {
        Identifier path = Brachydium.id("item/material/" + TagDictionary.ore.lowerCaseName + "." + material);
        JModel model = model().parent("brachydium:block/material_sets/" + material.getMaterialIconSet().name + "/ore_type/stone");
        RESOURCE_PACK.addModel(model, path);
    }

    public static void addSimpleMaterialItemTag(Material material, TagDictionary.Entry tag) {
        Identifier path = new Identifier("c", "items/" + material + "_" + tag.lowerCaseName + "s");
        Identifier item = MaterialItem.createItemId(material, tag);
        RESOURCE_PACK.addTag(path, tag().add(item));
    }

    public static void addItemTag(String tag, Identifier... values) {
        JTag jTag = tag();
        for (Identifier id : values) {
            jTag.add(id);
        }
        RESOURCE_PACK.addTag(new Identifier(tag), jTag);
    }

    public static void addSimpleLootTable(Identifier id) {
        JLootTable lootTable = loot("minecraft:block")
                .pool(pool()
                        .rolls(1)
                        .entry(entry()
                                .type("minecraft:item")
                                .name(id.toString())));
        RESOURCE_PACK.addLootTable(new Identifier(id.getNamespace(), "blocks/" + id.getPath()), lootTable);
    }

    public static void addPipeBlockState(String material, int size) {

        JState state = state()
                .add(multipart(new JBlockModel(mtId("block/cable/cable_core_" + size))))
                .add(multipart(new JBlockModel(mtId("block/cable/cable_side_" + size)).uvlock()).when(new JWhen().add("north", "true")))
                .add(multipart(new JBlockModel(mtId("block/cable/cable_side_" + size)).uvlock().y(90)).when(new JWhen().add("east", "true")))
                .add(multipart(new JBlockModel(mtId("block/cable/cable_side_" + size)).uvlock().y(180)).when(new JWhen().add("south", "true")))
                .add(multipart(new JBlockModel(mtId("block/cable/cable_side_" + size)).uvlock().y(270)).when(new JWhen().add("west", "true")))
                .add(multipart(new JBlockModel(mtId("block/cable/cable_side_" + size)).uvlock().x(270)).when(new JWhen().add("up", "true")))
                .add(multipart(new JBlockModel(mtId("block/cable/cable_side_" + size)).uvlock().x(90)).when(new JWhen().add("down", "true")));
        RESOURCE_PACK.addBlockState(state, mtId("cable/" + material + "." + size));

        JModel itemModel = model().parent(mtId("block/cable/cable_core_" + size).toString());
        RESOURCE_PACK.addModel(itemModel, mtId("item/cable/" + material + "." + size));
    }

    public static void addPipeModel(int size) {
        JModel model = model().parent(mtId("block/cable/base_core_" + size).toString()).textures(textures().var("all", mtId("block/cable/wire").toString()));
        System.out.println(model.toString());
        RESOURCE_PACK.addModel(model, mtId("block/cable/cable_core_" + size));
        RESOURCE_PACK.addModel(model().parent(mtId("block/cable/base_side_" + size).toString()).textures(textures().var("all", mtId("block/cable/wire").toString())), mtId("block/cable/cable_side_" + size));
    }

    public static void addPipeModelTemplate(int size) {
        String texture = "all";
        float from = 8 - size / 2f;
        float to = 8 + size / 2f;
        JModel core = model().textures(
                textures().particle("#" + texture) // rrp doesn't add # to particle texture for some reason
        ).element(
                element().from(from, from, from).to(to, to, to)
                        .faces(faces()
                                .up(face(texture).tintIndex(1).uv(from, from, to, to))
                                .down(face(texture).tintIndex(1).uv(from, from, to, to))
                                .north(face(texture).tintIndex(1).uv(from, from, to, to))
                                .south(face(texture).tintIndex(1).uv(from, from, to, to))
                                .east(face(texture).tintIndex(1).uv(from, from, to, to))
                                .west(face(texture).tintIndex(1).uv(from, from, to, to))
                        ).shade()
        );
        JModel side = model().textures(textures().particle(texture)).element(
                element().from(from, from, 0).to(to, to, from)
                        .faces(faces()
                                .up(face(texture).tintIndex(1).uv(from, 0, to, from))
                                .down(face(texture).tintIndex(1).uv(from, 0, to, from))
                                .north(face(texture).tintIndex(1).uv(from, from, to, to).cullface(Direction.SOUTH))
                                .south(face(texture).tintIndex(1).uv(from, from, to, to))
                                .east(face(texture).tintIndex(1).uv(from, 0, to, from).rot270())
                                .west(face(texture).tintIndex(1).uv(from, 0, to, from).rot90())
                        ).shade()
        );
        RESOURCE_PACK.addModel(core, mtId("block/cable/base_core_" + size));
        RESOURCE_PACK.addModel(side, mtId("block/cable/base_side_" + size));
    }

    public static void addGenericMbeBlockState(Identifier id) {
        JState state = state().add(variant().put("", JState.model(id.getNamespace() + ":block/generic_tile")));
        RESOURCE_PACK.addBlockState(state, id);
    }
}
