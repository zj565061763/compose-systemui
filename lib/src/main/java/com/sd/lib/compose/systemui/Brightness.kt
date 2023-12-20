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

internal abstract class BrightnessStack : IBrightnessStack {

    private val _stack = object : LastStack<Brightness>() {
        override fun onLastItemChanged(item: Brightness?) {
            this@BrightnessStack.onLastBrightnessChanged(item)
        }
    }

    override fun add(brightness: Brightness) {
        _stack.add(brightness)
    }

    override fun remove(brightness: Brightness) {
        _stack.remove(brightness)
    }

    override fun last(): Brightness? {
        return _stack.last()
    }

    protected abstract fun onLastBrightnessChanged(brightness: Brightness?)
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