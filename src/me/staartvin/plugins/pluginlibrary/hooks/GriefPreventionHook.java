package me.staartvin.plugins.pluginlibrary.hooks;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import me.staartvin.plugins.pluginlibrary.Library;
import org.bukkit.Location;

import java.util.UUID;

/**
 * GriefPrevention library,
 * <a href="https://www.spigotmc.org/resources/griefprevention.1884/">link</a>.
 * <p>
 * 
 * @author Staartvin
 * 
 */
public class GriefPreventionHook extends LibraryHook {

    private GriefPrevention griefPrevention;

	/*
	 * (non-Javadoc)
	 * 
	 * @see me.staartvin.plugins.pluginlibrary.hooks.LibraryHook#isAvailable()
	 */
	@Override
	public boolean isAvailable() {
        return this.getPlugin().getServer().getPluginManager().isPluginEnabled(Library.GRIEFPREVENTION
                .getInternalPluginName());
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

        griefPrevention = (GriefPrevention) this.getPlugin().getServer().getPluginManager()
                .getPlugin(Library.GRIEFPREVENTION.getInternalPluginName());

        return griefPrevention != null;
    }

    private PlayerData getPlayerData(UUID uuid) {
        return griefPrevention.dataStore.getPlayerData(uuid);
    }

    /**
     * Get the number of claims a player has made
     * @param uuid UUID of the player
     * @return number of claims a player made, or -1 if no data is available
     */
    public int getNumberOfClaims(UUID uuid) {
        if (!this.isAvailable()) {
            return -1;
        }

        PlayerData data = this.getPlayerData(uuid);

        return data.getClaims().size();
    }

    /**
     * Get the number of claimed blocks a player has made
     * @param uuid UUID of the player
     * @return number of claimed blocks a player made, or -1 if no data is available
     */
    public int getNumberOfClaimedBlocks(UUID uuid) {
        if (!this.isAvailable())
            return -1;

        PlayerData data = this.getPlayerData(uuid);

        return data.getAccruedClaimBlocks();
    }

    /**
     * Get the number of remaining blocks that can be claimed by a player
     * @param uuid UUID of the player
     * @return the number of remaining blocks that can be claimed by a player, or -1 if no data is available
     */
    public int getNumberOfRemainingBlocks(UUID uuid) {
        if (!this.isAvailable())
            return -1;

        PlayerData data = this.getPlayerData(uuid);

        return data.getRemainingClaimBlocks();
    }

    /**
     * Get the number of bonus blocks a player can claim
     * @param uuid UUID of the player
     * @return number of bonus blocks a player can claim, or -1 if no data is available
     */
    public int getNumberOfBonusBlocks(UUID uuid) {
        if (!this.isAvailable())
            return -1;

        PlayerData data = this.getPlayerData(uuid);

        return data.getBonusClaimBlocks();
    }

    /**
     * Check whether a location is in someone's claim.
     *
     * @param loc
     *            Location to check
     * @param uuid
     *            UUID of player to find the claim of
     * @return true if the given location is in the player's claim; false
     *         otherwise.
     */
    public boolean isInRegion(Location loc, UUID uuid) {

        if (!this.isAvailable()) return false;

        PlayerData data = this.getPlayerData(uuid);

        Claim claim = griefPrevention.dataStore.getClaimAt(loc, false, data.lastClaim);

        return claim != null;

    }

}
