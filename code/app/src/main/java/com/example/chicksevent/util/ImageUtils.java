package com.example.chicksevent.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

/**
 * Utility class for safe image operations, particularly Base64 decoding with size validation.
 */
public class ImageUtils {
    private static final String TAG = "ImageUtils";
    
    /**
     * Safely decodes a Base64 image string with size validation.
     * 
     * @param base64Image the Base64-encoded image string
     * @param maxWidth maximum width in pixels (for downscaling)
     * @param maxHeight maximum height in pixels (for downscaling)
     * @return decoded Bitmap, or null if decoding fails or image is too large
     */
    public static Bitmap decodeBase64Image(String base64Image, int maxWidth, int maxHeight) {
        if (base64Image == null || base64Image.isEmpty()) {
            Log.w(TAG, "Base64 image string is null or empty");
            return null;
        }
        
        try {
            byte[] bytes = Base64.decode(base64Image, Base64.DEFAULT);
            
            // Check size before decoding
            if (bytes.length > AppConstants.MAX_BASE64_IMAGE_SIZE_BYTES) {
                Log.e(TAG, "Image too large: " + bytes.length + " bytes (max: " + 
                    AppConstants.MAX_BASE64_IMAGE_SIZE_BYTES + ")");
                return null;
            }
            
            // First, get image dimensions without loading into memory
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            
            // Calculate sample size for downscaling if needed
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
            options.inJustDecodeBounds = false;
            
            // Decode with downscaling
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            
            if (bitmap == null) {
                Log.e(TAG, "Failed to decode bitmap from Base64");
                return null;
            }
            
            return bitmap;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Invalid Base64 string", e);
            return null;
        } catch (OutOfMemoryError e) {
            Log.e(TAG, "Out of memory while decoding image", e);
            return null;
        }
    }
    
    /**
     * Calculates the appropriate inSampleSize for downscaling an image.
     * 
     * @param options BitmapFactory.Options with image dimensions
     * @param reqWidth requested maximum width
     * @param reqHeight requested maximum height
     * @return sample size (power of 2)
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, 
                                           int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            
            while ((halfHeight / inSampleSize) >= reqHeight 
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        
        return inSampleSize;
    }
}

