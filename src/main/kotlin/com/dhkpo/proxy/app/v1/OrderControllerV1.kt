package com.dhkpo.proxy.app.v1

import org.springframework.web.bind.annotation.*

@RequestMapping
@ResponseBody
interface OrderControllerV1 {

    @GetMapping("/v1/request")
    fun request(@RequestParam("itemId") itemId: String): String

    @GetMapping("/v1/no-log")
    fun noLog(): String
}
