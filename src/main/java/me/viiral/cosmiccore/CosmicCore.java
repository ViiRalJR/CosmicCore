package me.viiral.cosmiccore;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import me.mattstudios.mf.base.CommandManager;
import me.mattstudios.mf.base.components.TypeResult;
import me.viiral.cosmiccore.conf.ConfigurationService;
import me.viiral.cosmiccore.modules.armorsets.commands.AdminArmorSetCommand;
import me.viiral.cosmiccore.modules.armorsets.commands.CrystalExtractorCommand;
import me.viiral.cosmiccore.modules.armorsets.commands.MultiCommand;
import me.viiral.cosmiccore.modules.armorsets.listeners.ArmorListener;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSet;
import me.viiral.cosmiccore.modules.armorsets.struct.ArmorSetRegister;
import me.viiral.cosmiccore.modules.enchantments.commands.*;
import me.viiral.cosmiccore.modules.enchantments.gkits.Gkit;
import me.viiral.cosmiccore.modules.enchantments.gkits.GkitManager;
import me.viiral.cosmiccore.modules.enchantments.gkits.users.GkitUser;
import me.viiral.cosmiccore.modules.enchantments.gkits.users.GkitUserAdapter;
import me.viiral.cosmiccore.modules.enchantments.gkits.users.GkitUserManager;
import me.viiral.cosmiccore.modules.enchantments.language.LanguageHandler;
import me.viiral.cosmiccore.modules.enchantments.listeners.*;
import me.viiral.cosmiccore.modules.enchantments.struct.configuration.ConfigManager;
import me.viiral.cosmiccore.modules.enchantments.struct.EnchantRegister;
import me.viiral.cosmiccore.modules.enchantments.struct.configuration.EnchantConfigManager;
import me.viiral.cosmiccore.modules.enchantments.struct.enchantstruct.Enchantment;
import me.viiral.cosmiccore.modules.enchantments.struct.enums.EnchantTier;
import me.viiral.cosmiccore.modules.enchantments.struct.items.OrbBuilder;
import me.viiral.cosmiccore.modules.enchantments.struct.souls.SoulManager;
import me.viiral.cosmiccore.modules.mask.command.MaskCommand;
import me.viiral.cosmiccore.modules.mask.listener.MaskListener;
import me.viiral.cosmiccore.modules.mask.struct.MaskRegister;
import me.viiral.cosmiccore.modules.skins.command.SkinCommand;
import me.viiral.cosmiccore.modules.skins.listener.SkinListener;
import me.viiral.cosmiccore.modules.skins.struct.SkinRegister;
import me.viiral.cosmiccore.modules.user.UserManager;
import me.viiral.cosmiccore.modules.user.commands.BlessCommand;
import me.viiral.cosmiccore.utils.CC;
import me.viiral.cosmiccore.utils.DamageHandler;
import me.viiral.cosmiccore.utils.WorldGuardUtils;
import me.viiral.cosmiccore.utils.cache.CacheManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.stream.Collectors;

@Getter
public final class CosmicCore extends JavaPlugin implements Listener {

