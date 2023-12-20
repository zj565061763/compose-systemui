package com.sd.lib.compose.systemui

/**
 * 明亮度
 */
sealed class Brightness {
    /**
     * 亮色
     */
    class Light internal constructor() : Brightness()

    /**
     * 暗色
     */
    class Dark internal constructor() : Brightness()

    companion object {
        fun light(): Brightness = Light()
        fun dark(): Brightness = Dark()
    }
}

/**
 * 明亮度容器
 */
interface IBrightnessStack {
    /**
     * 添加
     */
    fun add(brightness: Brightness)

    /**
     * 移除
     */
    fun remove(brightness: Brightness)

    /**
     * 最后一个对象
     */
    fun last(): Brightness?
}

internal class BrightnessStack : IBrightnessStack {
    private var _callback: Callback? = null

    private val _stack = object : LastStack<Brightness>() {
        override fun onLastItemChanged(item: Brightness?) {
            _callback?.update(item)
        }
    }

    @Synchronized
    fun registerCallback(callback: Callback) {
        _callback = callback
    }

    @Synchronized
    fun unregisterCallback(callback: Callback) {
        if (_callback === callback) {
            _callback = null
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

    fun interface Callback {
        fun update(brightness: Brightness?)
    }
}

private abstract class LastStack<T> {
    private val _itemHolder = mutableListOf<T>()

    fun add(item: T) {
        if (last() == item) return
        _itemHolder.remove(item)
        _itemHolder.add(item)
        notifyCallback()
    }

    fun remove(item: T) {
        if (last() == item) {
            _itemHolder.removeLast()
            notifyCallback()
        } else {
            _itemHolder.remove(item)
        }
    }

    fun last(): T? {
        return _itemHolder.lastOrNull()
    }

    private fun notifyCallback() {
        onLastItemChanged(last())
    }

    abstract fun onLastItemChanged(item: T?)
}