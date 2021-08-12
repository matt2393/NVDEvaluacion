package dev.mattdev.nvdevaluacion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerNotif(private val ctx: Context, workerParams: WorkerParameters): Worker(ctx, workerParams) {
    override fun doWork(): Result {
        sendNotif()
        return Result.success()
    }
    private fun sendNotif() {
        createNotifChannel()
        val intent = Intent(ctx, SplashScreenActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0)

        val id = ctx.getString(R.string.channel_id)
        val title = ctx.getString(R.string.notif_title)
        val content = ctx.getString(R.string.notif_content)
        val builder = NotificationCompat.Builder(ctx, id)
            .setSmallIcon(R.drawable.ic_notif)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(ctx)) {
            notify(1, builder.build())
        }
    }
    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val id = ctx.getString(R.string.channel_id)
            val name = ctx.getString(R.string.channel_nofif)
            val descriptionText = ctx.getString(R.string.channel_nofif_des)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(id, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}