* `GradientMotionDrawable`

  ```kotlin
  val d = GradientMotionDrawable(1000)
          .withDrawables(arrayListOf(
                  Triple(GradientDrawable.Orientation.BL_TR, Color.parseColor("#f44336"), Color.parseColor("#F4511E")),
                  Triple(GradientDrawable.Orientation.BR_TL, Color.parseColor("#0288D1"), Color.parseColor("#C2185B")),
                  Triple(GradientDrawable.Orientation.BR_TL, Color.parseColor("#424242"), Color.parseColor("#673AB7"))
          ))
  iv.setImageDrawable(d)
  d.start()
  ```

  ​

