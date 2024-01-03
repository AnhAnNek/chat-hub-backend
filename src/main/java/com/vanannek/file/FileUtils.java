package com.vanannek.file;

import com.vanannek.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class FileUtils {

    private static final Logger log = LogManager.getLogger(FileUtils.class);

    public static final String PREFIX_USER_FOLDER = "/static/avatar/";
    public static final String PREFIX_DEFAULT_MALE_AVATAR = "classpath:avatar/default/male.png";
    public static final String PREFIX_DEFAULT_FEMALE_AVATAR = "classpath:avatar/default/female.jpeg";

    public static String getAvatarSrcByUsername(String username, User.EGender gender) {
        try {
            String avatarFolderPath = PREFIX_USER_FOLDER + username + "/";

            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(avatarFolderPath + "*.*");

            if (resources.length > 0) {
                return toImgSrc(resources[0]);
            }
        } catch (Exception e) {
            log.error("Error loading avatar for user {}: {}", username, e.getMessage());
        }
        return getDefaultSrcAvatar(gender);
    }

    public static String getDefaultSrcAvatar(User.EGender gender) {
        try {
            String defaultAvatarPath = gender == User.EGender.MALE
                    ? PREFIX_DEFAULT_MALE_AVATAR
                    : PREFIX_DEFAULT_FEMALE_AVATAR;

            Resource defaultAvatarResource = new PathMatchingResourcePatternResolver()
                    .getResource(defaultAvatarPath);
            if (defaultAvatarResource.exists()) {
                return toImgSrc(defaultAvatarResource);
            }
        } catch (IOException e) {
            log.error("Error loading default avatar: {}", e.getMessage());
        }
        return "";
    }

    public static String toImgSrc(Resource resource) throws IOException {
        File file = resource.getFile();
        Path path = Paths.get(file.getAbsolutePath());
        byte[] bytes = Files.readAllBytes(path);
        String base64 = Base64.getEncoder().encodeToString(bytes);
        String mimeType = Files.probeContentType(path);
        return String.format("data:%s;base64,%s", mimeType, base64);
    }
}
