package com.lambda.client.module.modules.chat

import com.lambda.client.module.Category
import com.lambda.client.module.Module
import com.lambda.client.util.TimeUtils
import com.lambda.client.util.color.EnumTextColor
import com.lambda.client.util.text.format
import com.lambda.commons.interfaces.DisplayEnum
import com.lambda.event.listener.listener
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.client.event.ClientChatReceivedEvent

internal object ChatTimestamp : Module(
    name = "ChatTimestamp",
    category = Category.CHAT,
    description = "Shows the time a message was sent beside the message",
    showOnArray = false
) {
    private val color by setting("Color", EnumTextColor.GRAY)
    private val separator by setting("Separator", Separator.ARROWS)
    private val timeFormat by setting("Time Format", TimeUtils.TimeFormat.HHMM)
    private val timeUnit by setting("Time Unit", TimeUtils.TimeUnit.H12)

    init {
        listener<ClientChatReceivedEvent> {
            if (mc.player == null) return@listener
            it.message = TextComponentString(formattedTime).appendSibling(it.message)
        }
    }

    val formattedTime: String
        get() = "${separator.left}${color format TimeUtils.getTime(timeFormat, timeUnit)}${separator.right} "

    val time: String
        get() = "${separator.left}${TimeUtils.getTime(timeFormat, timeUnit)}${separator.right} "

    @Suppress("unused")
    private enum class Separator(override val displayName: String, val left: String, val right: String) : DisplayEnum {
        ARROWS("< >", "<", ">"),
        SQUARE_BRACKETS("[ ]", "[", "]"),
        CURLY_BRACKETS("{ }", "{", "}"),
        ROUND_BRACKETS("( )", "(", ")"),
        NONE("None", "", "")
    }
}
