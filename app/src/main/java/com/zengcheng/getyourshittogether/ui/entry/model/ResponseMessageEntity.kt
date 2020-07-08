package com.zengcheng.getyourshittogether.ui.entry.model

class ResponseMessageEntity {
    var msg: String? = null

    constructor() {}
    constructor(msg: String?) {
        this.msg = msg
    }

    override fun toString(): String {
        return msg!!
    }
}
