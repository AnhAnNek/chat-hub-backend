package com.vanannek.file;

import com.vanannek.user.User;
import com.vanannek.util.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @GetMapping("/get-avatar")
    public String getAvatar(@RequestParam("username") String username, @RequestParam("genderStr") User.EGender gender) {
        String base64 = FileUtils.getAvatarSrcByUsername(username, gender);
        return base64;
    }
}
