import com.mickey305.util.Log;
import com.mickey305.util.file.exception.FilePathException;
import com.mickey305.util.file.targz.TarGzCompressor;
import com.mickey305.util.file.zip.ZipComponent;

import java.io.IOException;

/**
 * Created by K.Misaki on 2017/04/16.
 *
 */
public class Sample {
    private static final String HOME = System.getProperty("user.home");

    public static void main(String[] args) throws IOException {
//        testZip();
        testTarGz();
    }

    private static void testZip() {
        ZipComponent.compressDir(
                HOME + "/Downloads/test/../test/ターゲット", HOME + "/Downloads/test/result/target.zip",
                (in, out) -> Log.i("Compress Succeeded " + "{in:" + in + "," + " out:" + out + "}"),
                exception -> Log.e("Error Occurred"));
        ZipComponent.decompress(
                HOME + "/Downloads/test/result/target.zip", HOME + "/Downloads/test/../test/unzip",
                (in, out) -> Log.i("DeCompress Succeeded " + "{in:" + in + "," + " out:" + out + "}"),
                exception -> Log.e("Error Occurred"));
    }

    private static void testTarGz() {
        TarGzCompressor compressor = TarGzCompressor.getInstance();
        try {
            compressor.compressDir(
                    HOME + "/Downloads/test/ターゲット", HOME + "/Downloads/test/result/target.tar.gz");
        } catch (FilePathException e) {
            // nop
        }
    }
}
