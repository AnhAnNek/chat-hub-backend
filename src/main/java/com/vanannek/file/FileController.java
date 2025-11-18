package com.vanannek.file;

import com.vanannek.user.User;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
@Tag(name = "File")
@Hidden
public class FileController {

    @GetMapping("/get-avatar")
    public String getAvatar(@RequestParam("username") String username, @RequestParam("genderStr") User.EGender gender) {
        String base64 = FileUtils.getAvatarSrcByUsername(username, gender);
        return base64;
    }
}
