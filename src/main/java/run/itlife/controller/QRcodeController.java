package run.itlife.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import run.itlife.service.UserService;
import run.itlife.utils.CommonsParams;
import run.itlife.utils.ZXingQR;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@Controller
public class QRcodeController {
    private final UserService userService;
    private final ServletContext context;
    @Autowired
    ServletContext servletContext;
    @Autowired
    CommonsParams commonsParams;
    private static final Logger log = LoggerFactory.getLogger(QRcodeController.class);

    @Autowired
    public QRcodeController(UserService userService, ServletContext context) {
        this.userService = userService;
        this.context = context;
    }

    @GetMapping("qrcode/")
    @PreAuthorize("hasRole('USER')")
    public String getQrCode(HttpServletResponse response, ModelMap modelMap) throws Exception {
        commonsParams.setCommonParams(modelMap);
        byte[] qrImage = ZXingQR.qrcode(response);
        String resultQrImage = Base64.getEncoder().encodeToString(qrImage);
        modelMap.put("qrQode", resultQrImage);
        return "profile/qrcode";
    }
}
