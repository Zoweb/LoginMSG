package me.zoweb.loginmsg;

import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.bukkit.Bukkit.getServer;

/**
 * LoginMSG (c) zachy 2017
 */
class FileCreationHelpers {

    static String readFileContents(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    static void resourceToDataFile(Plugin plugin, String resourcePath, String dataName) throws IOException {
        //return Files.copy(in, target);
        InputStream in = plugin.getClass().getClassLoader().getResourceAsStream(resourcePath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(plugin.getDataFolder(), dataName)));

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\r\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
