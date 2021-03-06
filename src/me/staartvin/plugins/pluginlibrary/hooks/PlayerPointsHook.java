package me.staartvin.plugins.pluginlibrary.hooks;

import me.staartvin.plugins.pluginlibrary.Library;
import org.black_ixx.playerpoints.PlayerPoints;

import java.util.UUID;

/**
 * PlayerPoints library,
 * <a href="https://dev.bukkit.org/projects/playerpoints">link</a>.
 * <p>
 * 
 * @author Staartvin
 *
 */
public class PlayerPointsHook extends LibraryHook {

	private PlayerPoints api;

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.staartvin.plugins.pluginlibrary.hooks.LibraryHook#isAvailable()
	 */
	@Override
	public boolean isAvailable() {
        return this.getPlugin().getServer().getPluginManager().isPluginEnabled(Library.PLAYERPOINTS.getInternalPluginName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.staartvin.plugins.pluginlibrary.hooks.LibraryHook#hook()
	 */
	@Override
	public boolean hook() {
		if (!isAvailable())
			return false;

		api = (PlayerPoints) this.getPlugin().getServer().getPluginManager()
                .getPlugin(Library.PLAYERPOINTS.getInternalPluginName());

		return api != null;
	}

	/**
	 * Get the number of points a player has.
	 * @param uuid UUID of the player
	 * @return number of player points a player has. -1 if PlayerPoints is not available, 0 if player is not found.
	 */
    public int getPlayerPoints(UUID uuid) {
		if (!this.isAvailable()) return -1;

		return api.getAPI().look(uuid);
	}

	/**
	 * Set the number of PlayerPoints a player has.
	 * @param uuid UUID of the player
	 * @param value value to set it to.
	 * @return whether the points were successfully set or not.
	 */
	public boolean setPlayerPoints(UUID uuid, int value) {
		if (!this.isAvailable()) return false;

		return api.getAPI().set(uuid, value);
	}

	/**
	 * Give a player some player points.
	 * @param uuid UUID of the player
	 * @param value number of points to give
	 * @return whether the player has received the player points successfully.
	 */
	public boolean givePlayerPoints(UUID uuid, int value) {
		return api.getAPI().give(uuid, value);
	}

	/**
	 * Take points from a player.
	 * @param uuid UUID of the player
	 * @param value number of points to take
	 * @return whether the points have successfully been taken from the player.
	 */
	public boolean takePlayerPoints(UUID uuid, int value) {
		return api.getAPI().take(uuid, value);
	}
}
