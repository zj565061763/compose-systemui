package com.sd.lib.compose.systemui

sealed interface Brightness {
  /** 亮色 */
  class Light internal constructor() : Brightness

  /** 暗色 */
  class Dark internal constructor() : Brightness

  companion object {
    @JvmStatic
    fun light(): Brightness = Light()

    @JvmStatic
    fun dark(): Brightness = Dark()
  }
}

interface IBrightnessStack {
  /** 添加 */
  fun add(brightness: Brightness)

  /** 移除 */
  fun remove(brightness: Brightness)

  /** 最后一个对象 */
  fun last(): Brightness?
}

internal abstract class BrightnessStack : IBrightnessStack {
  private val _stack = object : LastStack<Brightness>() {
    override fun onLastItemChanged(item: Brightness?) {
      onBrightnessChanged(item)
    }
  }

  @Synchronized
  override fun add(brightness: Brightness) {
    _stack.add(brightness)
  }

  @Synchronized
  override fun remove(brightness: Brightness) {
    _stack.remove(brightness)
  }

  @Synchronized
  override fun last(): Brightness? {
    return _stack.last()
  }

  protected abstract fun onBrightnessChanged(brightness: Brightness?)
}

private abstract class LastStack<T> {
  private val _holder = mutableListOf<T>()

  fun add(item: T) {
    if (last() == item) return
    _holder.remove(item)
    _holder.add(item)
    notifyCallback()
  }

  fun remove(item: T) {
    if (last() == item) {
      _holder.removeAt(_holder.lastIndex)
      notifyCallback()
    } else {
      _holder.remove(item)
    }
  }

  fun last(): T? {
    return _holder.lastOrNull()
  }

  private fun notifyCallback() {
    onLastItemChanged(last())
  }

  abstract fun onLastItemChanged(item: T?)
}