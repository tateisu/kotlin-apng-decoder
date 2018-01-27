import jp.juggler.apng.ApngBitmap
import jp.juggler.apng.ApngDecoder
import jp.juggler.apng.ApngDecoderCallback
import jp.juggler.apng.Apng
import jp.juggler.apng.ApngAnimationControl
import jp.juggler.apng.ApngFrameControl
import jp.juggler.apng.ApngImageHeader
import org.junit.Test
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths

class Test1 {

    companion object {
        private val cwd: File
            get() = Paths.get(".").toAbsolutePath().normalize().toFile()

        private val imageDir: File
            get() = cwd
    }

    @Test
    fun test1() {
        println("imageDir=$imageDir")
    }

    @Test
    fun test2() {

        FileInputStream(File(imageDir, "walk.apng")).use { inStream ->
            ApngDecoder.parseStream(
                    BufferedInputStream(inStream),
                    object : ApngDecoderCallback {
                        override fun log(message: String) {
                            println(message)
                        }

                        override fun onHeader(apng: Apng, header: ApngImageHeader) {
                            println("header=$header")
                        }

                        override fun onAnimationInfo(apng: Apng, animationControl: ApngAnimationControl) {
                            println("animationControl=$animationControl")
                        }

                        override fun onDefaultImage(apng: Apng, bitmap: ApngBitmap) {
                            println("onDefaultImage w=${bitmap.width},h=${bitmap.height}")
                        }

                        override fun onAnimationFrame(apng: Apng, frameControl: ApngFrameControl, bitmap: ApngBitmap) {
                            println("onAnimationFrame frameControl=$frameControl")
                            println("onAnimationFrame w=${bitmap.width},h=${bitmap.height}")
                        }
                    }
            )
        }
    }
}
