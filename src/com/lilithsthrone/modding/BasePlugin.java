/**
 * 
 */
package com.lilithsthrone.modding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.effects.AbstractPerk;
import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.fetishes.AbstractFetish;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.inventory.enchanting.PossibleItemEffect;
import com.lilithsthrone.game.sex.SexType;

/**
 * @author Anon
 *
 */
public class BasePlugin {
	/**
	 * Mod metadata (name, version, etc)
	 */
	public PluginMetadata metadata;

	Logger log;

	public HashSet<GameCharacter> providedGameCharacters = new HashSet<GameCharacter>();

	private List<AbstractPerk> perks = new ArrayList<AbstractPerk>();

	/**
	 * Returns true when the plugin should be loaded.
	 * 
	 * @param loadedTags Currently loaded tags
	 * @return Ready to load
	 */
	public boolean isSatisfied(HashSet<String> loadedTags) {
		return loadedTags.containsAll(metadata.requires_tags);
	}

	/**
	 * Returns the tags that this plugin provides to other plugins.
	 */
	public Collection<? extends String> getProvidedTags() {
		return this.metadata.provides_tags;
	}

	/**
	 * All plugins loaded
	 */
	public void onPluginsLoaded() {}

	/**
	 * Do stuff in here to start up this plugin
	 */
	public void onStartup() {}
	
	protected final void declarePerk(String perkID, AbstractPerk perk) {
		Perk.addPerk(this, perkID, perk);
		this.perks.add(perk);
	}

	public final List<AbstractPerk> getPerks() {
		return perks ;
	}

	/**
	 * Set up NPCs here.
	 * 
	 * @param addedNpcs
	 */
	public void onInitUniqueNPCs(List<Class<? extends NPC>> addedNpcs) {}

	/**
	 * Called after Main.start()
	 */
	public void onMainStart() {}

	/**
	 * Called after Perk static, but before hiddens are visually sorted.
	 * 
	 * Use declarePerk().
	 */
	public void onInitPerks() {}

	/**
	 * Called after Main.main()
	 */
	public void onMainMain() {}

	/**
	 * @see com.lilithsthrone.game.sex.sexActions.SexActionInterface::getRelatedFetishes
	 */
	public void getRelatedFetishes(List<AbstractFetish> fetishes, GameCharacter characterPerforming,
			GameCharacter characterTargeted, boolean isPenetration, boolean isOrgasm) {
	}

	/**
	 * @see com.lilithsthrone.game.character.CharacterUtils.generateDesires(GameCharacter)
	 */
	public void onGenerateDesiresAvailableFetishesFixup(GameCharacter character,
			List<AbstractFetish> availableFetishes) {
	}

	/**
	 * Tweak character desires here.
	 * 
	 * @see com.lilithsthrone.game.character.CharacterUtils.generateDesires(GameCharacter)
	 */
	public void onAfterGenerateDesires(GameCharacter character, List<AbstractFetish> availableFetishes,
			Map<AbstractFetish, Integer> desireMap, Map<AbstractFetish, Integer> negativeMap, int desiresAssigned) {
	}

	public void onNPCGenerateTransformativePotion(NPC npc, GameCharacter target,
			List<PossibleItemEffect> possibleEffects) {
	}

	protected void addSexTypeWeighting(GameCharacter ctx, SexType type, GameCharacter target, List<SexType> request,
			Map<SexType, Integer> map, float multiplier) {
		map.put(type, (int) (ctx.calculateSexTypeWeighting(type, target, request) * multiplier));
	}

	public void onGenerateSexChoicesAddSexTypes(GameCharacter ctx, boolean resetPositioningBan, GameCharacter target,
			List<SexType> request, Map<SexType, Integer> foreplaySexTypes, Map<SexType, Integer> mainSexTypes) {
	}

	/**
	 * All AbstractRaces have been loaded.
	 */
	public void onInitRaces() {}
}