    private static CosmicCore instance;

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(GkitUser.class, new GkitUserAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create();
    private ConfigurationService configurationService;
    private EnchantConfigManager enchantConfigManager;
    private ArmorSetRegister armorSetRegister;
    private LanguageHandler languageHandler;
    private EnchantRegister enchantRegister;
    private WorldGuardUtils worldGuardUtils;
    private GkitUserManager gkitUserManager;
    private CommandManager commandManager;
    private DamageHandler damageHandler;
    private ConfigManager configManager;
    private CacheManager cacheManager;
    private MaskRegister maskRegister;
    private SkinRegister skinRegister;
    private SoulManager soulManager;
    private GkitManager gkitManager;
    private UserManager userManager;
    boolean joinable = false;


    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        //this.configurationService = new ConfigurationService();

        this.languageHandler = new LanguageHandler(this);
        this.languageHandler.load();

        sendConsoleMessage("LanguageHandler");

        this.cacheManager = new CacheManager();

        sendConsoleMessage("CacheManager");

        this.worldGuardUtils = new WorldGuardUtils();

        sendConsoleMessage("WorldGuardHook");

        this.damageHandler = new DamageHandler(this);

        sendConsoleMessage("DamageHandler");

        this.armorSetRegister = new ArmorSetRegister();
        this.armorSetRegister.initialize();

        sendConsoleMessage("ArmorSetRegister");

        this.commandManager = new CommandManager(this);

        this.soulManager = new SoulManager();

        sendConsoleMessage("SoulManager");

        this.userManager = new UserManager();

        sendConsoleMessage("UserManager");

        this.enchantRegister = new EnchantRegister(this);
        this.enchantRegister.initialize();

        sendConsoleMessage("EnchantRegister");

        this.enchantConfigManager = new EnchantConfigManager(this);
        this.enchantConfigManager.setupFiles();

        sendConsoleMessage("EnchantConfigManager");

        this.configManager = new ConfigManager(this);
        this.configManager.setupFiles();

        sendConsoleMessage("ConfigManager");

        this.gkitManager = new GkitManager(this);
        this.gkitManager.initialize();

        sendConsoleMessage("GkitManager");

        this.gkitUserManager = new GkitUserManager(this);
        this.gkitUserManager.loadAll();

        sendConsoleMessage("GkitUserManager");

        this.maskRegister = new MaskRegister(this);
        this.maskRegister.initialize();

        sendConsoleMessage("MaskRegister");

        this.skinRegister = new SkinRegister(this);
        this.skinRegister.initialize();

        sendConsoleMessage("SkinRegister");

        this.commandManager = new CommandManager(this);
        this.registerCommandParameters();
        this.registerCommandCompletion();
        this.commandManager.register(new AdminEnchantsCommand(this), new EnchanterCommand(), new ApplyCommand(), new GkitCommand(this), new Tinkerer(), new SplitSoulsCommand(), new WithdrawSoulsCommand(), new AdminArmorSetCommand());

        sendConsoleMessage("CommandManager");

        getCommand("givecrystal").setExecutor(new MultiCommand());
        getCommand("givemask").setExecutor(new MaskCommand());
        getCommand("giveextractor").setExecutor(new CrystalExtractorCommand());
        getCommand("giveskin").setExecutor(new SkinCommand());
        getCommand("bless").setExecutor(new BlessCommand());
        this.registerListeners();

        sendConsoleMessage("Listeners");

    }

    @Override
    public void onDisable() {
//        try {
//            this.configurationService.saveAllFiles();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }


    public static CosmicCore getInstance() {
        return instance;
    }

    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ArmorListener(), this);
        pm.registerEvents(new me.viiral.cosmiccore.utils.armor.ArmorListener(), this);
        pm.registerEvents(new BookListeners(this), this);
        pm.registerEvents(new EnchantmentListener(), this);
        pm.registerEvents(new ItemListener(this), this);
        pm.registerEvents(new SoulListener(this), this);
        pm.registerEvents(new ArmorSwapListener(this), this);
        pm.registerEvents(new TinkererListeners(), this);
        pm.registerEvents(new MaskListener(), this);
        pm.registerEvents(new SkinListener(), this);
        pm.registerEvents(new HolyWhiteScrollListener(), this);
    }

    private void registerCommandParameters() {
        this.commandManager.getParameterHandler().register(ArmorSet.class, argument -> {
            ArmorSet armorSet = this.armorSetRegister.getArmorSetFromID(this.armorSetRegister.getEnchantIDFromName(String.valueOf(argument)));
            return new TypeResult(armorSet, argument);
        });
        this.commandManager.getParameterHandler().register(Enchantment.class, argument -> {
            Enchantment enchantment = this.enchantRegister.getEnchantmentFromID(String.valueOf(argument).toLowerCase(Locale.ROOT));
            return new TypeResult(enchantment, argument);
        });

        this.commandManager.getParameterHandler().register(EnchantTier.class, argument -> {
            EnchantTier enchantTier = EnchantTier.valueOf(String.valueOf(argument).toUpperCase());
            return new TypeResult(enchantTier, argument);
        });

        this.commandManager.getParameterHandler().register(OrbBuilder.OrbType.class, argument -> {
            OrbBuilder.OrbType orbType = OrbBuilder.OrbType.valueOf(String.valueOf(argument).toUpperCase());
            return new TypeResult(orbType, argument);
        });

        this.commandManager.getParameterHandler().register(Gkit.class, argument -> {
            Gkit gkit = this.gkitManager.getGkitFromID(String.valueOf(argument).toLowerCase(Locale.ROOT));
            return new TypeResult(gkit, argument);
        });
    }

    private void registerCommandCompletion() {
        this.commandManager.getCompletionHandler().register("#sets", input -> this.armorSetRegister.getArmorSets().stream().map(ArmorSet::getID).collect(Collectors.toList()));
        this.commandManager.getCompletionHandler().register("#enchants", input -> this.enchantRegister.getEnchantments().stream().map(Enchantment::getID).collect(Collectors.toList()));
        this.commandManager.getCompletionHandler().register("#gkits", input -> this.gkitManager.getGkits().stream().map(Gkit::getID).collect(Collectors.toList()));
    }

    private void sendConsoleMessage(String s) {
        Bukkit.getConsoleSender().sendMessage(CC.translate("&a(CosmicCore) Successfully loaded " + s + "."));
    }
}
