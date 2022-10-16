package app.balotsav.com.vvitbalotsav.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.CheckResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import java.io.File;
import java.lang.Class;
import java.lang.Cloneable;
import java.lang.Deprecated;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.net.URL;

/**
 * Contains all public methods from {@link RequestBuilder<TranscodeType>}, all options from
 * {@link RequestOptions} and all generated options from
 * {@link com.bumptech.glide.annotation.GlideOption} in annotated methods in
 * {@link com.bumptech.glide.annotation.GlideExtension} annotated classes.
 *
 * <p>Generated code, do not modify.
 *
 * @see RequestBuilder<TranscodeType>
 * @see RequestOptions
 */
@SuppressWarnings({
    "unused",
    "deprecation"
})
public class GlideRequest<TranscodeType> extends RequestBuilder<TranscodeType> implements Cloneable {
  GlideRequest(Class<TranscodeType> transcodeClass, RequestBuilder<?> other) {
    super(transcodeClass, other);
  }

  GlideRequest(Glide glide, RequestManager requestManager, Class<TranscodeType> transcodeClass) {
    super(glide, requestManager ,transcodeClass);
  }

  @Override
  @CheckResult
  protected GlideRequest<File> getDownloadOnlyRequest() {
    return new GlideRequest<>(File.class, this).apply(DOWNLOAD_ONLY_OPTIONS);
  }

