package me.viiral.cosmiccore.modules.enchantments.struct;

import com.google.common.collect.ImmutableList;
import com.massivecraft.factions.shade.apache.Validate;
import lombok.Getter;
import me.viiral.cosmiccore.CosmicCore;
import me.viiral.cosmiccore.modules.enchantments.enchants.heroic.*;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import me.viiral.cosmiccore.modules.enchantments.enchants.soul.*;
import me.viiral.cosmiccore.modules.enchantments.enchants.legendary.*;
import me.viiral.cosmiccore.modules.enchantments.enchants.ultimate.*;
import me.viiral.cosmiccore.modules.enchantments.enchants.elite.*;
import me.viiral.cosmiccore.modules.enchantments.enchants.unique.*;
import me.viiral.cosmiccore.modules.enchantments.enchants.simple.*;

public class EnchantRegister {

    @Getter
    private static EnchantRegister instance;
    private final CosmicCore plugin;
    private final Map<String, Enchantment> enchantments;


    public EnchantRegister(CosmicCore plugin) {
        instance = this;
        this.plugin = plugin;
        this.enchantments = new HashMap<>();
    }

    public void initialize() {
        registerAll(
                new BloodyDeepWounds(), new BrutalBarbarian(), new DeepWounds(), new GodlyOverload(), new HolyAegis(), new MightyCactus(), new PaladinArmored(),
                new PlanetaryDeathbringer(), new ReflectiveBlock(), new RighteousAntiGank(), new ShadowAssassin(), new VampiricDevour(),

                new Aquatic(), new Gears(), new Glowing(), new AutoSmelt(), new EnderShift(), new CreeperArmor(), new Spirits(), new Ghost(), new FeatherWeight(),
                new Obliterate(), new ThunderingBlow(), new Aegis(), new AntiGravity(), new Bleed(),
                new Blessed(), new Insomnia(), new Entangle(), new Frozen(), new Haste(), new Blind(),
                new Implants(), new Trap(), new RocketEscape(), new Cactus(), new Teleportation(), new Voodoo(), new Angelic(),
                new Shockwave(), new Dodge(), new DoubleStrike(), new Guardians(), new Valor(), new Tank(), new ObsidianDestroyer(), new ObsidianShield(), new Metaphysical(),
                new Armored(), new BloodLust(), new Deathbringer(), new Diminish(), new Drunk(), new Enlighted(),
                new Execute(), new Overload(), new Inquisitive(), new Insanity(), new Lifesteal(), new Rage(),
                new Silence(), new Confusion(), new Springs(), new RepairGuard(), new IceAspect(), new Demonforged(), new Reforged(), new Hardened(),
                new Wither(), new Vampire(), new Poison(), new Poisoned(), new Stormcaller(), new Solitude(), new Experience(), new Oxygenate(), new Lifebloom(),
                new Curse(), new SkillSwipe(), new Ravenous(), new Molten(), new Berserk(), new SelfDestruct(), new Ragdoll(), new DeepWounds(), new Pummel(),
                new Paralyze(), new Greatsword(), new DivineImmolation(), new Dominate(), new Clarity(), new Epicness(), new Decapitation(), new Telepathy(),
                new Commander(), new Trickster(), new Shackle(), new Teleblock(), new Paradox(), new Phoenix(), new NaturesWrath(), new DeathGod(), new Barbarian(),
                new Inversion(), new Devour(), new Cleave(), new SpiritLink(), new Leadership(), new Disintegrate(), new Protection(), new Blacksmith(), new EnderWalker(),
                new Assassin(), new Destruction(), new PlagueCarrier(), new SelfDestruct(), new UndeadRuse(), new Heavy(), new ArrowDeflect(), new Block(), new SmokeBomb(),
                new Enrage(), new Immortal(), new AntiGank(), new Headless(), new Lucky()
        );
    }

    public void registerAll(Enchantment... enchantments) {
        for (Enchantment enchantment : enchantments) {
            register(enchantment);
        }
    }

    public void register(Enchantment enchantment) {
        try {
            this.enchantments.put(enchantment.getID(), enchantment);
            this.plugin.getServer().getPluginManager().registerEvents(enchantment, plugin);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to register enchantment " + enchantment.getName());
            System.out.println(e.getMessage());
        }
    }

    public void unregisterAll() {
        this.enchantments.forEach(((enchantName, enchantment) -> HandlerList.unregisterAll(enchantment)));
        this.enchantments.clear();
    }

    public void unregister(Enchantment enchantment) {
        HandlerList.unregisterAll(enchantment);
        this.enchantments.remove(enchantment.getName());
    }

    public void reload() {
        this.unregisterAll();
        this.initialize();
    }

    public boolean isEnchantment(String name) {
        return (getEnchantmentFromID(name) != null);
    }

    public Enchantment getEnchantmentFromID(String name) {
        Validate.notNull(name);
        Validate.notEmpty(name);
        return this.enchantments.get(name.toLowerCase());
    }

    public Enchantment getEnchantmentFromName(String name) {
        Validate.notNull(name);
        Validate.notEmpty(name);
        name = this.getEnchantIDFromName(name);
        return this.enchantments.get(name.toLowerCase());
    }

    public String getEnchantIDFromName(String name) {
        return name.replace(" ", "").toLowerCase(Locale.ROOT);
    }

    public Collection<Enchantment> getEnchantments(EnchantTier tier) {
        Validate.notNull(tier);
        return getEnchantments().stream().filter(customEnchantment -> (customEnchantment.getTier() == tier)).collect(Collectors.toList());
    }

    public List<Enchantment> getOrderedEnchantments(EnchantTier tier) {
        Validate.notNull(tier);
        return getEnchantments().stream().filter(customEnchantment -> (customEnchantment.getTier() == tier)).sorted(Comparator.comparing(Enchantment::getID)).collect(Collectors.toList());
    }

    public Collection<Enchantment> getEnchantments() {
        return Collections.unmodifiableCollection(this.enchantments.values());
    }
    public Enchantment selectRandomEnchantment(EnchantTier enchantTier) {
        Collection<Enchantment> enchantments = this.getEnchantments(enchantTier);
        return (enchantments.size() > 0) ? ImmutableList.copyOf(enchantments).get(ThreadLocalRandom.current().nextInt(enchantments.size())) : null;
    }
}

