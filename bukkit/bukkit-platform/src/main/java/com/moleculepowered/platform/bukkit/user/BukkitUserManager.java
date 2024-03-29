package com.moleculepowered.platform.bukkit.user;

import com.moleculepowered.api.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;

/**
 * A {@link UserManager} class created for the bukkit platform, it handles all tasks related
 * to the users handled by this platform. Allowing you to add, remove or replace existing users.
 *
 * @author OMGitzFROST
 */
public final class BukkitUserManager extends UserManager
{
    private final File userDataFolder;
    private final Plugin plugin;

    /**
     * The main constructor for this manager system, it's used to initialize all required
     * objects necessary for this class to work properly
     *
     * @param plugin Parent plugin
     */
    public BukkitUserManager(@NotNull Plugin plugin) {
        this.plugin = plugin;
        this.userDataFolder = new File(plugin.getDataFolder(), "user-data");
    }

    /**
     * This method is typically used to perform necessary setup tasks for your plugin, such as
     * registering event listeners, initializing configurations, setting up database connections,
     * or starting scheduled tasks. This method should be called when the server loads your plugin and
     * is usually where you would initialize any resources or functionality needed for your plugin to
     * operate correctly.
     */
    @Override
    public void onEnable() {

        // ENSURE USER DATA FOLDER EXISTS BEFORE ANYTHING
        if (!userDataFolder.exists() && !userDataFolder.mkdirs())
            throw new IllegalArgumentException("An error occurred whilst attempting to create the user-data folder");

        // ADD ALL OFFLINE USERS TO OUR USER MAP
        Arrays.stream(Bukkit.getOfflinePlayers()).forEach(p -> users.add(new BukkitUser(plugin, p)));
    }
}
