# Omniscience
A flexible all-around No SQL logging system for spigot/paper spigot ecosystems with hooks for custom logging patterns.

**WARNING: This repository is currently volatile and a public release has not been had yet. Use at your own caution.**

## About

Omniscience is a document-driven logging system based heavily on the concepts incorperated in Prism, but intended for the spigot/paper spigot ecosystem. Using parameter-driven commands combined with optional flags you can take deep dives into the ever progressing story of each player and what they do on your server.

Omniscience is a great project that was released open source for use, and I'm forever thankful to 501 for releasing it. While the majority is done and maintenance to 1.16 is completed, expanding upon Omniscience to ensure it remains an excellent tool is on the long list of projects to complete. We hope that anyone needing a simple, No SQL solution can make use of this system just as we have, though as stated above, there is no official public release.

### Original Author
*Omniscience is something of a passion project for me. Lord of the Craft is a strongly roleplay focused server that incorperates many aspects of Minecraft into it's day to day operation. We jumped from LogBlock to HawkEye to CoreProtect and none of them could quite satisfy the in-depth chat logging we needed, the api hooks we needed, and the block logging/rollbacks we needed. I saw the oppertunity to create something unique and robust, and thus Omniscience was born.*

*-501Warhead*

## Commands

##### `/omniscience`
* **Permission:** `omniscience.mayuse`
* **Aliases:** `o`, `omni`
* **Usage:** View the help for Omniscience

##### `/omniscience search <parameters> <flags>`
* **Permission:** `omniscience.commands.search`
* **Usage:** Query omniscience based on the provided parameters and flags. See further below for more information

##### `/omniscience page <#>`
* **Permission:** `omniscience.commands.page`
* **Usage:** Flip through the pages of your most recent Omniscience search.

##### `/omniscience rollback <parameters> <flags>`
* **Permission:** `omniscience.commands.rollback`
* **Usage:** Rollback a collection of actions that omniscience has recorded based on the parameters and flags provided. See further below for more information.

##### `/omniscience tool`
* **Permission:** `omniscience.commands.tool`
* **Usage:** Grab the configured hand-held searching tool to quickly query any block you can reach

## Parameters

Parameters are the meat of how you interact with Omniscience. This is how you filter down to what you actually want to see, and is based heavily on the existing Prism/CoreProtect/HawkEye system as this is what is most familiar to users. There are however much more detailed parameters with Omniscience, allowing you to deep dive into filtering records by item names and descriptions.

Plugins can create their own parameters, so this list can never be fully complete. See the individual plugin's documentation for what it does.

#
##### Log Info
##### `r:<#>` - Radius
* **Default:** `5` (Changable in `config.yml`)
* **Usage:** Specify a number to filter your query to filter out any records not within a # box around your current in-game location.
* **Example:** `r:100`

##### `t:<time>` - Time
* **Alias:** `since`
* **Default:** `3d` (Changable in `config.yml`)
Usage: Specify a shorthand time to filter out records older than the specified time. 
* **Example:** `t:2d3h4m5s` for 2 days, 3 hours, 4 minutes, and 5 seconds.

##### `a:<action>` - Action
* **Usage:** Specify a comma-seperated list of actions to filter records by. Add a `!` in front of the event to exclude it from the search.
* **Example:** `a:break`, `a:place,break`, `a:place,!break`

##### `c:<text>` - Cause
* **Usage:** Specify a comma-seperated list of words to search for as causes for events. Add a `!` in front of the word to exclude it from the search. Use `p:` for players, this is useful for searching for entity- and world- related events.
* **Example:** `c:environment`, `c:zombie,!environment`

##### `e:<Entity>` - Entity
* **Usage:** Specify a comma-seperated list of entities to filter for events that involve them. Add a `!` in front of the entity type to exclude it from the search. The parameter will search for any event that has explicitly marked that it is involved with an entity of that type, whether it's a cause or target of such action
* **Example:** `e:zombie`, `e:!player,zombie`

##### `m:<text>` - Message
* **Usage:** Specify a comma-seperated list of words to search for in chat-related events. Add a `!` in front of the word to exclude it from the search.
* **Example:** `m:badword`, `m:501warhead,smells`, `m:501warhead,smells,!jk`

##### `trg:<misc>` - Target
* **Usage:** Specify a comma-seperated list of all-capital words to search for as targets. Add a `!` in front of the target to exclude it from the search.
* **Example:** `trg:COW`, `trg:COW,COBBLESTONE,127.0.0.1`, `trg:COBBLESTONE,!COW`

#
##### Player Info
##### `p:<username|uuid>` - Player
* **Usage:** Specify a comma-seperated list of players or uuids to filter by that specific player. Add a `!` in front of their name to exclude players from the search.
* **Example:** `p:501warhead`, `p:501warhead,Tofuus`, `p:501warhead,!Tofuus`

##### `ip:<ip>` - IP
* **Usage:** Specify a comma-seperated list of involved IPs to filter events that involve them. Add a `!` in front of the IP to exclude it from the search.
* **Example:** `i:192.168.0.1`, `i:192.168.0.1,!127.0.0.1`

#
##### Item/Block Info
##### `b:<Block>` - Block
* **Usage:** Specify a comma-seperated list of blocks to filter events that involve them. Add a `!` in front of the block to exclude it from the search. Will not work for items.
* **Example:** `b:stone`, `b:stone,oak_log`, `b:stone,!oak_log`

##### `i:<Material>` - Item
* **Usage:** Specify a comma-seperated list of item materials to filter events that involve them. Add a `!` in front of the item material to exclude it from the search. Will not work for blocks.
* **Example:** `i:iron_sword`, `i:iron_sword,diamond_sword`, `i:iron_sword,!diamond_sword`

##### `d:<text>` - Item Description
* **Usage:** Specify a comma-seperated list of words to search for in item descriptions. Add a `!` in front of the word to exclude it from the search.
* **Example:** `d:badword`, `d:501warhead,smells`, `d:501warhead,smells,!jk`

##### `n:<text>` - Item Name
* **Usage:** Specify a comma-seperated list of words to search for in item names. Add a `!` in front of the word to exclude it from the search.
* **Example:** `n:badword`, `n:501warhead,smells`, `n:501warhead,smells,!jk`

##### `cu:<yes|no>` - Custom Item
* **Usage:** Specify whether to only show interactions involving items with custom metadata.
* **Example:** `cu:yes`, `cu:no`

