package com.moleculepowered.platform.bukkit.v1_9_R3.adapter;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.moleculepowered.api.localization.i18n.tl;

/**
 * Used to adapt player methods for Spigot 1.9.x
 *
 * @author OMGitzFROST
 */
@SuppressWarnings("unused")
public final class PlayerAdapter implements com.moleculepowered.platform.bukkit.adapter.PlayerAdapter
{
    private final Player player;

    // TODO: 6/9/23 ADD JAVADOC
    public PlayerAdapter(@NotNull Player player) {
        this.player = player;
    }

    /**
     * Returns the locale of the adapted player
     *
     * @return Player locale
     */
    @Override
    public String getLocale() {
        return player.spigot().getLocale();
    }

    /**
     * Returns a players current ping
     *
     * @return Players ping
     */
    @Override
    public int getPing() {
        return ((CraftPlayer) player).getHandle().ping;
    }

    /**
     * Sends the adapted player an action bar in-game
     *
     * @param message Action bar message
     */
    @Override
    public void sendActionBar(String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(tl(message)));
    }

    /**
     * <p>Sends a title and a subtitle message to the player. If either of these values are null, they will
     * not be sent and the display will remain unchanged. If they are empty strings, the display will be
     * updated as such. If the strings contain a new line, only the first line will be sent. The titles
     * will be displayed with the client's default timings.</p>
     *
     * @param title    Title text
     * @param subtitle Subtitle text
     */
    @Override
    public void sendTitle(@Nullable String title, @Nullable String subtitle) {
        sendTitle(title, subtitle, 10, 70, 20);
    }

    /**
     * <p>Sends a title and a subtitle message to the player.</p>
     * <p>If either of these values is null, they will not be sent and the display will remain unchanged.
     * If they are empty strings, the display will be updated as such. If the strings contain a new line,
     * only the first line will be sent. All timing values may take a value of -1 to indicate that they will
     * use the last value sent (or the defaults if no title has been displayed).</p>
     *
     * @param title    Title text
     * @param subtitle Subtitle text
     * @param fadeIn   time in ticks for titles to fade in. Defaults to 10.
     * @param stay     time in ticks for titles to stay. Defaults to 70.
     * @param fadeOut  time in ticks for titles to fade out. Defaults to 20.
     */
    @Override
    public void sendTitle(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut) {

        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
        connection.sendPacket(packetPlayOutTimes);

        if (subtitle != null) {
            // TODO: 5/24/23 ADD PLACEHOLDER API HOOK
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + tl(subtitle) + "\"}");
            PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
            connection.sendPacket(packetPlayOutSubTitle);
        }

        if (title != null) {
            // TODO: 5/24/23 ADD PLACEHOLDER API HOOK
            title = title.replaceAll("%player%", player.getDisplayName());
            title = ChatColor.translateAlternateColorCodes('&', title);
            IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + tl(title) + "\"}");
            PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
            connection.sendPacket(packetPlayOutTitle);
        }
    }
}
