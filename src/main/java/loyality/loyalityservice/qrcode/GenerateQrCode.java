package loyality.loyalityservice.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class GenerateQrCode {
    public static byte[] getQRCode() throws WriterException, IOException {
        String data="http://ya.ru";
        String path="/Users/misha/Downloads";

        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500,500);

        //MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));

        ByteArrayOutputStream pngOutputStream =
                new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig
                (0xFF000002, 0xFF04B4AE);
        try {
            MatrixToImageWriter.writeToStream(matrix, "png", pngOutputStream, con);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return pngOutputStream.toByteArray();
    }
}
