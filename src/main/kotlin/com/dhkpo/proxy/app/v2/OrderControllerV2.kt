package com.dhkpo.proxy.app.v2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@RequestMapping
@ResponseBody
open class OrderControllerV2(private val orderService: OrderServiceV2?) {

    @GetMapping("/v2/request")
    open fun request(itemId: String): String {
        orderService?.orderItem(itemId)
        return "ok"
    }

    @GetMapping("/v2/no-log")
    open fun noLog(): String {
        return "ok"
    }
}
