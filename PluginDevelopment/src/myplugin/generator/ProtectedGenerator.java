package myplugin.generator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myplugin.generator.options.GeneratorOptions;

public class ProtectedGenerator extends BasicGenerator {

	private static final Pattern REGION_PATTERN = Pattern.compile(
	        "// <protected id=\"(.*?)\" begin>(.*?)// <protected id=\"\\1\" end>",
	        Pattern.DOTALL
	    );

	public ProtectedGenerator(GeneratorOptions generatorOptions) {
		super(generatorOptions);
	}
	
	//public static Map<String, String> extractProtectedRegions(Path filePath) {
	public static Map<String, String> extractProtectedRegions(String path) {
		Path filePath = Paths.get(path);
        Map<String, String> regions = new HashMap<>();

        if (!Files.exists(filePath)) {
            return regions; // nothing to extract
        }

        try {
            //String content = Files.readString(filePath);
            /*List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < lines.size(); i++) {
                sb.append(lines.get(i));
                if (i < lines.size() - 1) {
                    sb.append(System.lineSeparator());
                }
            }
            String content = sb.toString();*/
            String content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);

            //new String(Files.readAllBytes(filePath));

            Matcher matcher = REGION_PATTERN.matcher(content);
            while (matcher.find()) {
                String id = matcher.group(1).trim();
                String body = matcher.group(2).trim();
                regions.put(id, body);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + filePath, e);
        }

        return regions;
    }
	
	
}
