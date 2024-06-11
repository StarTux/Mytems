/**
 * Items that can be upgraded by gathering item xp and selecting
 * perks, and combined to get higher tiers.
 *
 * UpgradableItem: One single instance per item, which comprises all
 *   its tiers and status.
 *
 * UpgradableItemMenu: The GUI that opens to choose upgrades
 *
 * UpgradableItemTag: The (super)class of the tag of the item, that
 *   is the serializable metadata.
 *
 * UpgradableItemTier: One tier of an item, corresponding with one
 *   Mytem.  Higher tiers are usually created by combining to items
 *   with the same tier.
 *
 * UpgradableItemStat: And item upgrade.  For each level, the player
 *   can choose one Stat to upgrade.  Stats can have multiple levels
 *   or just one level.
 *
 * UpgradableItemStatLevel: One level of a stat.
 *
 * UpgradableStatStatus: An status represending how a user can
 *   currently interact with a stat.  Meaning if they can upgrade it,
 *   or why not.  Both to display proper information and respond to
 *   user input.
 */
package com.cavetale.mytems.item.upgradable;
