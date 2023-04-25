package net.abaaja.mctl.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.abaaja.mctl.MCTL;
import net.minecraft.commands.CommandSourceStack;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class getSuggestionsMCTL {
    public static CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder){
        String completePath = MCTL.FileLocation;
        File folder = new File(completePath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            builder.suggest("No files found");
            return builder.buildFuture();
        }

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(MCTL.FileExtension)) {
                builder.suggest(file.getName().replace(MCTL.FileExtension, ""));
            }
        }
        return builder.buildFuture();
    }
}
