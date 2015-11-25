package com.xuie.util;

import android.content.Context;
import android.media.AudioManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xuie on 16-1-13.
 */
public class AudioUtils {

    private static AudioManager getAudioManager(Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public static int getCurrentVolume(Context context) {
        AudioManager audioManager = getAudioManager(context);
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public static int getMaxVolume(Context context) {
        AudioManager audioManager = getAudioManager(context);
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    public static void volumeUp(Context context) {
        int currentVol = getCurrentVolume(context);
        setVolume(context, ++currentVol);
    }

    public static void volumeDown(Context context) {
        int currentVol = getCurrentVolume(context);
        setVolume(context, --currentVol);
    }

    public static void setVolume(Context context, int vol) {
        AudioManager audioManager = getAudioManager(context);
        int maxVol = getMaxVolume(context);
        if (vol >= 0 || vol < maxVol)
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vol, 0);
    }

    public static boolean isMute(Context context) {
        AudioManager audioManager = getAudioManager(context);
        try {
            Class ownerClass = Class.forName(AudioManager.class.getName());
            Method method = ownerClass.getMethod("isStreamMute", int.class);
            return (boolean) method.invoke(audioManager, AudioManager.STREAM_MUSIC);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setMute(Context context) {
        AudioManager audioManager = getAudioManager(context);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, !isMute(context));
    }
}
