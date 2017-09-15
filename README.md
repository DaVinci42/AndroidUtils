```groovy
compile 'com.davinci42:android-utils:1000'
```


* `GradientMotionDrawable`

  ```kotlin
  val d = AnimationDrawable()
          .gradientMotion(1000, arrayListOf(
                  GradientDrawable(GradientDrawable.Orientation.BL_TR,
                          intArrayOf(Color.parseColor("#f44336"), Color.parseColor("#F4511E"))),
                  GradientDrawable(GradientDrawable.Orientation.BL_TR,
                          intArrayOf(Color.parseColor("#0288D1"), Color.parseColor("#C2185B"))),
                  GradientDrawable(GradientDrawable.Orientation.BL_TR,
                          intArrayOf(Color.parseColor("#424242"), Color.parseColor("#673AB7")))
          ))
  imageView.setImageDrawable(d)
  d.start()
  ```

  ​


