package xuk.android.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

/**
 *
 * @author xuie
 * @date 17-2-9
 */

object Utils {

  fun getDefaultIntent(activity: Activity): Intent {
    // 截屏
    val dView = activity.window.decorView
    dView.isDrawingCacheEnabled = true
    dView.buildDrawingCache()
    val bmp = dView.drawingCache

    // 将Bitmap转换为Uri
    val pathOfBmp = MediaStore.Images.Media.insertImage(activity.contentResolver, bmp, "title", null)
    val bmpUri = Uri.parse(pathOfBmp)

    // 清理截屏缓存
    dView.isDrawingCacheEnabled = false
    dView.destroyDrawingCache()

    //        File file = BitmapUtils.Drawable2File(this, R.mipmap.ic_launcher, Environment.getExternalStorageDirectory() + "/test.png");
    //        Uri bmpUri = BitmapUtils.File2Uri(file);

    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
    shareIntent.type = "image/*"
    return shareIntent
  }
}