  /**
   * @see GlideOptions#sizeMultiplier(float)
   */
  public GlideRequest<TranscodeType> sizeMultiplier(float sizeMultiplier) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).sizeMultiplier(sizeMultiplier);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).sizeMultiplier(sizeMultiplier);
    }
    return this;
  }

  /**
   * @see GlideOptions#useUnlimitedSourceGeneratorsPool(boolean)
   */
  public GlideRequest<TranscodeType> useUnlimitedSourceGeneratorsPool(boolean flag) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).useUnlimitedSourceGeneratorsPool(flag);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).useUnlimitedSourceGeneratorsPool(flag);
    }
    return this;
  }

  /**
   * @see GlideOptions#onlyRetrieveFromCache(boolean)
   */
  public GlideRequest<TranscodeType> onlyRetrieveFromCache(boolean flag) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).onlyRetrieveFromCache(flag);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).onlyRetrieveFromCache(flag);
    }
    return this;
  }

  /**
   * @see GlideOptions#diskCacheStrategy(DiskCacheStrategy)
   */
  public GlideRequest<TranscodeType> diskCacheStrategy(DiskCacheStrategy strategy) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).diskCacheStrategy(strategy);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).diskCacheStrategy(strategy);
    }
    return this;
  }

  /**
   * @see GlideOptions#priority(Priority)
   */
  public GlideRequest<TranscodeType> priority(Priority priority) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).priority(priority);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).priority(priority);
    }
    return this;
  }

  /**
   * @see GlideOptions#placeholder(Drawable)
   */
  public GlideRequest<TranscodeType> placeholder(Drawable drawable) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).placeholder(drawable);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).placeholder(drawable);
    }
    return this;
  }

  /**
   * @see GlideOptions#placeholder(int)
   */
  public GlideRequest<TranscodeType> placeholder(int resourceId) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).placeholder(resourceId);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).placeholder(resourceId);
    }
    return this;
  }

  /**
   * @see GlideOptions#fallback(Drawable)
   */
  public GlideRequest<TranscodeType> fallback(Drawable drawable) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).fallback(drawable);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).fallback(drawable);
    }
    return this;
  }

  /**
   * @see GlideOptions#fallback(int)
   */
  public GlideRequest<TranscodeType> fallback(int resourceId) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).fallback(resourceId);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).fallback(resourceId);
    }
    return this;
  }

  /**
   * @see GlideOptions#error(Drawable)
   */
  public GlideRequest<TranscodeType> error(Drawable drawable) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).error(drawable);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).error(drawable);
    }
    return this;
  }

  /**
   * @see GlideOptions#error(int)
   */
  public GlideRequest<TranscodeType> error(int resourceId) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).error(resourceId);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).error(resourceId);
    }
    return this;
  }

  /**
   * @see GlideOptions#theme(Resources.Theme)
   */
  public GlideRequest<TranscodeType> theme(Resources.Theme theme) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).theme(theme);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).theme(theme);
    }
    return this;
  }

  /**
   * @see GlideOptions#skipMemoryCache(boolean)
   */
  public GlideRequest<TranscodeType> skipMemoryCache(boolean skip) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).skipMemoryCache(skip);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).skipMemoryCache(skip);
    }
    return this;
  }

  /**
   * @see GlideOptions#override(int, int)
   */
  public GlideRequest<TranscodeType> override(int width, int height) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).override(width, height);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).override(width, height);
    }
    return this;
  }

  /**
   * @see GlideOptions#override(int)
   */
  public GlideRequest<TranscodeType> override(int size) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).override(size);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).override(size);
    }
    return this;
  }

  /**
   * @see GlideOptions#signature(Key)
   */
  public GlideRequest<TranscodeType> signature(Key signature) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).signature(signature);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).signature(signature);
    }
    return this;
  }

  /**
   * @see GlideOptions#set(Option<T>, T)
   */
  public <T> GlideRequest<TranscodeType> set(Option<T> option, T value) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).set(option, value);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).set(option, value);
    }
    return this;
  }

  /**
   * @see GlideOptions#decode(Class<?>)
   */
  public GlideRequest<TranscodeType> decode(Class<?> resourceClass) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).decode(resourceClass);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).decode(resourceClass);
    }
    return this;
  }

  /**
   * @see GlideOptions#encodeFormat(Bitmap.CompressFormat)
   */
  public GlideRequest<TranscodeType> encodeFormat(Bitmap.CompressFormat format) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).encodeFormat(format);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).encodeFormat(format);
    }
    return this;
  }

  /**
   * @see GlideOptions#encodeQuality(int)
   */
  public GlideRequest<TranscodeType> encodeQuality(int quality) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).encodeQuality(quality);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).encodeQuality(quality);
    }
    return this;
  }

  /**
   * @see GlideOptions#frame(long)
   */
  public GlideRequest<TranscodeType> frame(long frameTimeMicros) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).frame(frameTimeMicros);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).frame(frameTimeMicros);
    }
    return this;
  }

  /**
   * @see GlideOptions#format(DecodeFormat)
   */
  public GlideRequest<TranscodeType> format(DecodeFormat format) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).format(format);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).format(format);
    }
    return this;
  }

  /**
   * @see GlideOptions#disallowHardwareConfig()
   */
  public GlideRequest<TranscodeType> disallowHardwareConfig() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).disallowHardwareConfig();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).disallowHardwareConfig();
    }
    return this;
  }

  /**
   * @see GlideOptions#downsample(DownsampleStrategy)
   */
  public GlideRequest<TranscodeType> downsample(DownsampleStrategy strategy) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).downsample(strategy);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).downsample(strategy);
    }
    return this;
  }

  /**
   * @see GlideOptions#timeout(int)
   */
  public GlideRequest<TranscodeType> timeout(int timeoutMs) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).timeout(timeoutMs);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).timeout(timeoutMs);
    }
    return this;
  }

  /**
   * @see GlideOptions#optionalCenterCrop()
   */
  public GlideRequest<TranscodeType> optionalCenterCrop() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).optionalCenterCrop();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).optionalCenterCrop();
    }
    return this;
  }

  /**
   * @see GlideOptions#centerCrop()
   */
  public GlideRequest<TranscodeType> centerCrop() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).centerCrop();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).centerCrop();
    }
    return this;
  }

  /**
   * @see GlideOptions#optionalFitCenter()
   */
  public GlideRequest<TranscodeType> optionalFitCenter() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).optionalFitCenter();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).optionalFitCenter();
    }
    return this;
  }

  /**
   * @see GlideOptions#fitCenter()
   */
  public GlideRequest<TranscodeType> fitCenter() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).fitCenter();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).fitCenter();
    }
    return this;
  }

  /**
   * @see GlideOptions#optionalCenterInside()
   */
  public GlideRequest<TranscodeType> optionalCenterInside() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).optionalCenterInside();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).optionalCenterInside();
    }
    return this;
  }

  /**
   * @see GlideOptions#centerInside()
   */
  public GlideRequest<TranscodeType> centerInside() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).centerInside();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).centerInside();
    }
    return this;
  }

  /**
   * @see GlideOptions#optionalCircleCrop()
   */
  public GlideRequest<TranscodeType> optionalCircleCrop() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).optionalCircleCrop();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).optionalCircleCrop();
    }
    return this;
  }

  /**
   * @see GlideOptions#circleCrop()
   */
  public GlideRequest<TranscodeType> circleCrop() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).circleCrop();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).circleCrop();
    }
    return this;
  }

  /**
   * @see GlideOptions#transform(Transformation<Bitmap>)
   */
  public GlideRequest<TranscodeType> transform(Transformation<Bitmap> transformation) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).transform(transformation);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).transform(transformation);
    }
    return this;
  }

  /**
   * @see GlideOptions#transforms(Transformation<Bitmap>[])
   */
  @SuppressWarnings({
      "unchecked",
      "varargs"
  })
  public GlideRequest<TranscodeType> transforms(Transformation<Bitmap>... transformations) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).transforms(transformations);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).transforms(transformations);
    }
    return this;
  }

  /**
   * @see GlideOptions#optionalTransform(Transformation<Bitmap>)
   */
  public GlideRequest<TranscodeType> optionalTransform(Transformation<Bitmap> transformation) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).optionalTransform(transformation);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).optionalTransform(transformation);
    }
    return this;
  }

  /**
   * @see GlideOptions#optionalTransform(Class<T>, Transformation<T>)
   */
  public <T> GlideRequest<TranscodeType> optionalTransform(Class<T> resourceClass,
      Transformation<T> transformation) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).optionalTransform(resourceClass, transformation);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).optionalTransform(resourceClass, transformation);
    }
    return this;
  }

  /**
   * @see GlideOptions#transform(Class<T>, Transformation<T>)
   */
  public <T> GlideRequest<TranscodeType> transform(Class<T> resourceClass,
      Transformation<T> transformation) {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).transform(resourceClass, transformation);
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).transform(resourceClass, transformation);
    }
    return this;
  }

  /**
   * @see GlideOptions#dontTransform()
   */
  public GlideRequest<TranscodeType> dontTransform() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).dontTransform();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).dontTransform();
    }
    return this;
  }

  /**
   * @see GlideOptions#dontAnimate()
   */
  public GlideRequest<TranscodeType> dontAnimate() {
    if (getMutableOptions() instanceof GlideOptions) {
      this.requestOptions = ((GlideOptions) getMutableOptions()).dontAnimate();
    } else {
      this.requestOptions = new GlideOptions().apply(this.requestOptions).dontAnimate();
    }
    return this;
  }

  @Override
  public GlideRequest<TranscodeType> apply(RequestOptions requestOptions) {
    return (GlideRequest<TranscodeType>) super.apply(requestOptions);
  }

  @Override
  public GlideRequest<TranscodeType> transition(TransitionOptions<?, ? super TranscodeType> transitionOptions) {
    return (GlideRequest<TranscodeType>) super.transition(transitionOptions);
  }

  @Override
  public GlideRequest<TranscodeType> listener(RequestListener<TranscodeType> requestListener) {
    return (GlideRequest<TranscodeType>) super.listener(requestListener);
  }

  @Override
  public GlideRequest<TranscodeType> thumbnail(RequestBuilder<TranscodeType> thumbnailRequest) {
    return (GlideRequest<TranscodeType>) super.thumbnail(thumbnailRequest);
  }

  @Override
  public GlideRequest<TranscodeType> thumbnail(float sizeMultiplier) {
    return (GlideRequest<TranscodeType>) super.thumbnail(sizeMultiplier);
  }

  @Override
  public GlideRequest<TranscodeType> load(Object model) {
    return (GlideRequest<TranscodeType>) super.load(model);
  }

  @Override
  public GlideRequest<TranscodeType> load(String string) {
    return (GlideRequest<TranscodeType>) super.load(string);
  }

  @Override
  public GlideRequest<TranscodeType> load(Uri uri) {
    return (GlideRequest<TranscodeType>) super.load(uri);
  }

  @Override
  public GlideRequest<TranscodeType> load(File file) {
    return (GlideRequest<TranscodeType>) super.load(file);
  }

  @Override
  public GlideRequest<TranscodeType> load(Integer resourceId) {
    return (GlideRequest<TranscodeType>) super.load(resourceId);
  }

  @Override
  @Deprecated
  public GlideRequest<TranscodeType> load(URL url) {
    return (GlideRequest<TranscodeType>) super.load(url);
  }

  @Override
  public GlideRequest<TranscodeType> load(byte[] model) {
    return (GlideRequest<TranscodeType>) super.load(model);
  }

  @Override
  @CheckResult
  public GlideRequest<TranscodeType> clone() {
    return (GlideRequest<TranscodeType>) super.clone();
  }
}
