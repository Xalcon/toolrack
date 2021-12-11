package xalcon.toolrack.data.server;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ResourceLocation;
import xalcon.toolrack.ToolRackMod;

public class ModBlocksLootTableProvider extends LootTableProvider {

    public ModBlocksLootTableProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK));
    }
    
    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        final Set<ResourceLocation> modLootTableIds =
            LootTables
                .all()
                .stream()
                .filter(lootTable -> lootTable.getNamespace().equals(ToolRackMod.MOD_ID))
                .collect(Collectors.toSet());

        for (ResourceLocation id : Sets.difference(modLootTableIds, map.keySet()))
            validationtracker.reportProblem("Missing mod loot table: " + id);

        map.forEach((id, lootTable) -> LootTableManager.validate(validationtracker, id, lootTable));
    }

    @Override
    public String getName() {
        return ToolRackMod.MOD_ID + "_lootTables";
    }
    
}
