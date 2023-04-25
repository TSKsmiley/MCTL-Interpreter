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
        File[] listOfFiles = getFiles("");

        if (listOfFiles == null) {
            builder.suggest("No files found");
            return builder.buildFuture();
        }

        for (File file : listOfFiles) {
            if(file.isDirectory()) listOfFiles += getFiles(file.getName());

            if (file.isFile() && file.getName().endsWith(MCTL.FileExtension)) {
                builder.suggest(file.getName().replace(MCTL.FileExtension, ""));
            }
        }
        return builder.buildFuture();
    }

    public static File[] getFiles(String path) {
        String completePath = MCTL.FileLocation + path;
        File folder = new File(completePath);
        return folder.listFiles();
    }
}
