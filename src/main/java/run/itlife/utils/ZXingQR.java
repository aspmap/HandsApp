package run.itlife.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

public class ZXingQR {
    private static int WIDTH_QR_CODE = 400;
    private static int HEIGHT_QR_CODE = 400;

    public static byte[] qrcode(HttpServletResponse response) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //if (!CheckObjectsForNull.isNull(username)) {
            String path = "handsapp.top/" + "sub-posts" + '/' + username;
            response.setContentType("image/png");
            return ZXingQR.getQRCodeImage(path, WIDTH_QR_CODE, HEIGHT_QR_CODE);
        //}
        //return null;
    }

    public static byte[] getQRCodeImage(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }
}
