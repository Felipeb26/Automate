package com.bats.init.service;

import com.bats.init.config.Exceptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

@Service
public class QrCodeService {

    private static String path = System.getProperty("user.home");

    public void generateQr() {
        try {
            String data = "https://github.com/Felipeb26/Automate/blob/master/automate.exe?raw=true";
            path = path + "/qr.png";
            BitMatrix bitmap = new MultiFormatWriter().encode(
                    data, BarcodeFormat.QR_CODE, 500, 500);

            File file = new File(path);
            if (!file.exists()) {
                MatrixToImageWriter.writeToPath(bitmap, "png", Paths.get(path));
            }
        } catch (Exception e) {
            Exceptions.ToText(e);
        }
    }
}
