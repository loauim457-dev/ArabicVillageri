package com.example.arabicvillager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ArabicVillager extends JavaPlugin implements Listener {

    private final Random random = new Random();
    private final Map<UUID, Long> cooldown = new HashMap<>();

    private final Map<String, String[]> lines = new HashMap<>();
    private final String[] defaultLines = {
        "أهلاً يا غريب، نورت القرية!",
        "الجو حلو النهارده، مش كده؟",
        "حذاري من الزومبي بالليل!",
        "هممم... عندي حاجات حلوة للبيع."
    };

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        lines.put("farmer", new String[]{"المحصول السنة دي حلو أوي!", "عندي خبز طازة، تعالى شوف."});
        lines.put("librarian", new String[]{"العلم نور يا صاحبي.", "عندي كتب سحرية نادرة."});
        lines.put("armorer", new String[]{"الدرع الكويس بينقذ حياتك.", "حديد من أجود الأنواع!"});
        lines.put("weaponsmith", new String[]{"سيوفي بتقطع زي الحرير.", "محتاج سلاح يا بطل؟"});
        lines.put("toolsmith", new String[]{"الأدوات الجيدة أساس الشغل.", "فأس ولا معول؟"});
        lines.put("butcher", new String[]{"لحمة طازة النهارده!", "تحب تاخد شوية ستيك؟"});
        lines.put("cleric", new String[]{"البركة فيك يا ابني.", "عندي جرعات تنفعك."});
        lines.put("fisherman", new String[]{"السمك النهارده كتير!", "الصيد هوايتي."});
        lines.put("nitwit", new String[]{"أنا... مش عارف أنا فين.", "هاه؟ إنت بتكلمني؟"});
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Villager v)) return;
        if (e.getHand() != EquipmentSlot.HAND) return;

        long now = System.currentTimeMillis();
        Long last = cooldown.get(e.getPlayer().getUniqueId());
        if (last != null && now - last < 2000) return;
        cooldown.put(e.getPlayer().getUniqueId(), now);

        String prof = v.getProfession().getKey().getKey();
        String[] pool = lines.getOrDefault(prof, defaultLines);
        String msg = pool[random.nextInt(pool.length)];

        e.getPlayer().sendMessage(ChatColor.GREEN + "[قروي] " + ChatColor.WHITE + msg);
    }
    }
