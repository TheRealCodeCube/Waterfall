package io.github.codecube.waterfall.toolbar;

import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.codecube.waterfall.core.WaterfallPlugin;

public abstract class ChatInputHTIL extends HotbarToolbarItemListener {
	private String defaultPrompt = "Enter some text";

	public ChatInputHTIL() {

	}

	public ChatInputHTIL(String promptText) {
		defaultPrompt = promptText;
	}

	@Override
	public boolean onUse(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
		Prompt donePrompt = new MessagePrompt() {
			@Override
			public String getPromptText(ConversationContext context) {
				return "Done!";
			}

			@Override
			protected Prompt getNextPrompt(ConversationContext context) {
				return null;
			}
		};
		Prompt firstPrompt = new StringPrompt() {
			@Override
			public String getPromptText(ConversationContext context) {
				return getPrompt(used, user, action, sneaking) + " (type in chat)";
			}

			@Override
			public Prompt acceptInput(ConversationContext context, String input) {
				onInput(used, user, action, sneaking, input);
				return donePrompt;
			}
		};
		Conversation conversation = new Conversation(WaterfallPlugin.getPlugin(WaterfallPlugin.class), user,
				firstPrompt);
		user.beginConversation(conversation);
		return false;
	}

	@Override
	public ItemStack onUpdate(HotbarToolbarItem used, Player holder, boolean sneaking) {
		return null;
	}

	public String getPrompt(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking) {
		return defaultPrompt;
	}

	public abstract void onInput(HotbarToolbarItem used, Player user, HTIUseMode action, boolean sneaking,
			String input);
}
