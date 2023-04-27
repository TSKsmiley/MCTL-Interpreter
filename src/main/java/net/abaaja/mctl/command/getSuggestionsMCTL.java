package net.abaaja.mctl.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.abaaja.mctl.MCTL;
import net.minecraft.commands.CommandSourceStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class getSuggestionsMCTL {
    public static CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder){
        var test = getAllFiles("");
        for (File file : test) {
            if (file.isFile() && file.getName().endsWith(MCTL.FileExtension)) {
                File scriptFolder = new File(MCTL.FileLocation);
                String RelativePath = file.getPath()
                        .replace(scriptFolder.getPath(), "") //remove the script folder path
                        .substring(1) //remove the first slash
                        .replace(MCTL.FileExtension, "") //remove the file extension
                        .replace("\\","/"); // replace backslashes with forward slashes

                builder.suggest("\""+RelativePath+"\"");
            }
        }
        return builder.buildFuture();
    }

    private static List<File> getAllFiles(String path) {
        ArrayList<File> allFiles = new ArrayList<>();
        File[] files = getFilesInFolder(path);

        for (File file : files) {
            if(file.isDirectory()) {
                var subFolderFiles = getAllFiles(path + file.getName()+"/");
                allFiles.addAll(subFolderFiles);
            }
            else {
                allFiles.add(file);
            }
        }
        return allFiles;
    }

    private static File[] getFilesInFolder(String path) {
        String completePath = MCTL.FileLocation + path;
        File folder = new File(completePath);
        return folder.listFiles();
    }
}
